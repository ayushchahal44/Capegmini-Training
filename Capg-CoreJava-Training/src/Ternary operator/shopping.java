class shopping 
{
	public static void main(String[] args) 
	{
		boolean pmember = true;
		double bill = 9000;
		System.out.println(
			pmember && bill>=5000 ? "Thanks for the shopping you are our pmember you can enjoy extra discount on your order." : (bill>=5000) ? "You can enjoy extra discount after purchasing our premium membership." : "Thank you for the shopping but you are not eligible for extra discount."
			);
	}
}
