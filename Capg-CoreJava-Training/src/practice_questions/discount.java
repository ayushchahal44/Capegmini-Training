import java.util.*;
class  discount
{
	public static void main(String[] args) 
	{
		Scanner s = new Scanner(System.in);
		int unit_c = 100;
		System.out.println("Enter the number of products to buy: ");
		int unit_q = s.nextInt();
		int total = unit_c * unit_q;
		if(total>1000){
			int dis = total - (total * 10/100);
			System.out.println("Your total amount after 10% discount is: "+dis);
	}
	else{
		System.out.println("Your total amount is: "+total);
	}
}
}
