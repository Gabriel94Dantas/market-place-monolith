package org.acme.contexts;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MysqlContext {

    private String getURL(){
        if(System.getenv("MYSQL_HOST") != null
                && !System.getenv("MYSQL_HOST").isEmpty()){
            return System.getenv("MYSQL_HOST");
        }else{
            return "jdbc:mysql://localhost:3306/marketplacedb";
        }
    }

    private String getUsername(){
        if(System.getenv("MYSQL_APP_USER") != null
                && !System.getenv("MYSQL_APP_USER").isEmpty()){
            return System.getenv("MYSQL_APP_USER");
        }else{
            return "root";
        }
    }

    private String getPassword(){
        if(System.getenv("MYSQL_PASSWORD") != null
                && !System.getenv("MYSQL_PASSWORD").isEmpty()){
            return System.getenv("MYSQL_PASSWORD");
        }else{
            return "r00T12#";
        }
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getURL(), getUsername(), getPassword());
    }

}
