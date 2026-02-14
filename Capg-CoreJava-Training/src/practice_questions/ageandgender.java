import java.util.*;
class  ageandgender
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the Age: ");
		int age = s.nextInt();
		System.out.print("Enter you Gender (M/F): ");
		char gender = s.next().toUpperCase().charAt(0);
		s.nextLine();
		if(gender == 'F'){
			System.out.println("You will work in Urban area only.");
	} else if(gender == 'M' && age>=20 && age<40){
		System.out.println("You can work anywhere");
	} else if(gender == 'M' && age>=40 && age<60){
		System.out.println("You can only work in urban area.");
	}
	else{
		System.out.println("ERROR");
	}
}
}
