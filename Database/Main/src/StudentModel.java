import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

import static java.sql.DriverManager.getConnection;

public class StudentModel {
    Connection conn = null;
    String url;
    Statement stmt = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    StudentModel(String url) {
        this.url = url;
    }

    public void connect() throws SQLException {
        conn = getConnection(url);
    }

    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }

    public void createStatement() throws SQLException {
        this.stmt = conn.createStatement();
    }

    public ArrayList<String> SQLQueryStudentNames(){
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

    public ArrayList<String> getCourseName(String ID) {
        ArrayList<String> courseNames = new ArrayList<>();
        String sql = "Select courseName,courseYear,courseSemester  From Courses where courseID='" + ID + "';";

        try {
            rs = stmt.executeQuery(sql);
            courseNames.add(rs.getString(1));
            courseNames.add(Integer.toString(rs.getInt(2)));
            courseNames.add(rs.getString(3));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courseNames;
    }

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

    public ArrayList<String> getAllUngradedCourses() {
        ArrayList<String> courses = new ArrayList<>();
        //String primary = "select IFNULL(avg(grade),'null') From Grades WHERE Grades.courseID='" + courseID + "' AND Grades.grade is";
        String sql = "select courseID from Grades where grade is null;";
        String name = null;
        //String primary = "select grade From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' AND Grades.grade is";
        String avgGrade = "None";
        //String sql = "select avg(grade) as CourseAverage From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' AND Grades.grade is not null ;";
        try {

            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next() ) {
                    name = rs.getString(1);
                if (!courses.contains(name)) {
                    courses.add(name);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(courses);
        return courses;


    }


    /* CHANGES */
    public String getCourseAverage(String courseID) {
        //String primary = "select IFNULL(avg(grade),'null') From Grades WHERE Grades.courseID='" + courseID + "' AND Grades.grade is";
        String primary = "select IFNULL(avg(grade),'none') From Grades WHERE Grades.courseID='" + courseID + "' and grade is not null;";

        //String primary = "select grade From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' AND Grades.grade is";
        rs = null;
        String avgGrade = "None";
        //String sql = "select avg(grade) as CourseAverage From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='" + courseID + "' AND Grades.grade is not null ;";
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


    public String getStudentAverage(String ID) {
        String sql = "Select IFNULL(AVG(grade),'none') From Grades Where studentName ='" + ID + "' and grade is not null;";
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

    public void updateGrade(String studentName, String courseID, int grade) {
        String sql = "UPDATE Grades Set grade = " + grade + " Where studentName='"+ studentName + "' and courseID= '" + courseID + "';";
        try {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
