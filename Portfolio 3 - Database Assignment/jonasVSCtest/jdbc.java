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

  // --QUERIES--
  // Returns string arraylist of student names
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

  // Returns ArrayList of a single student's courses and average
  public ArrayList<String> getStudent(String student)
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT grade,courseID FROM Grades WHERE studentName='" + student + "';");
      while(rs.next())
      {
        String str;
        String course = rs.getString("courseID");
        Integer grade = rs.getInt("grade");
        if (rs.wasNull())
        {
          str = "Not Graded";
        } 
        else
        {
          str = grade.toString();
        }
        result.add("Course " + course + " grade: " + str);
      }
      rs = query("SELECT AVG(grade) AS average FROM Grades WHERE studentName='" + student + "';");
      while(rs.next())
      {
        String str;
        Integer grade = rs.getInt("average");
        if (rs.wasNull())
        {
          str = "No average available";
        }
        else
        {
          str = grade.toString();
        }
        result.add("Average grade: " + str);
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

  // Returns an ArrayList of courseIDs
  public ArrayList<String> getCourses()
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT courseID FROM Courses");
      while(rs.next())
      {
        String courseID = rs.getString("courseID");
        result.add(courseID);
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

  // Returns an ArrayList of a single course's overall information
  public ArrayList<String> getCourse(String courseID)
  {
    ArrayList<String> result = new ArrayList<String>();
    try
    {
      ResultSet rs = query("SELECT courseName,courseYear,fallSemester,teacherName FROM Courses WHERE courseID='"+courseID+"';");
      while(rs.next())
      {
        result.add("Course Name: " + rs.getString("courseName"));
        result.add("Course year: " + rs.getString("courseYear"));
        String str;
        if (rs.getBoolean("fallSemester"))
        {
          str =  "Fall";
        }
        else
        {
          str = "Spring";
        }
        result.add(str + " Semester");
        result.add("Teacher: " + rs.getString("teacherName"));
      }
      rs = query("SELECT count(courseID) as Attends,AVG(grade) as Average FROM Grades WHERE courseID='"+courseID+"';");
      while(rs.next())
      {
        result.add("Attendees: " + rs.getInt("Attends"));
        String avg = "" + rs.getInt("Average");
        if (rs.wasNull())
        {
          avg = "None";
        }
        result.add("Grade Average: " + avg);
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

  // Sends a grade change to the database
  public void setGrade(String grade, String studentName, String courseID)
  {
    try
    {
      query("UPDATE Grades SET grade = " + grade + " WHERE courseID='"+courseID+"' AND studentName='"+studentName+"';");
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
}