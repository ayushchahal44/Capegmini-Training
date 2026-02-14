class  tickets
	{
	public static void main(String[] args) 
	{
		int seats = 2;
		boolean member = true;
		boolean promo = false;
		System.out.println(
			(member || promo && seats<=100) ? "Booked.." : (member || promo) ? "No seats left." : "You are not a member and doesn't have any promo code."
			);
	}
}
