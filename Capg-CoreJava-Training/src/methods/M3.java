import java.util.*;
class M3 {
    public static void main(String[] args) {
        System.out.println("Details: \n"+Details());
    }
    public static String Details() {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter name: ");
		String name,age,phone,address;
        name = s.nextLine();
        System.out.print("Enter age: ");
        age = s.nextLine();
        System.out.print("Enter phone number: ");
        phone = s.nextLine();
        System.out.print("Enter address: ");
        address = s.nextLine();
		return ("Name: "+name+"\nAge: "+age+"\nPhone: "+phone+"\nAddress: "+address);
    }
}
