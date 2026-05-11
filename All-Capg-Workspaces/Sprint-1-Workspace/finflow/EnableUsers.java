import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EnableUsers {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/finflowdb?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "ayush";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            Statement stmt = conn.createStatement();
            int rows = stmt.executeUpdate("UPDATE users SET enabled = true");
            System.out.println("Success! Enabled " + rows + " users in the database.");
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
