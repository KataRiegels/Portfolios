

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;


public class StudentsView {
    private StudentsController control;
    private GridPane Startview;
    Button exitBtn   = new Button("Exit");
    Button returnBTN = new Button("Return");

    // Screen 1
    Button continueBTN       = new Button("Continue");
    Label studentOrCourseLBL = new Label("Select course or student:");
    ComboBox<String> selStudentOrCourseCOMB =new ComboBox<>();

    // Screen 2
    Label selectCourseLBL             = new Label("Select course");
    ComboBox<String> selectCourseCOMB = new ComboBox<>();
    TextArea displayCourseInfoTXT     = new TextArea();
    Button confirmCourseBTN           = new Button("Confirm");
    //return btn

    // screen 3
    Label selectStudentLBL             = new Label("Select student");
    ComboBox<String> selectStudentCOMB = new ComboBox<>();
    TextArea displayStudentInfoTXT     = new TextArea();
    Button addGradeBTN                 = new Button("Add grade for student");
    Button confirmStudentBTN           = new Button("Confirm");


    // screen 4
    Label selectedStudentLBL              = new Label("");
    Label selectNullCourseLBL             = new Label("Select course");
    ComboBox<String> selectNullCourseCOMB = new ComboBox<>();
    Label selectGradeLBL                  = new Label("Select a grade");
    ComboBox<String> selectGradeCOMB      = new ComboBox<>();
    Button setGradeBTN                    = new Button("Set grade for student");


    public StudentsView(StudentsController control){
        this.control=control;
        createAndConfigure();
    }

    private void createAndConfigure(){
        Startview = new GridPane();
        Startview.setMinSize(300,200);
        Startview.setPadding(new Insets(10,10,10,10));
        Startview.setVgap(5);
        Startview.setHgap(1);


        Startview.add(exitBtn, 20,40);
        /*
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

         */




    }

    public Parent asParent(){
        return Startview;
    }


}


