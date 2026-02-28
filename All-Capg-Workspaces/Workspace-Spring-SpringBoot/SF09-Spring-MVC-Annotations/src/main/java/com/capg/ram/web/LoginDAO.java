package com.capg.ram.web;

 

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.springframework.stereotype.Repository;

@Repository
public class LoginDAO {

    public boolean validateUser(String user, String pass) {

        boolean status = false;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection(
                "jdbc:oracle:thin:@localhost:1521:XE",
                "capgdb",
                "capgdb"
            );

            PreparedStatement ps =
                con.prepareStatement(
                    "SELECT * FROM users WHERE username=? AND password=?");

            ps.setString(1, user);
            ps.setString(2, pass);

            ResultSet rs = ps.executeQuery();
            status = rs.next();
             
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }
}