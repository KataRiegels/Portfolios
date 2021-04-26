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
    db = database;
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
  private ResultSet query(String Query) throws SQLException
  {
    connect();
    Statement stmt = conn.createStatement();
    return stmt.executeQuery(Query);
  }

  // 
  public ArrayList<String> getStudents()
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT studentName FROM Students");
      while(rs.next())
      {
        String studentName = rs.getString("studentName");
        result.add(studentName);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    } finally
    {
      endConnection();
    }
    return result;
  }

  public ArrayList<String> getStudent()
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT studentName FROM Students");
      while(rs.next())
      {
        String studentName = rs.getString("studentName");
        result.add(studentName);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    } 
    finally
    {
      endConnection();
    }
    return result;
  }

  public ArrayList<String> getCourses()
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT courseID FROM Courses");
      while(rs.next())
      {
        String studentName = rs.getString("courseID");
        result.add(studentName);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    } 
    finally
    {
      endConnection();
    }
    return result;
  }

}