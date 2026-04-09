import java.util.*;
class validcred {
    public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		System.out.print("Enter the username: ");
		String username = s.nextLine();
		System.out.print("Enter the password: ");
		String pass =s.nextLine();
		int flag = valid(username,pass);
		if(flag == 200){
			System.out.println("Authorized.\nStatus Code: "+flag);
		}else{
			System.out.println("Unauthorized.\nStatus Code: "+flag);
		}
	}
    protected static int valid(String user,String pass) {
		String username = "ayush";
		String password = "chahal";
		if(user.equals(username) && pass.equals(password)){
			return 200;
	}
	return 401;
	}
}
