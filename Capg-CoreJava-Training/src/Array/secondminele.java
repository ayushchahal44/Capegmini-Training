import java.util.Scanner;
class secondminele
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
		int max = arr[0], smin = -99999;
		for(int i=1;i<arr.length;i++){
			if(arr[i] < max){
				smin = max;
				max = arr[i];
			} else if(arr[i] < smin && arr[i] != max){
				smin = arr[i];
				}
			}
			System.out.println(smin);
	}
}

