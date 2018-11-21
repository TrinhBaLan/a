import java.util.ArrayList;
import java.util.Collections;

public class list <E extends Comparable<E>> extends ArrayList<E>  implements Comparable<E>  {
	ArrayList<E> l;
	public list(ArrayList<E> l) {
		this.l = l;
	}
	public void sort() {
		Collections.sort(l);
		//Collections.sort(l,Collections.reverseOrder());
	}
	public void print() {
		for( E e:l) {
			System.out.println(e);
		}
	}
	@Override
	public int compareTo(E o) {
		// TODO Auto-generated method stub
		return this.compareTo(o);
	}
	public E maxValue() {
		list<E> tmp = new list<>(l);
		tmp.sort();
		return tmp.getList().get(tmp.getList().size()-1);
	}
	public ArrayList<E> getList(){
		return l;
	}
	
	public static void main(String[] args) {
		ArrayList<String> al = new ArrayList<String>();
		al.add("jhjh");
		al.add("kiii");
		al.add("aaaa");
		list<String> l = new list<>(al);
		l.sort();
		l.print();
		
		ArrayList<Integer> intx = new ArrayList<>();
		intx.add(3);
		intx.add(2);
		intx.add(6);
		intx.add(-4);
		list<Integer> lx = new list<>(intx);
		lx.sort();
		System.out.println(lx.maxValue());
		lx.print();
	}
	
}
