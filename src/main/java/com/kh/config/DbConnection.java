package com.kh.config;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {

    private static Connection connection = null;
    public static boolean dataLoaded = false;
    public static Connection initConnection(String fileName){
        try {
            connection = DriverManager.getConnection("jdbc:h2:mem:test;INIT=create table person as select * from CSVREAD('"+fileName+"');");
            dataLoaded = true;
        } catch (SQLException e) {
            System.out.println("SQLException " + e);
        }
        return connection;
    }
    public static Connection getConnection() {

        if(connection!=null)
            return connection;
        else{
            JOptionPane.showMessageDialog(null,"Please Load CSV File ...");
            return null;
        }

    }
}
