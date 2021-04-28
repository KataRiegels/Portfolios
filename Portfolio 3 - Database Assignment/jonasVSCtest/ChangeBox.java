import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ChangeBox {

    static String answer;

    public static String display(String title, String message)
    {
        // Window
        answer = null;
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(250);
        window.setMinHeight(150);

        // Labels
        Label label = new Label();
        label.setText(message);

        // Text Fields
        TextField text = new TextField();
        text.setPromptText("New Grade");

        // Buttons
        Button confirm = new Button("Confirm");
        Button cancel = new Button("Cancel");

        // Button events
        confirm.setOnAction(e ->
        {
            String temp = text.getText();
            try {
                Integer.parseInt(temp);
                answer = temp;
            }
            finally
            {
                window.close();
            }
        });

        cancel.setOnAction(e ->
        {
            window.close();
        });

        // Layouts
        HBox layout1 = new HBox();
        layout1.getChildren().addAll(confirm,cancel);
        layout1.setAlignment(Pos.CENTER);

        VBox layout2 = new VBox(10);
        layout2.getChildren().addAll(label,text,layout1);
        layout2.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(layout2);

        // Show & Return
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}