import java.util.Scanner;

class calculator {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
		System.out.println("Chose 1. Addition 2. Subtraction 3. Multiplication 4. Division: ");
		int choice = s.nextInt();
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------------------------------------");
		switch(choice){
			case 1:{
				System.out.println("You chose 1. Addition: ");
				System.out.println("Number 1: ");
				double c1 = s.nextDouble();
				System.out.println("Number 2: ");
				double c2 = s.nextDouble();
				System.out.println("The addition of "+c1+" and "+c2+" is: "+(c1+c2));
			}
				break;
			case 2:{
				System.out.println("You chose 2. Subtraction: ");
				System.out.println("Number 1: ");
				double c3 = s.nextDouble();
				System.out.println("Number 2: ");
				double c4 = s.nextDouble();
				System.out.println("The subtraction of "+c3+" and "+c4+" is: "+(c3-c4));
			}
				break;
			case 3:{
				System.out.println("You chose 3. Multiplication: ");
				System.out.println("Number 1: ");
				double c5 = s.nextDouble();
				System.out.println("Number 2: ");
				double c6 = s.nextDouble();
				System.out.println("The multipication of "+c5+" and "+c6+" is: "+(c5*c6));
			}
				break;
			case 4:{
				System.out.println("You chose 4. Division: ");
				System.out.println("Number 1: ");
				double c7 = s.nextDouble();
				System.out.println("Number 2: ");
				double c8 = s.nextDouble();
				if (c8 == 0) {
                    System.out.println("Division by zero is not allowed.");
                } else {
                    System.out.println("Division: " + (c7 / c8));
                }
			}
                break;
			default:{
				System.out.println("Chose only the given 4 options.");
			}
		}
	}
}
