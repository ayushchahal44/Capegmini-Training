class MarkerDriver1
{
	public static void main(String[] args) 
	{
		Marker.name = "Blue";
		System.out.println(Marker.name);
		Marker.write();
		Marker.name = "Red";
		System.out.println(Marker.name);
		System.out.println(Marker.name);
	}
}
