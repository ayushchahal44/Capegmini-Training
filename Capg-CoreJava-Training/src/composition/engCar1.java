package composition;
public class engCar1 {
	public static void main(String[] args) {
		Cr c;
		c = new Cr("Maruti",new Eng());
		System.out.println(c.model);
		System.out.println(c.e.eno);
	}
}
class Eng{
	int eno = 123;
}

class Cr{
	String model;
	Eng e;
	Cr(String model,Eng e){
		this.model = model;
		this.e=e;
	}
}