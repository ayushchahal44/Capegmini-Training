import java.util.*;
class  Numtoapha
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the number to show the character: ");
		int count = s.nextInt();
		int index=1;
		for(char i='A';i<='Z';i++){
			if(index==count){
			System.out.print("Character: "+i);
			break;
	}
	index++;
	}
}
}


 