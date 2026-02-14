import java.util.Scanner;
class maxeleodd
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
		int max=arr[1];
		for(int i=1;i<arr.length;i+=2){
			if(arr[i] > max) max=arr[i];
		}
		System.out.print("Max element of array: "+max);
	}
}
