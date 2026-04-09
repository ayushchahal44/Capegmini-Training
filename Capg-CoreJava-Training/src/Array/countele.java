import java.util.Scanner;
class countele
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
		int count=0;
		System.out.print("Enter the value you want to count: ");
		int value = s.nextInt();
		for(int i=0;i<arr.length;i++){
			if(value == arr[i]) count++;
		}
		System.out.print("Number of times element occur: "+count);
	}
}
