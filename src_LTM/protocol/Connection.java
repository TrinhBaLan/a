package protocol;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import server.Server;

public class Connection extends Thread {
    private Socket socketConnection = null;
    private ObjectOutputStream socketWrite = null;
    private ObjectInputStream socketRead = null;
    private FileOutputStream fileWrite = null;
    private FileInputStream fileRead = null;
    private Packet packet = null;
    private File file = null;
    private String side;
    private Server sv_connect;

    public Connection(Socket sv, String side, Server sv_connect) {
        this.socketConnection = sv;
        this.side = side;
        this.sv_connect = sv_connect;
        start();
    }

    public void run() {
        try {
            //System.out.println("A thread has been created!");

            socketWrite = new ObjectOutputStream(socketConnection.getOutputStream());
            socketRead = new ObjectInputStream(socketConnection.getInputStream());
           	if( side.equals("Client")) ClientTalk();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void ClientTalk() {
    	// co recv file,  message, client_ip, redirect_connection
    	System.out.println("client talk");
    	while(true) {
	    	try {
	    		packet = (Packet) socketRead.readObject();
	            System.out.println("Received a packet from a server.");
				
		    	switch (packet.getMsgType()) {
		    		case MESSAGEForSERVER: {
		    			 // In tin nhan duoc gui ra man hinh
		    			String fromClient = new String(packet.getData(), StandardCharsets.UTF_8);
	                    System.out.println(fromClient);
	                    
	                    // lang nghe goi tin done tu client 
	                    // nhan tin nhan down file @@done@@ tu client
	                    break;
		    		}
		    		case MESSAGEForCLIENT: {
		    			 // In tin nhan duoc gui ra man hinh
		    			// khong co tac dung gi trong xu ly
	                   System.out.println(new String(packet.getData(), StandardCharsets.UTF_8));
	                   break;
		    		}
		    		case RECEIVE_FILE: {
		    			long begin = System.currentTimeMillis();
	                	file = new File("src/client/files/" + new String(packet.getData(), StandardCharsets.UTF_8));
	                    fileWrite = new FileOutputStream(file);
	
	                    long fileSize = socketRead.readLong();
	                    System.out.println("File size is: " + fileSize + " bytes.");
	                    int count;
	
	                    // Doc du lieu roi viet vao file
	                    while (fileSize > 0) {
	                        packet = (Packet) socketRead.readObject();
	                        count = packet.getDataLength();
	                        fileWrite.write(packet.getData(), 0, count);
	                        fileSize -= count;
	                    }
	                    System.out.println("File received!");
	                    fileWrite.close();
	                    System.out.println(System.currentTimeMillis() - begin);
	                    String done_mess = "@@done@@";
	                    socketWrite.writeObject(new Packet(Message.MESSAGEForSERVER, done_mess.getBytes().length, done_mess.getBytes()));
	                    
	                    break;
		    		}
		    		
		    		case REDIRECT_CONNECTION: {
		    			// nhan ip cua client chuan bi dong vai tro server
		    			System.out.println("redirect connection");
		    			String Host_IP = new String( packet.getData(), StandardCharsets.UTF_8);
		    			System.out.println("ip:" + Host_IP);
		    			// tao thread moi cho client kieu gi nhi????????
		    			// tao thread moi cho client kieu gi nhi????????
		    			// tao thread moi cho client kieu gi nhi????????
		    			break;
		    		}
		    		default:{
		    			break;
		    		}
		    	}
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
    }

    public void ServerTalk() {

    }

    public void serverSendFile() {
    	try {
            // Ten file se duoc gui trong phan data duoi dang string
            file = new File("src/server/files/" + new String(Server.getFileName()));
            fileRead = new FileInputStream(file);
            long fileSize = file.length();
            System.out.println("Sending file " + file.getName() + " to the client. The file size is: " + fileSize + " bytes.");

            // gui ten file, kich thuoc file cho client
            socketWrite.writeObject(new Packet(Message.RECEIVE_FILE, file.getName().getBytes().length, file.getName().getBytes()));
            socketWrite.writeLong(fileSize);      
            
            int count = 0;

            // doc va gui file theo packet cho client
            while (fileSize > count) {         
            	packet = new Packet();
                int reading_size = fileRead.read(packet.getData());
                packet.setDataLength(reading_size);
                socketWrite.writeObject(packet);
                count += reading_size;
            }
            System.out.println("File sent to the client!");
            fileRead.close();
        } catch (IOException e) {
            String error = "Cannot find or open the file you requested.";
            try {
				socketWrite.writeObject(new Packet(Message.MESSAGEForCLIENT, error.getBytes().length, error.getBytes()));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }
    	
    	// lang nghe packet tu phia client lai
    	ClientTalk(); 
    }
    public Socket getSocketConnection() {
        return socketConnection;
    }

    public void setSocketConnection(Socket socketConnection) {
        this.socketConnection = socketConnection;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(Packet packet) {
        this.packet = packet;
    }
}
