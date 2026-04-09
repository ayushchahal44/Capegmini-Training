import java.util.*;
class  Summationalpha
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("The starting char is:");
		char st = s.next().toUpperCase().charAt(0);
		s.nextLine();
		System.out.print("The Ending char is:");
		char e = s.next().toUpperCase().charAt(0);
		int count =1;
		int odd_sum=0;
		int even_sum=0;
		for(char i=st;i<=e;i++){
			if(i%2==0){
				even_sum+=count;
	}else{
		odd_sum+=count;
}
count++;
	}
	System.out.println("Here is the sum of even numbers between the range: "+even_sum);
	System.out.println("Here is the sum of odd numbers  between the range: "+odd_sum);
}
}

 