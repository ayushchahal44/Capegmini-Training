package com.capg.jdbc;
import java.sql.*;
import java.util.Scanner;

public class CRUDSwitchExample {
    public static void main(String[] args) throws Exception {
        String driver = "oracle.jdbc.driver.OracleDriver";
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        String user = "capgdb";
        String pass = "capgdb";
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, pass);
        Scanner sc = new Scanner(System.in);
        System.out.println("1.Insert");
        System.out.println("2.Select");
        System.out.println("3.Update");
        System.out.println("4.Delete");
        System.out.print("Enter choice : ");
        int ch = sc.nextInt();
        switch (ch) {
        case 1:
            String ins ="insert into Employeeinfo3 values(?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(ins);
            System.out.print("Empno : ");
            ps1.setInt(1, sc.nextInt());
            System.out.print("Ename : ");
            ps1.setString(2, sc.next());
            System.out.print("Salary : ");
            ps1.setDouble(3, sc.nextDouble());
            int i = ps1.executeUpdate();
            System.out.println("Inserted : " + i);
            break;
        case 2:
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from Employeeinfo3");
            while (rs.next()) {
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getDouble(3));
            }
            break;
        case 3:
            String up ="update Employeeinfo3 set sal=? where empno=?";
            PreparedStatement ps2 = conn.prepareStatement(up);
            System.out.print("New Salary : ");
            ps2.setDouble(1, sc.nextDouble());
            System.out.print("Empno : ");
            ps2.setInt(2, sc.nextInt());
            int u = ps2.executeUpdate();
            System.out.println("Updated : " + u);
            break;
        case 4:
            String del ="delete from Employeeinfo3 where empno=?";
            PreparedStatement ps3 = conn.prepareStatement(del);
            System.out.print("Empno : ");
            ps3.setInt(1, sc.nextInt());
            int d = ps3.executeUpdate();
            System.out.println("Deleted : " + d);
            break;
        default:
            System.out.println("Invalid Choice");
        }
        conn.close();
    }
}