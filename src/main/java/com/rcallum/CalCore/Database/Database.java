package com.rcallum.CalCore.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Database {
    protected Connection connection;

    public abstract Connection getSQLConnection();

    public abstract void load(String tableName);

    public void initialize(String tableName) {
        connection = getSQLConnection();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM " + tableName);
            ResultSet rs = ps.executeQuery();
            close(ps, rs);

        } catch (SQLException ex) {
            // Unable to retreive connection
            ex.printStackTrace();
        }
    }

    /**
     * Get a single value from the database. Your If your statement returns multiple
     * values, only the first value will return. Use queryRow for multiple values in
     * 1 row.
     * <p>
     *
     * @param tableName
     *            Name of the table to access
     *
     * @param row
     *            row you would like to get data from.
     *
     * @param id
     *            The column ID/Name
     *
     * @return the {@link Database}'s Query in Object format. Casting required to
     *         change variables into their original form.
     */
    public Object queryValue(String tableName, int row, String id) {
        String statement = "SELECT "+id+" "
                + "FROM " + tableName +
                " WHERE ID = " + row;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement(statement);

            rs = ps.executeQuery();
            rs.getObject(1);
        } catch (SQLException ex) {
            // Errors.sqlConnectionExecute()
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                // Errors.sqlConnectionClose()
                ex.printStackTrace();
            }
        }
        return null;
    }

    public void insertValues(String tableName, HashMap<String, Object> data) {
        String values = "";
        String ids = "";
        for (String key : data.keySet()) {
            values += "?,";
            ids += key + ",";
        }
        values = values.substring(0, values.length()-1);
        ids = ids.substring(0, ids.length()-1);

        String statement = "REPLACE INTO "+ tableName +
                "(" + ids + ") VALUES(" + values + ")";

        System.out.println(statement);
        int id = 0;
        String info = "This is some information";
        PreparedStatement ps = null;
        try {
            ps = getSQLConnection().prepareStatement(statement);
            int i = 1;
            for (Object value : data.values()) {
                ps.setObject(i, value);
                i++;
            }
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    /**
     * Get a hashmap of data from a single row on your specified table
     * <p>
     *
     * @param tableName
     *            Name of the table to query
     *
     * @param row
     *            row you would like to get data from.
     *
     * @return the {@link Database}'s Query in HashMap<String, Object> format. Casting required
     *         to change objects into their original form. The String is the name of the column
     *         and the Object is the data stored inside.
     */
    public HashMap<String, Object> queryRow(String tableName, int row) {
        String statement = "SELECT * "
                + "FROM " + tableName +
                " WHERE ID = " + row;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        HashMap<String, Object> objects = new HashMap<>();
        try {
            conn = getSQLConnection();
            ps = conn.prepareStatement(statement);

            rs = ps.executeQuery();
            int size = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= size; i++) {
                objects.put(rs.getMetaData().getColumnName(i), rs.getObject(i));
            }

            return objects;
        } catch (SQLException ex) {
            // Errors.sqlConnectionExecute()
            ex.printStackTrace();
        } finally {
            try {
                if (ps != null)
                    ps.close();
                if (conn != null)
                    conn.close();
            } catch (SQLException ex) {
                // Errors.sqlConnectionClose()
                ex.printStackTrace();
            }
        }
        return null;
    }


    /**
     * Close the current connection of the statement to the database.
     * <p>
     *
     * @param The
     *            statement previously used.
     *
     * @param The
     *            result set that was returned from the statement.
     *
     */
    public void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
        } catch (SQLException ex) {
//            Error.close(SQLiteLib.getInstance(), ex);
            ex.printStackTrace();
        }
    }


    /**
     * Close the current connection to the database. The database will need to be
     * re-initialized if this is used. When intializing using the main class, it
     * will delete this current object and create a new object connected to the db.
     * If you'd like to reload this db without trashing the database object, invoke
     * the load() method through the global map of databases. E.g
     * getDatabase("name").load();.
     *
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {

//            Error.close(SQLiteLib.getInstance(), e);
            e.printStackTrace();
        }
    }
}
