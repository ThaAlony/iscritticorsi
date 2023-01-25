/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.edu.isspitagora.ic.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ale
 */
public class DBConnect {
    public static Connection getConnection() throws SQLException{
        String jdbcURL = "jdbc:mariadb://localhost:3306/iscritticorsi?user=root";
        return DriverManager.getConnection(jdbcURL);
        
    }
}
