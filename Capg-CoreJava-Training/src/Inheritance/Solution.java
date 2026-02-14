package Inheritance;
public class Solution {
	public static void main(String[] args) {
	Google g1 = new Photos("Ayush","Chahal",50);
	g1.display();
	if(g1.valid()) {
		Photos p = new Photos("Ayush","Chahal",50);
		System.out.println("Photos: "+p.getPhotos());
	}
	}
}
class Google{
	private String username;
	private String password;
	Google(String username,String password){
		this.username=username;
		this.password=password;
	}
	public void display() {
		if(username == "Ayush" && password == "Chahal") {
			System.out.println("Login successful.");
		}	
	}
	public boolean valid() {
		if(username == "Ayush" && password == "Chahal") return true;
		return false;
	}
}
class Photos extends Google{
	private int photos;
	Photos(String username,String password,int photos){
		super(username,password);
		this.photos=photos;
	}
	public int getPhotos() {
		return photos;
	}
}

class Gmail extends Google{
	private int mail;
	Gmail(String username,String password,int mail){
		super(username,password);
		this.mail=mail;
	}
	public int getMail() {
		return mail;
	}
}
class Browser extends Google{
	private String search;
	Browser(String username,String password,String search){
		super(username,password);
		this.search=search;
	}
	public String getSearch() {
		return search;
	}
}

