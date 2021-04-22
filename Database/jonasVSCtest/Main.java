import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    // JavaFX window elements
    Button button1,button2,topMenu1,topMenu2,sideMenu1,sideMenu2;
    Stage window;
    Scene scene1,scene2;

    public static void main(String[] args) 
    {
        conclear(10); //Temporary command to add space in console
        String db = "jdbc:sqlite:/home/xilas/Desktop/gits/Portfolios/Database/Main/StudentsDB.db";
        jdbc con = new jdbc(db);
        //con.getStudent("Aisha Lincoln");

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
        // Window 
        window = primaryStage;
        window.setTitle("Course Database");

        window.setOnCloseRequest(
        e ->
        {
            e.consume();
            closeProgram();
        });

        // Labels
        Label label1 = new Label("Welcome to the first scene");
        Label nameLabel = new Label("Username");
        Label passLabel = new Label("Password");

        // Text Fields
        TextField nameInput = new TextField("e.g. Joshua");
        TextField passInput = new TextField();
        passInput.setPromptText("8 characters or more");

        // Buttons
        button1 = new Button();
        button2 = new Button();
        topMenu1 = new Button();
        topMenu2 = new Button();
        sideMenu1 = new Button();
        sideMenu2 = new Button();
        button1.setText("Hello There");
        button2.setText("Kenobi!");
        topMenu1.setText("File");
        topMenu2.setText("View");
        sideMenu1.setText("Stuff");
        sideMenu2.setText("Morestuff");

        button1.setOnAction(
        e -> 
        {
            window.setScene(scene2);
        });
        button2.setOnAction(
        e -> 
        {
            window.setScene(scene1);
            boolean result = ConfirmBox.display("Confirm your answer!","Are you a jedi?");
            System.out.println(result);
            if (!result)
            {
                closeProgram();
            }
        });

        // Layouts
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10,10,10,10));
        grid.setVgap(8);
        grid.setHgap(10);
        GridPane.setConstraints(nameLabel, 0, 0);
        GridPane.setConstraints(nameInput, 1, 0);
        GridPane.setConstraints(passLabel, 0, 1);
        GridPane.setConstraints(passInput, 1, 1);

        grid.getChildren().addAll(nameLabel,nameInput,passLabel,passInput);

        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,button1);

        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);

        HBox topMenu = new HBox();
        topMenu.getChildren().addAll(topMenu1,topMenu2);

        VBox sideMenu = new VBox();
        sideMenu.getChildren().addAll(sideMenu1,sideMenu2);

        BorderPane borderPane = new BorderPane();
        borderPane.setTop(topMenu);
        borderPane.setLeft(sideMenu);

        // Scenes
        scene1 = new Scene(grid, 800, 450);
        scene2 = new Scene(layout2, 800, 450);
        window.setScene(scene1);
        window.show();
    }
    
    private void closeProgram()
    {
        Boolean answer = ConfirmBox.display("Before you go!","Are you sure you want to exit!?");
        if (answer)
        {
            window.close();
        }
    }
}
