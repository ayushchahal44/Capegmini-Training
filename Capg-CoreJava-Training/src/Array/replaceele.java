import java.util.Scanner;
class replaceele
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
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
			}
		System.out.print("Enter the index you want to replace: ");
		int rep_i = s.nextInt();
		System.out.print("Enter the Replace Value : ");
		int rep_v = s.nextInt();
		arr[rep_i]=rep_v;
		
		System.out.println("---------------After replacing------------------");
		for(int i=0;i<arr.length;i++){
			System.out.print(arr[i]+" ");
			}
	}
}
