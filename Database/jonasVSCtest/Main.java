import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    // Global Variables
    Stage window;
    Boolean showStudents;
    String db = "jdbc:sqlite:/home/xilas/Desktop/gits/Portfolios/Database/Main/StudentsDB.db";
    jdbc con = new jdbc(db);
    ListView<String> show;

    public static void main(String[] args) 
    {
        conclear(10); //Temporary command to add space in console
        launch(args);
    }
    public static void conclear(int times)
    {
        for (int i = 0 ; i < times ; i++)
        {
            System.out.println("");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception 
    {
        showStudents = true;

        // Window 

        window = primaryStage;
        window.setTitle("Course Database");

        // Window Sizes
        window.setMinWidth(650);
        window.setMinHeight(400);

        window.setMaxWidth(900);
        window.setMaxHeight(600);

        int[] startSize = {800,450};

        // Window Events
        window.setOnCloseRequest(
        e ->
        {
            e.consume();
            closeProgram();
        });

        // Labels

        Label label1 = new Label("Currently showing students:");
        Label label2 = new Label("Selected student info:");

        // Lists

        show = new ListView<String>();
        show.getItems().addAll(con.getStudents());
        show.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        ListView<String> showlist = new ListView<>();
        showlist.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Text Fields

        TextField nameInput = new TextField("e.g. Joshua");
        TextField passInput = new TextField();
        passInput.setPromptText("8 characters or more");

        // Buttons

        Button viewButton = new Button("Courses");
        Button changeButton = new Button("Change Field");
        Button refresh = new Button("Refresh");

        // Button Events
        refresh.setOnAction(e -> 
        {
            if (showStudents)
            {
                show.getItems().clear();
                show.getItems().addAll(con.getStudents());
            }
            else
            {
                show.getItems().clear();
                show.getItems().addAll(con.getCourses());
            }
        });
        viewButton.setOnAction(e -> 
        {
            //show = con.getCourses();
            if (showStudents)
            {
                viewButton.setText("Students");
                label1.setText("Currently showing courses:");
                show.getItems().clear();
                show.getItems().addAll(con.getCourses());
                showStudents = false;
            }
            else
            {
                viewButton.setText("Courses");
                label1.setText("Currently showing students:");
                show.getItems().clear();
                show.getItems().addAll(con.getStudents());
                showStudents = true;
            }
        }); 

        // Layouts

        VBox leftSide= new VBox(40);
        leftSide.setPadding(new Insets(10,10,10,10));
        leftSide.getChildren().addAll(label1,show,viewButton);

        VBox rightSide = new VBox(40);
        rightSide.setPadding(new Insets(10,10,10,10));
        rightSide.getChildren().addAll(label2,showlist,changeButton);

        VBox middle = new VBox(20);
        middle.getChildren().addAll(refresh);
        middle.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        borderPane.setLeft(leftSide);
        borderPane.setRight(rightSide);
        borderPane.setCenter(middle);
        

        // Scenes
        Scene scene1 = new Scene(borderPane, startSize[0],startSize[1]);
        window.setScene(scene1);
        window.show();
    }
    
    private void closeProgram()
    {
        Boolean answer = AlertBox.display("Before you go!","Are you sure you want to exit!?");
        if (answer)
        {
            window.close();
        }
    }
}
