import java.util.Scanner;

class cart {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
		System.out.println("Have you ordered successfully? (true/false): ");
		boolean cart = s.nextBoolean();
		s.nextLine();
		System.out.println("What is your address: ");
        String addr = s.nextLine();
		System.out.println(" ");
		System.out.println("Status of your Cart:- ");
		if(cart && !addr.isEmpty()){
			System.out.println("Order confirmed and is ready to deliver at your address"+addr);
		}else if(addr.isEmpty()){
			System.out.println("Address incorrect please check once.");
		}else{
		System.out.println("Your cart is empty");
		}
	}
}

