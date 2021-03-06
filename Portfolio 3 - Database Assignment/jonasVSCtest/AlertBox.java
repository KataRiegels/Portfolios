import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    static boolean answer;

    public static Boolean display(String title, String message, boolean notification)
    {
        // Window
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL); // Have to close this before doing anything else
        window.setTitle(title);
        window.setMinWidth(250);

        // Labels
        Label label = new Label();
        label.setText(message);

        // Buttons
        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        // Button events
        yesButton.setOnAction(e ->
        {
            answer = true;
            window.close();
        });
        noButton.setOnAction(e ->
        {
            answer = false;
            window.close();
        });

        // Layouts
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yesButton,noButton);
        layout.setAlignment(Pos.CENTER);

        // Scene
        Scene scene = new Scene(layout);

        // Startup
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }
}
