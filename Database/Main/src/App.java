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
        String url = "jdbc:sqlite:StudentsDB.db";
        StudentModel SDB = new StudentModel(url);
        StudentsController control=new StudentsController(SDB);
        StudentsView view=new StudentsView(control);
        control.setView(view);
        primaryStage.setTitle("Student Course Registration");
        primaryStage.setScene(new Scene(view.asParent(), 600, 450));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args){
        launch(args);
    }

}
