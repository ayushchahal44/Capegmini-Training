import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ListUsers {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/finflowdb?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "ayush";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT id, email, enabled, role FROM users");
            System.out.println("ID | Email | Enabled | Role");
            System.out.println("----------------------------------");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("email") + " | " + rs.getBoolean("enabled") + " | " + rs.getString("role"));
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
