import java.util.Scanner;
class prensentele
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
		boolean found = false;
		for(int i=0;i<a.length;i++){
			if(a[i] == key){
				found = true;
				break;
			}
		}
		System.out.println(found ? "Present" : "Not Present");
	}
}
