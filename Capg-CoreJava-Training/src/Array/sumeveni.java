import java.util.Scanner;
class sumeveni
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the size of your array: ");
		int arr[]= new int[s.nextInt()];
		for(int i=0;i<arr.length;i++){
		System.out.print("Enter the "+(i+1)+" element of the array: ");
		arr[i]=s.nextInt();
		}
		System.out.println("-------------------------------------");
		int sum=0;
		for(int i=0;i<arr.length;i++){
			if(i%2==0) sum+=arr[i];
		}
		System.out.print("Sum of array's even elements is: "+sum);
	}
}
