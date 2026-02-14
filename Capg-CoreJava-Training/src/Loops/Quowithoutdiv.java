import java.util.*;
class  Quowithoutdiv
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("The divisor:");
		int dsr = s.nextInt();
		System.out.print("The divident:");
		int dvr = s.nextInt();
		int count=0;
		while(dvr>=dsr){
			count++;
			dvr=dvr-dsr;
}
			System.out.print("The quotient: "+count);
	}
}


 