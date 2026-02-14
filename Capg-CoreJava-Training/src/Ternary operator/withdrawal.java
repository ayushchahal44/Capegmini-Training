class withdrawal
{
	public static void main(String[] args) 
	{
		int balance = 1000;
		int amount = 500;
		System.out.println((amount>0 && balance-amount>=0)? "Can withdrawal" : "Cannot be withdrawal");
	}
}
