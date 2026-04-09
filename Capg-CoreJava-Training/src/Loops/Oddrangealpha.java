import java.util.*;
class  Oddrangealpha
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("The starting char is:");
		char st = s.next().toUpperCase().charAt(0);
		s.nextLine();
		System.out.print("The Ending char is:");
		char e = s.next().toUpperCase().charAt(0);
		System.out.print("Here is the Numbers between the range: ");
		int count =1;
		for(char i=st;i<=e;i++){
			if(i%2!=0){
			System.out.print(count+" ");
	}
	count++;
}
	}
}


 