class  interview
{
	public static void main(String[] args) 
	{
		boolean graduation = true;
		double exp = 5;
		boolean project_exp = true;
		System.out.println(
			graduation && project_exp && (exp>=2) ? "interview Scheduled." : (graduation || project_exp) ? (project_exp) ? "Complete graduation first" : "Please have strong project experience first" : "The required condition of years of experience is not met."
			);
	}
}
