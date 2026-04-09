import java.util.Scanner;
class oddi
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
		System.out.print("Odd index of array is: ");
		for(int i=0;i<arr.length;i++){
			if(i%2!=0) System.out.print(arr[i]+" ");
			}
	}
}
