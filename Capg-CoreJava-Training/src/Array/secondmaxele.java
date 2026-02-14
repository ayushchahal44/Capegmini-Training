import java.util.Scanner;
class secondmaxele
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
		int max = arr[0], smax = -99999;
		for(int i=1;i<arr.length;i++){
			if(arr[i] > max){
				smax = max;
				max = arr[i];
			} else if(arr[i] > smax && arr[i] != max){
				smax = arr[i];
				}
			}
			System.out.println(smax);
	}
}

