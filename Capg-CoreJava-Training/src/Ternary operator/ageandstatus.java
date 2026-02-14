class  ageandstatus
{
	public static void main(String[] args) 
	{
		boolean status = true;
		int age = 20;
		System.out.println(
			age>=18 && status ? "Good to go." : (status) ? "Access denied age limit." : "You are banned from our application"
			);
	}
}
