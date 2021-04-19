

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;


public class StudentsView {
    private StudentsController control;
    private GridPane Startview;
    Button exitBtn=new Button("Exit");
    Button getCourseAverageBTN=new Button("Get course average");
    Button continueBTN=new Button("Continue");

    Label courseSelectionLBL =new Label("Select course:");
    Label test2 =new Label("Select student:");

    //Label TimeLbl = new Label("Select earliest time:");
    Text TrainText = new Text();
    TextArea test = new TextArea();

    ComboBox<String> selectCourseCOMB =new ComboBox<>();
    //ComboBox<String> EndStationComB=new ComboBox<>();
    ComboBox<String> selStudentOrCourse =new ComboBox<>();


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
        Startview.add(exitBtn, 20,40);
        Startview.add(getCourseAverageBTN,15,6);
        Startview.add(continueBTN,20,6);

        getCourseAverageBTN.setVisible(true);
        Startview.add(courseSelectionLBL,1,1);
        //Startview.add(EndstationLbl,1,3);
        Startview.add(TrainText,1,7,15,7);
        Startview.add(test,50,50,10,10);
        test.setEditable(false);
        Startview.add(test2,50,50,10,10);
        test2.setVisible(false);
        Startview.add(selStudentOrCourse, 15, 4);
        Startview.add(selectCourseCOMB, 17,1);
        selectCourseCOMB.setVisible(false);
        //Startview.add(EndStationComB,15,3);

        ObservableList<String> stations=control.getStudentNames();
        ObservableList<String> studVScourse = control.studVScourse();

        selectCourseCOMB.setItems(stations);
        //EndStationComB.setItems(stations);
        selectCourseCOMB.getSelectionModel().selectFirst();
        selStudentOrCourse.setItems(studVScourse);
        selStudentOrCourse.getSelectionModel().selectFirst();

        //EndStationComB.getSelectionModel().selectFirst();




    }

    public Parent asParent(){
        return Startview;
    }


}


