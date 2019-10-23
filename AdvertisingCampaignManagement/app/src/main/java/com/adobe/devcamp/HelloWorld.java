package com.adobe.devcamp;

import java.sql.Connection;
import java.sql.DriverManager;

public class HelloWorld {

    public static void main(String[] args) throws Exception{

        // Loading driver
        Class.forName("com.mysql.jdbc.Driver");
        // Getting connection
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/javadevcamp",
                "root",
                "devcamp");
    }
}
