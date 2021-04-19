

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;




public class StudentsView {
    private StudentsController control;
    private GridPane Startview;
    Button exitBtn=new Button("Exit");
    Button FindTrainsBtn=new Button("Find Trains");
    Label StartstationLbl=new Label("Select start station:");
    Label EndstationLbl=new Label("Select destination:");
    //Label TimeLbl = new Label("Select earliest time:");
    TextArea TrainText = new TextArea();
    ComboBox<String> StartStationComB=new ComboBox<>();
    ComboBox<String> EndStationComB=new ComboBox<>();
    //ComboBox<Integer> HourComB =new ComboBox<>();
    //ComboBox<Integer> MinuteComB =new ComboBox<>();

    public StudentsView(StudentsController control){
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview=new GridPane();
        Startview.setMinSize(300,200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);
        Startview.add(exitBtn, 20,15);
        Startview.add(FindTrainsBtn,15,6);
        Startview.add(StartstationLbl,1,1);
        Startview.add(EndstationLbl,1,3);
        Startview.add(TrainText,1,7,15,7);

        Startview.add(StartStationComB, 15,1);
        Startview.add(EndStationComB,15,3);
        Startview.add(HourComB,13,5);
        Startview.add(MinuteComB,15,5);

        ObservableList<String> stations=control.getStations();
        StartStationComB.setItems(stations);
        EndStationComB.setItems(stations);
        StartStationComB.getSelectionModel().selectFirst();
        EndStationComB.getSelectionModel().selectFirst();
        //HourComB.setItems(control.getHours());
        HourComB.getSelectionModel().selectFirst();
        //MinuteComB.setItems(control.getMinutes());
        MinuteComB.getSelectionModel().selectFirst();




    }

    public Parent asParent(){
        return Startview;
    }


}


