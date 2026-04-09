import java.util.*;
class areamethod {
    public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		for(;;){
        System.out.println("\n1. Square  2. Rectangle  3. Circle");
        System.out.print("Choose option: ");
        int choice = s.nextInt();
        switch (choice) {
            case 1:
                System.out.println("Area of Square = " + square());
                break;
            case 2:
                System.out.println("Area of Rectangle = " + rectangle());
                break;
            case 3:
                System.out.println("Area of Circle = " + circle());
                break;
            default:
                System.out.println("Invalid choice.");
        }
		System.out.println("Do you want to continue? (Yes/No): ");
			char flag = s.next().toUpperCase().charAt(0);
			s.nextLine();
			if(!flag=='Y'){
				System.out.println("Program Ended.");
				break;
			}
		}
    }
    public static double square() {
		Scanner s = new Scanner(System.in);
        System.out.print("Enter side: ");
        double side = s.nextDouble();
        return (side * side);
    }
    public static double rectangle() {
		Scanner s = new Scanner(System.in);
        System.out.print("Enter length: ");
        double l = s.nextDouble();
        System.out.print("Enter breadth: ");
        double b = s.nextDouble();
        return (l * b);
    }

    public static double circle() {
		Scanner s = new Scanner(System.in);
        System.out.print("Enter radius: ");
        double r = s.nextDouble();
        return (3.14 * r * r);
    }
}
