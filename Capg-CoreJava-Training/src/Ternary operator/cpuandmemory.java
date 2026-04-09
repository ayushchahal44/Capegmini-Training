class  cpuandmemory
{
	public static void main(String[] args) 
	{
		double cpu = 70;
		double memory = 89;
		System.out.println(
			cpu<85 && memory <90 ? "Good to go." : (memory <90) ? "CPU is exceeding its limit." : "Memory is exceeding its limit."
			);
	}
}
