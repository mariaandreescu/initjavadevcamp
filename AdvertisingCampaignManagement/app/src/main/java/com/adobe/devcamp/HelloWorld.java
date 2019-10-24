package com.adobe.devcamp;


import java.sql.Connection;
import java.sql.DriverManager;

public class HelloWorld {

    public static void main(String[] args) throws Exception {
       Class.forName("com.mysql.jdbc.Driver");

        Connection conn = DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/javadevcamp",
          "jdc",
          "devcamp"
        );
    }

}
