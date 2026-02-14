import java.util.*;
class M2
{
	public static void main(String[] args) 
	{
		System.out.println("x= "+m1()); 
		System.out.println("x= "+m2()); 
		System.out.println("x= "+m3()); 
		int res1 = m1();
		double res2 = m2();
		int res3 = m3();
		System.out.println("m1()-> "+res1); 
		System.out.println("m2()-> "+res2); 
		System.out.println("m3()-> "+res3); 
	}
	public static int m1(){
		int x = 100;
		return x;
	}
	public static double m2(){
		int x =200;
		return x;
	}
	public static int m3(){
		double x =300;
		return (int)x;
	}
}
