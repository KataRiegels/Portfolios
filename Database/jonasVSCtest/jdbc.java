import java.sql.*;

public class jdbc 
{
  Connection conn = null;

  public jdbc(String database)
  {
    System.out.println("Attempting connect to database at: " + database);
    try
    {
      conn = DriverManager.getConnection(database);
      System.out.println("Database Connected Successfully!");
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    finally
    {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException ex) {
          System.out.println(ex.getMessage());
        }
      }
    }
  }
}