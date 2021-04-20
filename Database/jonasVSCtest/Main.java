import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    // JavaFX window elements
    Button button1,button2;
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
        window = primaryStage;
        window.setTitle("Course Database");

        Label label1 = new Label("Welcome to the first scene");

        button1 = new Button();
        button1.setText("Hello There");

        button2 = new Button();
        button2.setText("Kenobi!");

        button1.setOnAction(
        e -> 
        {
            window.setScene(scene2);
        });
        button2.setOnAction(
        e -> 
        {
            window.setScene(scene1);
        });

        /*
        // Example of event handler that uses class, 'this' in this case
        button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if (event.getSource() == button)
                {
                    System.out.println("Kenobi!");
                }
            }
        });
        */

        // Layout 1
        VBox layout1 = new VBox(20);
        layout1.getChildren().addAll(label1,button1);
        scene1 = new Scene(layout1, 800, 450);

        // Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 800, 450);

        window.setScene(scene1);

        window.show();
    }
    
    /*@Override
    public void handle(ActionEvent event) 
    {
        if (event.getSource() == button)
        {
            System.out.println("Kenobi!");
        }
    }*/
}

