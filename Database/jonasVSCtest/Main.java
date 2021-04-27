import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener.Change;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application
{
    // Global Variables
    Stage window;
    Boolean showStudents;
    String db = "jdbc:sqlite:Students.db";
    jdbc con = new jdbc(db);
    ListView<String> showLeft,showRight;

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

        showLeft= new ListView<String>();
        showLeft.getItems().addAll(con.getStudents());
        showLeft.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        
        showRight = new ListView<String>();
        showRight.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        // Buttons

        Button viewButton = new Button("Courses");
        Button detailsButton = new Button("Details");
        Button refresh = new Button("Refresh");

        // Button Events

        refresh.setOnAction(e -> 
        {
            showRight.getItems().clear();
            if (showStudents)
            {
                showLeft.getItems().clear();
                showLeft.getItems().addAll(con.getStudents());
            }
            else
            {
                showLeft.getItems().clear();
                showLeft.getItems().addAll(con.getCourses());
            }
        });

        viewButton.setOnAction(e -> 
        {
            showRight.getItems().clear();
            if (showStudents)
            {
                viewButton.setText("Students");
                label1.setText("Currently showing courses:");
                showLeft.getItems().clear();
                showLeft.getItems().addAll(con.getCourses());
                showStudents = false;
            }
            else
            {
                viewButton.setText("Courses");
                label1.setText("Currently showing students:");
                showLeft.getItems().clear();
                showLeft.getItems().addAll(con.getStudents());
                showStudents = true;
            }
        }); 

        detailsButton.setOnAction(e -> 
        {
            String currentTopic = showRight.getSelectionModel().getSelectedItem();
            String currentTable = showLeft.getSelectionModel().getSelectedItem();
            String[] temp = currentTopic.split(" ");
            
            String str = temp[temp.length-2] + temp[temp.length-1];
            if (str.equals("NotGraded") && showStudents)
            {
                String out = ChangeBox.display("Change Grade", "Input:");
                if (out != null)
                {
                    con.setGrade(out,currentTable,temp[1]);
                    showRight.getItems().clear();
                    showRight.getItems().addAll(con.getStudent(currentTable));
                }
            }
        });

        // ListView Listeners

        showLeft.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String current = newValue;
                if (showStudents)
                {
                    showRight.getItems().clear();
                    showRight.getItems().addAll(con.getStudent(current));
                } 
                else
                {
                    showRight.getItems().clear();
                    showRight.getItems().addAll(con.getCourse(current));
                }
                }
        });

        // Layouts

        VBox leftSide= new VBox(40);
        leftSide.setPadding(new Insets(10,10,10,10));
        leftSide.getChildren().addAll(label1,showLeft,viewButton);
        leftSide.setAlignment(Pos.CENTER);

        VBox rightSide = new VBox(40);
        rightSide.setPadding(new Insets(10,10,10,10));
        rightSide.getChildren().addAll(label2,showRight,detailsButton);
        rightSide.setAlignment(Pos.CENTER);

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
        Boolean answer = AlertBox.display("Before you go!","Are you sure you want to exit!?",false);
        if (answer)
        {
            window.close();
        }
    }
}
