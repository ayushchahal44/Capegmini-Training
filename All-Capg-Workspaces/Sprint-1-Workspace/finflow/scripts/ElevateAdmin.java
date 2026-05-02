import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ElevateAdmin {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/finflowdb?useSSL=false&allowPublicKeyRetrieval=true", "root", "ayush");
            Statement stmt = conn.createStatement();
            stmt.executeUpdate("UPDATE users SET role = 'ADMIN' WHERE email = 'admin@gmail.com'");
            System.out.println("Elevated admin@gmail.com to ADMIN role successfully.");
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
