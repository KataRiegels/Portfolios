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

    StudentModel(String url){
        this.url = url;
    }

    public void connect() throws SQLException {
        conn=getConnection(url);
    }
    public void close() throws SQLException {
        if (conn != null)
            conn.close();
    }
    public void createStatement() throws SQLException{
        this.stmt= conn.createStatement();
    }

    public ArrayList<String> SQLQueryStudentNames(){
        ArrayList<String> Names = new ArrayList<>();
        String sql="Select courseID From Courses;";
        try {
            rs = stmt.executeQuery(sql);
            while (rs != null && rs.next()){
                String name = rs.getString(1);
                Names.add(name);
            }
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
        rs=null;
        return Names;
    }
    public double getStudentAverage(String ID){
        String sql="Select AVG(grade) From Grades Where studentName ='" + ID + "' and grade is not null;";
        double avgGrade = 0.0;
        try{
            rs = stmt.executeQuery(sql);
            avgGrade = rs.getDouble(1);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return avgGrade;
    }



    public String getCourseAverage(String courseID){
        String sql = "select avg(grade) as CourseAverage From Grades INNER JOIN Courses ON Grades.courseID=Courses.courseID WHERE Grades.courseID='"+ courseID +"' ;";
        String avgGrade = "None";

        try{
            //connect();
            //PreparedStatement pstmtAvgCourse = conn.prepareStatement(sql);
            //pstmtAvgCourse.setString(1, )
            rs = stmt.executeQuery(sql);
            //System.out.println(rs.getDouble(1));
            if (rs == null){
                avgGrade = "Course has not been graded";
            }else  avgGrade = Double.toString(rs.getDouble(1));
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
        return avgGrade;

    }


}
