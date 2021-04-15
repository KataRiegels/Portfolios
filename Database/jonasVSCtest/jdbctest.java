import java.sql.*;

public class jdbctest {

    public static void main(String[] args) {
        Connection conn = null;
        try {
          String url = "jdbc:sqlite:/home/xilas/Desktop/gits/Portfolios/Database/Main/StudentsDB.db";
          conn = DriverManager.getConnection(url);
    
          System.out.println("Got it!");
    
        } catch (SQLException e) {
            throw new Error("Problem", e);
        } finally {
          try {
            if (conn != null) {
                conn.close();
                
            }
          } catch (SQLException ex) {
              System.out.println(ex.getMessage());
          }
        }
      }
}