import java.util.*;
class bankdetails {
    static double balance;
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter name: ");
        String name = s.nextLine();
        System.out.print("IFSC Code: ");
        String ifsc = s.nextLine();
        System.out.print("Enter Balance: ");
        balance = s.nextDouble();
        for (;;) {
            System.out.println("\n1. Withdraw\n2. Deposit\n3. Exit");
            int ch = s.nextInt();
            if (ch == 1) {
                System.out.print("Enter withdrawal amount: ");
                double amt = s.nextDouble();
				if (balance-amt<0){
					continue;
				}
                display(withdraw(amt), name, ifsc);
            }
            else if (ch == 2) {
                System.out.print("Enter deposit amount: ");
                double amt = s.nextDouble();
				if (amt<0){
					continue;
				}
                display(deposit(amt), name, ifsc);
            }
            else if (ch == 3) {
                break;
            }
        }
        s.close();
    }
    public static void display(boolean status, String name, String ifsc) {
        System.out.println("Name: " + name);
        System.out.println("IFSC: " + ifsc);
        if (status)
            System.out.println("Transaction Successful");
        else
            System.out.println("Transaction Failed");
        System.out.println("Balance: " + balance);
    }
    public static boolean withdraw(double amt) {
        if (amt > 0 && balance >= amt) {
            balance = balance - amt;
            return true;
        }
        return false;
    }
    public static boolean deposit(double amt) {
        if (amt > 0) {
            balance = balance + amt;
            return true;
        }
        return false;
    }
}
