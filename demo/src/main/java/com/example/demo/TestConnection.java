package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestConnection {
    public static void main(String[] args) throws Exception {
        System.out.println("Hi Trisha");
        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
            Connection conn = DriverManager.getConnection("jdbc:ucanaccess://C:/Users/TrishaLaineValladoli/OneDrive - IBM/Documents/MyProfile/Profile.accdb");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from education");
            while (rs.next()) {
                System.out.println(rs.getString("user_name"));
            }

            rs.close();
            st.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("error" + e.getMessage());
        }
    }
}