package com.rcallum.CalCore.Database;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLite extends Database{
    private String dbname;

    private String customCreateString;

    private File dataFolder;

    public SQLite(String databaseName, String createStatement, File folder) {
        dbname = databaseName;
        customCreateString = createStatement;
        dataFolder = folder;
        System.out.println(folder);
    }

    public Connection getSQLConnection() {
        File folder = new File(dataFolder, dbname + ".db");
        if (!folder.exists()) {
            try {
                folder.createNewFile();
                System.out.println("Created new File");
            } catch (IOException e) {
                // File write error: " + dbname + ".db
                // e.printStackTrace();
                System.out.println("File write error: "+ dataFolder + dbname + ".db");
            }
        }
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + folder);
            return connection;
        } catch (SQLException ex) {
            // SQLite exception on initialize
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            // You need the SQLite JBDC library. Google it. Put it in /lib folder
            ex.printStackTrace();

        }
        return null;
    }

    public void load(String tableName) {
        connection = getSQLConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate(customCreateString);
            s.close();
        } catch (SQLException e) {
            System.out.println("[CalCore] Created Database table: " + tableName + " Successfully");
        }
        initialize(tableName);
    }

    public File getDataFolder() {
        return dataFolder;
    }
}

