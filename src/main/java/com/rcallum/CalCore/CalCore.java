package com.rcallum.CalCore;

import com.rcallum.CalCore.Database.SQLite;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public final class CalCore {

    public static void main(String[] args) throws SQLException {
        File folder = new File("/Users/callum/Documents/Home/Coding/MC/Plugins/CalCore/build/libs");
        String db_name = "DATABASE_NAME";
        String table_name = "table_test";

        // An example creation string
        String sqlString = "CREATE TABLE IF NOT EXISTS " + table_name + " ("
                + "`ID` varchar(32) NOT NULL,"
                + "'INFO' string,"
                + "PRIMARY KEY (`ID`)"
                + ");";

        SQLite db = new SQLite(db_name, sqlString, folder);
        db.load(table_name); // Initialise the connection

        HashMap<String, Object> map = new HashMap<>();
        map.put("ID", 3);
        map.put("INFO", "3rd info?");
        db.insertValues(table_name, map);


        db.queryRow(table_name, "ID", 3).forEach((s, o) -> {
            System.out.println("Name: " + s + " Value: " + o.toString());
        });


        db.closeConnection(); // Close the connection
    }
}
