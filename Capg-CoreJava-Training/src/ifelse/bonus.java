import java.util.Scanner;

class bonus {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
		System.out.println("Enter the name of the employee: ");
		String name = s.nextLine();
		System.out.println("Enter the year of experience : ");
		int exp = s.nextInt();
		System.out.println("Enter the rating of "+name+" : ");
		int rating = s.nextInt();
		System.out.println("Status of Bonus:- ");
		if(rating>=4 && exp>=5){
			System.out.println(name+" you are eligible for the bonus.");
		}else if(rating>=4){
			System.out.println(name+" you are not eligible for the bonus.");
			System.out.println(name+" your experience is not more then 5 years.");
		}else{
			System.out.println(name+" you are not eligible for the bonus.");
			System.out.println(name+" your rating is not more then 4.");
		}
	}
}

