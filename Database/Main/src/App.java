import java.sql.*;

import static java.sql.DriverManager.getConnection;

public class App {
    public static void main(String[] args) throws Exception {
        String query = "select studentName,grade from CourseGrade ";
        Connection conn = null;

        try{
            conn = connect();
            poop(conn, query);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn!=null){
                try{
                    conn.close();
                } catch (SQLException ex){
                    ex.printStackTrace();
                }
            }
        }


    }

    public static Connection connect() throws SQLException{
        Connection conn = null;
        String url = "jdbc:sqlite:C:\\Users\\Mitzie\\Documents\\4th_sem\\Software development\\Portfolios\\Database\\Main\\StudentsDB.db";
        conn = DriverManager.getConnection(url);
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
}
