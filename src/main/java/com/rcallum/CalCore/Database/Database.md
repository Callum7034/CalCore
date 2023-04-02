# Database (SQLite)

CalCore 02/04/2023

## Creation of databse
This will create a database and a table inside of it.
If a database file already exists it will just create the table. If the table already exists nothing will happen.

```java
File folder = new File("FILE_URI");
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
db.closeConnection(); // Close the connection
```

## Accessing the database
To put information in:

```java
HashMap<String, Object> map = new HashMap<>();
map.put("ID", 3);
map.put("INFO", "3rd info?");
db.insertValues(table_name, map); // Inserting will replace any ID with the same value
```

Getting the information out:
```java
// To get a map of an entire row:
int row = 0;
HashMap<String, Object> data = db.queryRow(table_name, row);

// Single object
String valueID = "INFO";        
Object singleData = db.queryValue(table_name, row, valueID)
String value = (String) singleData;

```
