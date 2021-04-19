import java.sql.*;
import java.util.ArrayList;

import javax.management.Query;

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
  public ResultSet query(String Query)
  {
    ResultSet result = null;
    try {
      connect();
      Statement stmt = conn.createStatement();

      result = stmt.executeQuery(Query);
    } catch (SQLException e) {
      System.err.println("Error: query refused connect");
      e.printStackTrace();
    } finally
    {
      endConnection();
    }
    return result;
  }

  public String[] getStudent(String student)
  {
    String[] result = null;
    ResultSet rs = query("SELECT studentName,grade,Grades.courseID,courseName,courseYear,fallSemester,teacherName FROM Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE studentName='" + student + "';");
    try
    {
      while(rs.next())
      {
        String studentName = rs.getString("studentName");
        System.out.println(studentName);
      }
    }
    catch (SQLException e)
    {
      e.printStackTrace();
    }
    return result;
  }
}