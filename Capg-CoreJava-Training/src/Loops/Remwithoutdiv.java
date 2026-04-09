import java.util.*;
class  Remwithoutdiv
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("The divisor:");
		int dsr = s.nextInt();
		System.out.print("The divident:");
		int dvr = s.nextInt();
		while(dvr>=dsr){
			dvr=dvr-dsr;
}
			System.out.print("The Reminder: "+dvr);
	}
}


 