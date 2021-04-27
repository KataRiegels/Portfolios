

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class StudentsView {
    private StudentsController control;
    private Pane Startview;
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
        this.control = control;
        createAndConfigure();
    }



    private void createAndConfigure(){
        startview = new Pane();
        startview.setMinSize(500,500);

        //Startview.setPadding(new Insets(10,10,10,10));
        //Startview.setVgap(10);
        //Startview.setHgap(10);
        //Startview.setGridLinesVisible(true);
        /*ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPercentWidth(50);*/
        //Startview.getColumnConstraints().addAll(column1, column2,column3); // each get 50% of width

        Node[] screen1 = {continueBTN, studentOrCourseLBL, selStudentOrCourseCOMB};
        Node[] screen2 = { selectCourseLBL, selectCourseCOMB,displayCourseInfoTXT, confirmCourseBTN,returnBTN};
        Node[] screen3 = {selectStudentLBL, selectStudentCOMB, displayStudentInfoTXT, addGradeBTN, confirmStudentBTN};

        startview.getChildren().addAll(screen1.getNodes());

        startview.getChildren().addAll(screen2.getNodes());

        startview.getChildren().addAll(screen3.getNodes());
        startview.getChildren().addAll(screen4.getNodes());
        screen2.addNode(addGradeBTN);


        startview.getChildren().add(exitBtn);
        exitBtn.relocate(550, 20);
        startview.getChildren().add(returnBTN);
        returnBTN.relocate(530, 400);

        // Screen 1
        continueBTN.relocate(250,200);
        studentOrCourseLBL.relocate(100, 100);
        selStudentOrCourseCOMB.relocate(250, 100);
        ObservableList<String> courseOrStudent = makeObsList("Students", "Courses");
        selStudentOrCourseCOMB.setItems(courseOrStudent);


        // Screen 2
        selectCourseLBL.relocate(100, 100);
        selectCourseCOMB.relocate(250, 100);
        ObservableList<String> courses = control.getCourseIDs();
        selectCourseCOMB.setItems(courses);
        displayCourseInfoTXT.relocate(100, 250);
        displayCourseInfoTXT.setMaxWidth(400);
        displayCourseInfoTXT.setMaxHeight(150);
        confirmCourseBTN.relocate(250, 150);
        //return btn


        // screen 3
        selectStudentLBL.relocate(100,100);
        selectStudentCOMB.relocate(250, 100);
        ObservableList<String> students = control.getStudentNames();
        selectStudentCOMB.setItems(students);
        displayStudentInfoTXT.relocate(100,250);
        displayStudentInfoTXT.setMaxWidth(400);
        displayStudentInfoTXT.setMaxHeight(150);
        addGradeBTN.relocate(40,400);
        confirmStudentBTN.relocate(250,150);


        // screen 4
        selectedStudentLBL.relocate(100,50);
        selectNullCourseCOMB.relocate(250,70);
        selectNullCourseLBL.relocate(100,70);
        selectGradeLBL.relocate(100,180);
        selectGradeCOMB.relocate(250,180);
        ObservableList<String> possibleGrades = makeObsList("-3", "00", "02", "4", "7", "10", "12");
        selectGradeCOMB.setItems(possibleGrades);
        setGradeBTN.relocate(100,300);
        confirmGradeUpdate.relocate(250,350);
    }

    public ObservableList<String> makeObsList(String... strings){
        ArrayList<String> names= new ArrayList<>();
        for (String str: strings) names.add(str);
        //names.add("Students");
        //names.add("Course");
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }

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




    public Parent asParent(){
        return startview;
    }




}

class Screen{
    Node[] nodes;
    Screen prev;

    public Screen(Node[] nodes){
        this.nodes = nodes;
        this.prev = this;
    }

    public void setPrev(Screen prev){
        this.prev = prev;
    }

    public void setNodes(Node[] nodes){
        this.nodes = nodes;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Screen getPrev() {
        return prev;
    }

    public void hide(){
        for(Node node : nodes ){
            node.setVisible(false);
        }
    }

    public void show(){
        for(Node node : nodes ){
            node.setVisible(true);
        }
    }

    public void addNode(Node node){
        nodes = Arrays.copyOf(nodes, nodes.length+1);
        nodes[nodes.length-1] = node;

    }

}
