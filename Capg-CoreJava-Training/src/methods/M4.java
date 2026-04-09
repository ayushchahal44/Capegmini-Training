class M4
{
	public static void main(String[] args) 
	{
		int x = 100;
		M1(M2(),100,M3(200));
	}
	public static void M1(int i,int a,int b){
		System.out.println("m1() -> "+a);
		System.out.println("m2() -> "+i);
		System.out.println("m3(int a) -> "+b);
	}
	public static int M2(){
		return 10;
	}
	public static int M3(int a){
		return a;
	}
}
