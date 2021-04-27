

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

    // null screen
    Label errorMsgLBL = new Label("error:");
    Button okBTN       = new Button("Okay");


    // Screen 1
    Button continueBTN       = new Button("Continue");
    Label studentOrCourseLBL = new Label("Select course or student:");
    ComboBox<String> selStudentOrCourseCOMB =new ComboBox<>();

    // Screen 2
    Label selectCourseLBL             = new Label("Select course");
    ComboBox<String> selectCourseCOMB = new ComboBox<>();
    TextArea displayCourseInfoTXT     = new TextArea();
    Button confirmCourseBTN           = new Button("Confirm");
    Button addCourseGradeBTN          = new Button("Add grades for course");

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

        nullscreen = new Screen(new Node[]{errorMsgLBL});
        screen1 = new Screen(new Node[] {continueBTN, studentOrCourseLBL, selStudentOrCourseCOMB});
        screen2 = new Screen(new Node[] {selectCourseLBL, selectCourseCOMB, displayCourseInfoTXT, confirmCourseBTN, addCourseGradeBTN});
        screen3 = new Screen(new Node[] {selectStudentLBL, selectStudentCOMB, displayStudentInfoTXT, addGradeBTN, confirmStudentBTN});
        screen4 = new Screen(new Node[] {selectedStudentLBL, selectNullCourseLBL, selectNullCourseCOMB, selectGradeLBL, selectGradeCOMB, setGradeBTN, confirmGradeUpdate});
        screen2.setPrev(screen1);
        screen3.setPrev(screen1);
        screen4.setPrev(screen3);
        nullscreen.setPrev(screen1);



        screens.put("Courses", screen2);
        screens.put("Students", screen3);
        screens.put(null, nullscreen);

        //screens.put("Add grade", screen4);

        startview.getChildren().addAll(screen1.getNodes());

        startview.getChildren().addAll(screen2.getNodes());

        startview.getChildren().addAll(screen3.getNodes());
        startview.getChildren().addAll(screen4.getNodes());
        startview.getChildren().addAll(nullscreen.getNodes());


        startview.getChildren().add(exitBtn);
        exitBtn.relocate(550, 20);
        startview.getChildren().add(returnBTN);
        returnBTN.relocate(530, 400);

        // null screen
        errorMsgLBL.relocate(50, 100);
        nullscreen.setTrigger(null);


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
        addCourseGradeBTN.relocate(40,400);
        screen2.setTrigger(courseOrStudent.get(1));
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
        screen2.setTrigger(courseOrStudent.get(0));


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






    public Parent asParent(){
        return startview;
    }
}

class Screen{
    Node[] nodes;
    Screen prev;
    String trigger = "none";

    public Screen(Node[] nodes){
        this.nodes = nodes;
        this.prev = this;
    }

    public String getTrigger(){
        return trigger;
    }

    public void setTrigger(String trigger) {
        this.trigger = trigger;
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
