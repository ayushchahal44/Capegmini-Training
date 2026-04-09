package arrayList;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayListOne {
	public static void main(String[] args) {
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		l1.add(2);
		l1.add(3);
		l1.add(4);
		l1.add(5);

		Iterator<Integer> i1 = l1.iterator();
		while (i1.hasNext()) {
			System.out.print(i1.next() + " ");
		}
	}
}
