
// importing required classes / packages
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "jdbc:sqlite:StudentsDB.db";
        StudentModel SDB = new StudentModel(url);
        StudentsController control=new StudentsController(SDB);
        StudentsView view=new StudentsView(control);//,primaryStage);
        control.setView(view);
        primaryStage.setTitle("Student Course Administration");
        primaryStage.setScene(new Scene(view.asParent(), 600, 450));
        primaryStage.show();
        primaryStage.setResizable(false);
    }

    public static void main(String[] args){
        launch(args);
    }

}
