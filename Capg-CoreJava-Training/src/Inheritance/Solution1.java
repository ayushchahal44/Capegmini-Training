package Inheritance;
import java.util.*;

public class Solution1 {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Username: ");
        String username = s.nextLine();
        System.out.println("Contact number: ");
        long contNo = s.nextLong();
        System.out.println("Ola Balance: ");
        double balance = s.nextDouble();
        s.nextLine();
        System.out.println("Pickup Address: ");
        String pickUp = s.nextLine();
        System.out.println("Drop Address: ");
        String drop = s.nextLine();
        System.out.println("Enter the type to book car:(Mini/Prime/Luxury) ");
        char type = s.next().charAt(0);
        double price = 500.00;
        Ola o1 = new Mini(username, contNo, balance, pickUp, drop, price,new Ola(username, contNo, balance));
        if (o1.valid()) {
            if (type == 'M') {
                double temp = price * 1.50;
                if (balance - temp < 0)
                    System.out.println("Your Balance is low Please recharge.");
                else {
                    Mini m1 = new Mini(username, contNo, balance, pickUp, drop, temp, o1);
                    m1.bookCab();
                    m1.displayDetails();
                    s.nextLine();
                    System.out.println("Do you want to cancel the ride?(Yes or No): ");
                    char rideStatus = s.next().charAt(0);
                    if(rideStatus == 'Y') {
                    	m1.cancelRide();
                    }
                }
            } else if (type == 'P') {
                double temp = price * 2;
                if (balance - temp < 0)
                    System.out.println("Your Balance is low Please recharge.");
                else {
                    Prime p1 = new Prime(username, contNo, balance, pickUp, drop, temp, o1);
                    p1.bookCab();
                    p1.displayDetails();
                    s.nextLine();
                    System.out.println("Do you want to cancel the ride?(Yes or No): ");
                    char rideStatus = s.next().charAt(0);
                    if(rideStatus == 'Y') {
                    	p1.cancelRide();
                    }
                }
            } else if (type == 'L') {
                double temp = price * 3;
                if (balance - temp < 0)
                    System.out.println("Your Balance is low Please recharge.");
                else {
                    Luxury l1 = new Luxury(username, contNo, balance, pickUp, drop, temp, o1);
                    l1.bookCab();
                    l1.displayDetails();
                    s.nextLine();
                    System.out.println("Do you want to cancel the ride?(Yes or No): ");
                    char rideStatus = s.next().charAt(0);
                    if(rideStatus == 'Y') {
                    	l1.cancelRide();
                    }
                }
            }
        }
        s.close();
    }
}
class Ola {
    private String username;
    private long contactNo;
    private double balance;

    Ola(String username, long contactNo, double balance) {
        this.username = username;
        this.contactNo = contactNo;
        this.balance = balance;
    }
    public boolean valid() {
        return username.equals("Ayush") && contactNo == 976020;
    }
    public void setBalance(double bal) {
        balance = bal;
    }
    public double getBalance() {
        return balance;
    }
}
class Mini extends Ola {
    private String pickUp;
    private String drop;
    private double price;
    private Ola ola;
    Mini(String username, long contactNo, double balance,
         String pickUp, String drop, double price, Ola ola) {
        super(username, contactNo, balance);
        this.pickUp = pickUp;
        this.drop = drop;
        this.price = price;
        this.ola = ola;
    }
    public void bookCab() {
        System.out.println("Cab is booked from " + pickUp + " to " + drop);
        double bal = ola.getBalance() - price;
        ola.setBalance(bal);
    }
    public void displayDetails() {
        System.out.println("Pickup: " + pickUp);
        System.out.println("Drop: " + drop);
        System.out.println("Car type: Mini");
        System.out.println("Price: " + price);
    }
    public void cancelRide() {
        System.out.println("Ride is canceled.");
    }
}
class Prime extends Ola {
    private String pickUp;
    private String drop;
    private double price;
    private Ola ola;
    Prime(String username, long contactNo, double balance,
          String pickUp, String drop, double price, Ola ola) {
        super(username, contactNo, balance);
        this.pickUp = pickUp;
        this.drop = drop;
        this.price = price;
        this.ola = ola;
    }
    public void bookCab() {
        System.out.println("Cab is booked from " + pickUp + " to " + drop);
        double bal = ola.getBalance() - price;
        ola.setBalance(bal);
    }
    public void displayDetails() {
        System.out.println("Pickup: " + pickUp);
        System.out.println("Drop: " + drop);
        System.out.println("Car type: Prime");
        System.out.println("Price: " + price);
    }
    public void cancelRide() {
        System.out.println("Ride is canceled.");
    }
}
class Luxury extends Ola {
    private String pickUp;
    private String drop;
    private double price;
    private Ola ola;
    Luxury(String username, long contactNo, double balance,
           String pickUp, String drop, double price, Ola ola) {
        super(username, contactNo, balance);
        this.pickUp = pickUp;
        this.drop = drop;
        this.price = price;
        this.ola = ola;
    }
    public void bookCab() {
        System.out.println("Cab is booked from " + pickUp + " to " + drop);
        double bal = ola.getBalance() - price;
        ola.setBalance(bal);
    }
    public void displayDetails() {
        System.out.println("Pickup: " + pickUp);
        System.out.println("Drop: " + drop);
        System.out.println("Car type: Luxury");
        System.out.println("Price: " + price);
    }
    public void cancelRide() {
        System.out.println("Ride is canceled.");
    }
}