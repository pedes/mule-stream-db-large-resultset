package com.mulesoft.training.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class LargeQueryHandler {
    public static Map execute() {
        String url = "jdbc:mysql://localhost:3306/employees?useCursorFetch=true";
        Map processedCollection = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(url, "sakila", "p_ssW0rd");
             Statement stmt = conn.createStatement()) {
            
            // Enable streaming of result set
            stmt.setFetchSize(10000);
            
            ResultSet rs = stmt.executeQuery("SELECT * FROM employees.salaries");
            
            while (rs.next()) {
            	System.out.println(rs.getInt("salary"));
            	processedCollection.put(rs.getInt("emp_no"), rs.getInt("salary"));
                // Process each row individually
                // For example, read values with rs.getString("column_name"), etc.
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return processedCollection;
    }
}
