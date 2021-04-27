import java.sql.*;
import java.util.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import static java.sql.DriverManager.getConnection;

public class App extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        //String query = "select studentName,grade from CourseGrade ";
        String url = "jdbc:sqlite:C:\\Users\\Mitzie\\Documents\\4th_sem\\Software development\\Portfolios\\Database\\Main\\StudentsDB.db";
        StudentModel SDB = new StudentModel(url);

        //String url = "jdbc:sqlite:StudentsDB.db";
        StudentsController control=new StudentsController(SDB);
        StudentsView view=new StudentsView(control);
        control.setView(view);
        primaryStage.setTitle("Train Trip Finder");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}
