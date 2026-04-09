@FunctionalInterface
interface Adder
{
	int add(int a, int b);
}
public class  lambdaMultiParam
{
	public static void main(String[] args) 
	{
		Adder sum = (x,y)->x+y;
		System.out.println("5 + 7 = "+sum.add(5,7));
	}
}
