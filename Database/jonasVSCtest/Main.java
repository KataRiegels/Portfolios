import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application
{
    Button button;
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
        Stage window = primaryStage;
        window.setTitle("Course Database");

        button = new Button();
        button.setText("Hello There");

        button.setOnAction(
        e -> 
        {
            System.out.println("Kenobi!");
            System.out.println("You've come at last");
        });
        /*button.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event) 
            {
                if (event.getSource() == button)
                {
                    System.out.println("Kenobi!");
                }
            }
        });*/

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout, 800, 450);
        window.setScene(scene);

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

