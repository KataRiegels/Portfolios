import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        String url="jdbc:sqlite:C:/Users/liner/Documents/SD/SD2021S/FridayApril9thTrainDB.db";
        TrainModel TDB=new TrainModel(url);
        TrainController control=new TrainController(TDB);
        TrainView view=new TrainView(control);
        control.setView(view);
        primaryStage.setTitle("Train Trip Finder");
        primaryStage.setScene(new Scene(view.asParent(), 600, 475));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

/*      @Override
      public  void start(Stage primaryStage) throws Exception{
          TrainView view= new TrainView();
          primaryStage.setTitle("Train Trip Finder");
          primaryStage.setScene(new Scene(view.asParent(), 600,475));
          primaryStage.show();
      }

      public static void main(String[] args){
          launch(args);
      }
*/
/*    public static void main(String[] args) {
        String url="jdbc:sqlite:C:/Users/liner/Documents/SD/SD2021S/FridayApril9thTrainDB.db";
        TrainModel TDB=new TrainModel(url);
        try {
            TDB.connect();
            TDB.CreateStatement();
            ArrayList<String> names=TDB.SQLQueryStationNames();
            TDB.PrintStations(names);
            TDB.StationInfoQuery();
            TDB.PreparedStmtFindTripsQuert();
            TDB.FindTrainTrips2("Høje Tåstrup", "Ringsted", 3.00);
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        finally {
            try {
                TDB.close();
            }catch (SQLException ex)
            {
                System.out.println(ex.getMessage());
            }
        }
    }
 */
}
