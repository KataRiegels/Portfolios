// importing required classes / packages
import java.sql.*;
import java.util.ArrayList;
import static java.sql.DriverManager.getConnection;


//Class that is used to send SQL queries to the database to retrieve and update information
public class StudentModel {

    // Variables
    String psql = "Select courseName,courseYear,courseSemester  From Courses where courseID= ?;";
    Connection conn         = null;
    Statement stmt          = null;
    PreparedStatement pstmt = null;
    ResultSet rs            = null;
    String url;

    // Init
    public StudentModel(String url) 
    {
        this.url = url;
    }

    // Connecting to the database
    public void connect() throws SQLException 
    {
        conn = getConnection(url);
    }

    // Closing connection with database
    public void close() throws SQLException 
    {
        if (conn != null)
            conn.close();
    }

    // Method for making SQL statements used to query
    public void createStatement() throws SQLException 
    {
        this.stmt = conn.createStatement();
        this.pstmt = conn.prepareStatement(psql);
    }

    // Method used to return an array of all courseIDs
    public ArrayList<String> SQLQueryCourseIDs() 
    {
        // Temporary Variables
        ArrayList<String> Names = new ArrayList<>();
        String sql = "Select courseID From Courses;";

        // Try-catch to try and get course ID's from our Courses table
        try 
        {
            rs = stmt.executeQuery(sql);                                // Executes the 'sql' query and returns a result set
            while (rs != null && rs.next())                             // Resultset is iterated through
            {                           
                String name = rs.getString(1);                          // Gets the String from SQL and converts it into a Java string
                Names.add(name);                                        // Adds courseID's to the names array
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());                         // Prints on error
        }
        rs = null;
        return Names;                                                   // Returns an ArrayList of courseIDs
    }

    // Method used to return an array of all student names
    public ArrayList<String> SQLQueryStudentNames() 
    {
        // Temporary Variables
        ArrayList<String> Names = new ArrayList<>();
        String sql = "Select studentName From Students;";

        // Try-catch as before, to try and get student names from Students table
        try 
        {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) 
            {
                String name = rs.getString(1);
                Names.add(name);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }

    /* Method used to return an array of all course names (using PreparedStatement!)*/
    public ArrayList<String> getCourseName(String ID) 
    {
        // Temporary Variables
        ArrayList<String> courseNames = new ArrayList<>();

        // Try-catch
        try 
        {
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();
            courseNames.add(rs.getString(1));
            courseNames.add(Integer.toString(rs.getInt(2)));
            courseNames.add(rs.getString(3));

        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return courseNames;
    }

    // Method used to return the name of the teacher for a course
    public String getTeacherName(String ID) 
    {
        // Temporary Variables
        String sql = "Select teacherName  From Courses where courseID='" + ID + "';";
        String fullname = "";

        try {
            rs = stmt.executeQuery(sql);
            fullname = rs.getString(1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return fullname;
    }


    // Method used to return the average grade in a course
    public String getCourseAverage(String courseID) 
    {
        // Temporary Variables
        String primary = "select IFNULL(avg(grade),'none') From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' and grade is not null;";
        String avgGrade = "None";

        rs = null;
        try 
        {
            rs = stmt.executeQuery(primary);
            if (rs.getString(1).equals("none"))                         // If return type is none, the course hasn't been graded
            {
                avgGrade = "Course has not been graded";
            } 
            else avgGrade = Double.toString(rs.getDouble(1));           // Converts SQL double to a string
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return avgGrade;
    }

    //Method used to return the average grade of a student
    public String getStudentAverage(String ID) 
    {
        // Temporary variables
        String sql = "Select IFNULL(AVG(grade),'none') From Grades INNER JOIN Students ON Grades.studentName=Students.studentName Where Grades.studentName ='" + ID + "' and grade is not null;";
        String avgGrade = "none";

        try 
        {
            rs = stmt.executeQuery(sql);
            if (rs.getString(1).equals("none")) 
            {
                avgGrade = "Course has not been graded";
            } 
            else avgGrade = Double.toString(rs.getDouble(1));
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return avgGrade;
    }

    // Method used to return an array of individual courses and their grade for a select student
    public ArrayList<String[]> getCourseAndGrade(String studentName) 
    {
        // Temporary Variables
        String sql = "Select courseID,IFNULL(Grade, 'Not graded')  From Grades where studentName ='" + studentName + "';";
        ArrayList<String[]> courseAndGrade = new ArrayList<>();
        String fullname = "";
        String grade = "0";

        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) 
            {
                fullname = rs.getString(1);
                grade = rs.getString(2);                                // Secondary SQL return                    
                courseAndGrade.add(new String[]{fullname, grade});
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        return courseAndGrade;
    }

    // Method that is used to return a list of students who have no grade in a select course (null grade)
    public ArrayList<String> getUngradedStudents(String courseID) 
    {
        // Temporary Variables
        ArrayList<String> names = new ArrayList<>();
        String sql = "Select studentName From Grades where courseID = '" + courseID + "' and grade is NULL ;";
        
        try 
        {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) 
            {
                String name = rs.getString(1);
                names.add(name);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        rs = null;
        return names;
    }

    // Method that is used to return a list of courses where a student has no grade (null grade)
    public ArrayList<String> getUngradedCourses(String studentName) 
    {
        // Temporary Variables
        ArrayList<String> names = new ArrayList<>();
        String sql = "Select courseID From Grades where studentName = '" + studentName + "' and grade is NULL ;";

        try 
        {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) 
            {
                String name = rs.getString(1);
                names.add(name);
            }
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
        rs = null;
        return names;
    }

    // Method that is used to update a null grade in the database
    public void updateGrade(String studentName, String courseID, int grade) 
    {
        String sql = "UPDATE Grades Set grade = " + grade + " Where studentName='"+ studentName + "' and courseID= '" + courseID + "';";

        try 
        {
            stmt.executeUpdate(sql);
        } 
        catch (SQLException e) 
        {
            System.out.println(e.getMessage());
        }
    }
}
