package com.capg.lambda;
interface Person{
	public void eat();
}
public class LambdaProg1{

	public static void main(String[] args) {
		Person p1 = new Person() {
			public void eat() {
				System.out.println("Good Food");
			}
		};
		p1.eat();
	}
}
