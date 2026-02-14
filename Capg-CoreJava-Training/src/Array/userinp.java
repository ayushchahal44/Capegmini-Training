import java.util.Scanner;
class userinp
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		int arr[]= new int[5];
		for(int i=0;i<arr.length;i++){
		System.out.print("Enter the "+(i+1)+" element of the array: ");
		arr[i]=s.nextInt();
		}
		System.out.println("-------------------------------------");
		System.out.print("Array is: ");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
			}
	}
}
