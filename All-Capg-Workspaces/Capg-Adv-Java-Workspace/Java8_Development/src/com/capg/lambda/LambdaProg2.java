package com.capg.lambda;
interface Person{
	public void eat();
}
public class LambdaProg2{

	public static void main(String[] args) {
		Person p1 = ()-> System.out.println("Eat good food.");
		p1.eat();	
	}
}
