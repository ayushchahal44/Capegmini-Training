import java.util.Scanner;
class secondhalfmax
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
		int max=arr[arr.length/2];
		for(int i=arr.length/2;i<arr.length;i++){
			if(arr[i] > max) max=arr[i];
		}
		System.out.print("Maximum of second half of array: "+max);
	}
}
