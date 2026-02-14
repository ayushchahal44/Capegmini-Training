class StudentDriver 
{
	public static void main(String[] args) 
	{
		//We can access static mem by using className / object ref
		Student.name = "Ayush";
		Student.age = 21;
		System.out.println("Student Name: "+Student.name);
		System.out.println("Student Age: "+Student.age);
		Student.study();Student.exam();
	}
}
