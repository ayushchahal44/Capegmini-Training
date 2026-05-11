import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DeleteUser {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/finflowdb?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "ayush";
        String emailToDelete = "ayushchahal44@gmail.com";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            
            // Delete from dependent tables first if necessary. 
            // For now, let's try deleting from users. If there are FK constraints, we'll see the error.
            int rows = stmt.executeUpdate("DELETE FROM users WHERE email = '" + emailToDelete + "'");
            
            if (rows > 0) {
                System.out.println("Success! Deleted user: " + emailToDelete);
            } else {
                System.out.println("User not found: " + emailToDelete);
            }
            
            conn.close();
        } catch (Exception e) {
            System.err.println("Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
