import java.util.Scanner;

class days {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
		System.out.print("Enter the number of the day: ");
		int choice = s.nextInt();
		System.out.println("---------------------------------------------------------------------------------------------------");
		System.out.println("---------------------------------------------------------------------------------------------------");
		switch(choice){
			case 1:{
				System.out.println("Day: Monday");
				System.out.println("Today is Weekday");
			}
				break;
			case 2:{
				System.out.println("Day: Tuesday");
				System.out.println("Today is Weekday");
			}
				break;
			case 3:{
				System.out.println("Day: Wednesday");
				System.out.println("Today is Weekday");
			}
				break;
			case 4:{
				System.out.println("Day: Thursday");
				System.out.println("Today is Weekday");
			}
                break;
			case 5:{
				System.out.println("Day: Friday");
				System.out.println("Today is Weekday");
			}
                break;
			case 6:{
				System.out.println("Day: Saturday");
				System.out.println("Today is Weekend");
			}
                break;
			case 7:{
				System.out.println("Day: Sunday");
				System.out.println("Today is Weekend");
			}
                break;
			default:{
				System.out.println("Please chose from the range 1 to 7");
			}
		}
	}
}
