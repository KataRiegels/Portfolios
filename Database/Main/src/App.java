import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;

public class App {
    public static void main(String[] args) throws Exception {
        //String query = "select studentName,grade from CourseGrade ";
        //String url = "jdbc:sqlite:C:\\Users\\Mitzie\\Documents\\4th_sem\\Software development\\Portfolios\\Database\\Main\\StudentsDB.db";
        String url = "jdbc:sqlite:StudentsDB.db";
        StudentModel SDB = new StudentModel(url);

        try{
            SDB.connect();
            SDB.createStatement();
            ArrayList<String> names = SDB.SQLQueryStudentNames();
            double ag = SDB.getStudentAverage("Anya Nielsen");
            String agC = SDB.getCourseAverage("SD", 2020, "spring");
            System.out.println("Course avg: " + agC);
            System.out.println(ag);
            System.out.println(names.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try{
                SDB.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    /*
    public static Connection connect() throws SQLException{
        Connection conn = null;
        //String url = "jdbc:sqlite:C:\\Users\\Mitzie\\Documents\\4th_sem\\Software development\\Portfolios\\Database\\Main\\StudentsDB.db";
        //conn = DriverManager.getConnection(url);
        return conn;
    }

    public static void poop(Connection conn, String query){
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String studentName = rs.getString("studentName");
                System.out.println(studentName);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    */

}
