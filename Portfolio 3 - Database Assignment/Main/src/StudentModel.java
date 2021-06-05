
// importing required classes / packages
import java.sql.*;
import java.util.ArrayList;

import static java.sql.DriverManager.getConnection;

//Class that is used to send SQL queries to the database to retrieve and update information
public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    String psql = "Select courseName,courseYear,courseSemester  From Courses where courseID= ?;";
    ResultSet rs = null;
    StudentModel(String url) {
        this.url = url;
    }

    //connecting to the database
    public void connect() throws SQLException {
        conn = getConnection(url);
    }
    //removing connection with database
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
    //used to create a statement to execute SQL queries
    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
        this.pstmt = conn.prepareStatement(psql);
    }
    //Method used to return an array of all courseIDs
    public ArrayList<String> SQLQueryCourseIDs() {
        ArrayList<String> Names = new ArrayList<>();
        String sql = "Select courseID From Courses;";

        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }
    //Method used to return an array of all student names
    public ArrayList<String> SQLQueryStudentNames() {
        ArrayList<String> Names = new ArrayList<>();
        String sql = "Select studentName From Students;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                Names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return Names;
    }
    /* Method used to return an array of all course names (using PreparedStatement!)*/
    public ArrayList<String> getCourseName(String ID) {
        ArrayList<String> courseNames = new ArrayList<>();
        //String sql = "Select courseName,courseYear,courseSemester  From Courses where courseID='" + ID + "';";

        try {
            pstmt.setString(1, ID);
            rs = pstmt.executeQuery();
            courseNames.add(rs.getString(1));
            courseNames.add(Integer.toString(rs.getInt(2)));
            courseNames.add(rs.getString(3));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseNames;
    }
    //Method used to return the name of the teacher for a course
    public String getTeacherName(String ID) {
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


    //Method used to return the average grade in a course
    public String getCourseAverage(String courseID) {
        String primary = "select IFNULL(avg(grade),'none') From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' and grade is not null;";

        rs = null;
        String avgGrade = "None";
        try {

            rs = stmt.executeQuery(primary);
            if (rs.getString(1).equals("none")) {
                avgGrade = "Course has not been graded";
            } else avgGrade = Double.toString(rs.getDouble(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avgGrade;


    }

    //Method used to return the average grade of a student
    public String getStudentAverage(String ID) {
        String sql = "Select IFNULL(AVG(grade),'none') From Grades INNER JOIN Students ON Grades.studentName=Students.studentName Where Grades.studentName ='" + ID + "' and grade is not null;";
        String avgGrade = "none";
        try {
            rs = stmt.executeQuery(sql);
            if (rs.getString(1).equals("none")) {
                avgGrade = "Course has not been graded";
            } else avgGrade = Double.toString(rs.getDouble(1));
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avgGrade;
    }

    //Method used to return an array of individual courses and their grade for a select student
    public ArrayList<String[]> getCourseAndGrade(String studentName) {
        ArrayList<String[]> courseAndGrade = new ArrayList<>();
        String sql = "Select courseID,IFNULL(Grade, 'Not graded')  From Grades where studentName ='" + studentName + "';";
        String fullname = "";
        String grade = "0";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                fullname = rs.getString(1);

                grade = rs.getString(2);
                courseAndGrade.add(new String[]{fullname, grade});
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseAndGrade;
    }
    //Method that is used to return a list of students who have no grade in a select course (null grade)
    public ArrayList<String> getUngradedStudents(String courseID) {
        ArrayList<String> names = new ArrayList<>();
        String sql = "Select studentName From Grades where courseID = '" + courseID + "' and grade is NULL ;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return names;
    }

    //Method that is used to return a list of courses where a student has no grade (null grade)
    public ArrayList<String> getUngradedCourses(String studentName) {
        ArrayList<String> names = new ArrayList<>();
        String sql = "Select courseID From Grades where studentName = '" + studentName + "' and grade is NULL ;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()) {
                String name = rs.getString(1);
                names.add(name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        rs = null;
        return names;
    }
    //Method that is used to update a null grade in the database
    public void updateGrade(String studentName, String courseID, int grade) {
        String sql = "UPDATE Grades Set grade = " + grade + " Where studentName='"+ studentName + "' and courseID= '" + courseID + "';";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
