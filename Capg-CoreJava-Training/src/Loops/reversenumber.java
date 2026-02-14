import java.util.*;
class  reversenumber
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the number to reverse: ");
		int count = s.nextInt();
		int reversed = 0;
		int temp=count;
		while(count!=0){
			int last = count%10;
			reversed*=10+last;
			count/=10;
		}
		System.out.print("Reverse of "+temp+" is: "+reversed);			
	}
}


 