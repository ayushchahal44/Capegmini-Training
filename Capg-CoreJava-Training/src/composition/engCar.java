package composition;
public class engCar {
	public static void main(String[] args) {
		Car c = new Car();
		System.out.println(c.model);
		System.out.println(c.e.eno);
	}
}
class Engine{
	int eno = 123;
}
class Car{
	String model = "Alto";
	Engine e = new Engine();
}