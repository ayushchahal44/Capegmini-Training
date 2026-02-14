class  onlineorder
{
	public static void main(String[] args) 
	{
		boolean payment = true;
		boolean backorder=true;
		int order=10;
		int stock = 12;
		System.out.println(
			payment && backorder && (stock-order>=0) ? "Can order." : (payment || backorder) ? (backorder) ? "Payment failed." : "Backorder is not available" : "Stock is out of limit."
			);
	}
}
