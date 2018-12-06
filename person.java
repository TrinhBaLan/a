
package lab12;

import java.util.ArrayList;

public class person {
    String name;
    String sex;
    boolean isMarried = false;
    person partner;
    ArrayList<person> children;

    public person(String name, String sex) {
        this.name = name;
        this.sex = sex;
        children = new ArrayList<person>();
    }
    public void setMarried( boolean b){
        if(b) isMarried = true;
    }
    public void marry(person p){
        isMarried = true;
        partner = p;
        p.setMarried(true);
        p.setPartner(this);
    }
    public void addChildren(person p){
        if( isMarried ){
            children.add(p);
            partner.getChildren().add(p);
        }
    }
    public void setPartner( person p){
        partner = p;
    }
    public person getPartner(){
        if( isMarried ){
            return partner;
        }
        else return null;
    }
    public ArrayList<person> getChildren(){
        return children;
    }
    public String toString(){
        return "name :"+ name +"  sex :" + sex;
    }
    
    
    public static void main(String[] args){
        person grandpa = new person("1/1/1111", "nam");
        person grandma = new person("2/2/222", "nu");
        grandma.marry(grandpa);
        
        person con1 = new person("con1", "nam");
        grandma.addChildren(con1);
        person  con2 = new person("con2", "nam");
        grandpa.addChildren(con2);
        
        con1.marry(new person("vo", "nu"));
        
        person chau1 = new person("chau1", "nu");
        con1.addChildren(chau1);
        person chau2 = new person("chau2", "nam");
        con1.addChildren(chau2);
        
//        System.out.println("khong ket hon");
//        notMarry(grandma);
//        
//        System.out.println("co 2 con");
//        have2children(grandpa);
    
        printLastestGen(grandma);
    }
    
     public static void notMarry( person p){
        if(! p.isMarried) System.out.println(p);
        else{
            for( int i = 0; i< p.getChildren().size(); i++){
                notMarry(p.getChildren().get(i));
            }
        }
    }
     
     public static void have2children(person p){
         if( p.isMarried ){
             if( p.getChildren().size() == 2){
                 System.out.println(p + "   "+ p.getPartner());
             }
             for( int i = 0; i< p.getChildren().size(); i++){
                have2children(p.getChildren().get(i));
            }
         }        
     }
     public static void lastestGen(person p, ArrayList lastest){
         
         if( p.getChildren().size() > 0){
             lastest.clear();
             for(person x : p.getChildren()){
                 lastest.add(x);
             }
         }
         
         for( int i = 0; i< p.getChildren().size(); i++){
             lastestGen(p.getChildren().get(i) , lastest);
         }
     }
     public static void printLastestGen(person p){
         ArrayList<person> lastest = new ArrayList<>();
         lastestGen(p, lastest);
         for (person x : lastest){
             System.out.println(x);
         }
     }
     
}