class  alert
{
	public static void main(String[] args) 
	{
		int count = 1;
		boolean active = true;
		System.out.println(
			(active && count<=3) ? "You can try again.." : (active) ? "No attempts left." : "Your account is locked."
			);
	}
}
