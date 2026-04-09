import java.util.*;
class  Expseven
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Starting number:");
		int m = s.nextInt();
		System.out.print("Ending number:");
		int n = s.nextInt();
		System.out.println("Desired numbers without 7: ");
		for(int i=m;i<=n;i++){
			if(!String.valueOf(i).contains("7")){
				System.out.print(i+" ");
			}
		}
	}
}


 