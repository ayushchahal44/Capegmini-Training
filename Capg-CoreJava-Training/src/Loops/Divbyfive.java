import java.util.*;
class  Divbyfive
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("The starting char is:");
		char st = s.next().toUpperCase().charAt(0);
		s.nextLine();
		System.out.print("The Ending char is:");
		char e = s.next().toUpperCase().charAt(0);
		System.out.print("Sum of multiple of 5 between the range: ");
		int count =1;
		int sum=0;
		for(char i=st;i<=e;i++){
			if(i%5==0){
				sum+=count;
	}
	count++;
}
			System.out.print(sum+" ");
	}
}


 