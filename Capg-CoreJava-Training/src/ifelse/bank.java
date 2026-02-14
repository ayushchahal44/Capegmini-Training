import java.util.Scanner;

class bank {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
		int min_bal = 500;
		System.out.println("Enter your account number: ");
		long acc_no = s.nextLong();
        System.out.println("The balance of the account:");
        int bal = s.nextInt();
		boolean flag = false;
		System.out.println(" ");
		System.out.println("Status of your Bank account:- ");
		if(min_bal>bal && !flag){
			System.out.println("Low Balance");
    }
	else if(flag){
		System.out.println("Frozen");
} else{
	System.out.println("Active");
}
	}
}

