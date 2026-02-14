import java.util.*;
class Happynum 
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.println("Enter the range to find the happy numers from 1 to: ");
		int num = s.nextInt();
		System.out.println("Happy numbers are: ");
		for(int i=1;i<=num;i++){
			if(calc(i)){
				System.out.print(i+" ");
			}
	}
	System.out.println();
	System.out.println("Happy numbers Through recurssion: ");
		for(int i=1;i<=num;i++){
			if(reccal(i)){
				System.out.print(i+" ");
			}
	}
}
	public static boolean calc(int n){
		while (n > 9) {
            int sum = 0;
            while (n > 0) {
                int dig = n % 10;
                sum += dig * dig;
                n /= 10;
            }
            n = sum;
        }
        return (n==1);
    }
	public static boolean reccal(int n){
		int sum=0;
		while(n>0){
			int dig = n%10;
			sum += dig * dig;
			n/=10;
		}
		if(sum==1) return true;
		if (sum > 9) return reccal(sum);
		return false;
	}
}