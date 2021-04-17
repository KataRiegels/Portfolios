import java.sql.*;
import java.util.ArrayList;

public class jdbc 
{
  // Private variables for connector and database
  private Connection conn = null;
  private String db = null;

  // Init with test connection
  public jdbc(String database)
  {
    System.out.println("Attempting connect to database at: " + database);
    try
    {
      connect(database);
      db = database;
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      endConnection();
    }
  }

  // Method for connecting the jdbc driver to database through the object, only use internally
  private void connect(String database) throws SQLException
  {
    conn = DriverManager.getConnection(database);
    System.out.println("Database Connected Successfully!");
  }

  // Default method using internal database
  private void connect() throws SQLException
  {
    if (db != null)
    {
      connect(db);
    } 
    else
    {
      System.err.println("Connector error: Internal database missing or not set");
    }
  }

  // Forcibly ends connection
  public void endConnection()
  {
    if (conn != null) {
      try {
        conn.close();
      } catch (SQLException ex) {
        System.err.println(ex.getMessage());
      }
    }
  }

  // Queries a database for data through sql
  public ArrayList query(String Query)
  {
    ArrayList result = null;
    try {
      connect();
    } catch (SQLException e) {
      System.err.println("Error: query refused connect");
      e.printStackTrace();
    } finally
    {
      endConnection();
    }
    return result;
  }
}