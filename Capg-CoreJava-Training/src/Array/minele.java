import java.util.Scanner;
class minele
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
		int min=arr[0];
		for(int i=1;i<arr.length;i++){
			if(min>arr[i]) min=arr[i];
		}
		System.out.print("Min element of array: "+min);
	}
}
