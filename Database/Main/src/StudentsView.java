

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import javafx.scene.*;

import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
//import javafx.scene.text.*;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class StudentsView {
    private StudentsController control;
    private Pane Startview;
    Button exitBtn   = new Button("Exit");
    Button returnBTN = new Button("Return");

    // null screen
    Label errorMsgLBL = new Label("error: ");
    Button okBTN       = new Button("Okay");


    // Screen 1

    Button continueBTN       = new Button("Continue");
    Label studentOrCourseLBL = new Label("Select course or student:");
    Label scr1InstructionsLBL = new Label("Select an option to see information or add grades.");

    ComboBox<String> selStudentOrCourseCOMB =new ComboBox<>();

    // Screen 2
    Label selectCourseLBL             = new Label("Select course");
    ComboBox<String> selectCourseCOMB = new ComboBox<>();
    TextArea displayCourseInfoTXT     = new TextArea();
    Button confirmCourseBTN           = new Button("Confirm");
    Button addCourseGradeBTN          = new Button("Add grades for course");
    Label scr2InstructionsLBL         = new Label("Choose a course you would like to see information about or add grades.");


    //return btn

    // screen 3
    Label selectStudentLBL             = new Label("Select student");
    ComboBox<String> selectStudentCOMB = new ComboBox<>();
    TextArea displayStudentInfoTXT     = new TextArea();
    Button addGradeBTN                 = new Button("Add grade for student");
    Button confirmStudentBTN           = new Button("Confirm");
    Label scr3InstructionsLBL          = new Label("Choose a student you would like to see information about or add grade.");

    // screen 4
    Label selectedStudentLBL              = new Label("");
    Label selectNullCourseLBL             = new Label("Select course");
    ComboBox<String> selectNullCourseCOMB = new ComboBox<>();
    Label selectGradeLBL                  = new Label("Select a grade");
    ComboBox<String> selectGradeCOMB      = new ComboBox<>();
    Button setGradeBTN                    = new Button("Set grade for student");
    Label confirmGradeUpdate              = new Label("");
    Label scr4InstructionsLBL = new Label("");

    public StudentsView(StudentsController control){
        this.control = control;
        createAndConfigure();
    }



    private void createAndConfigure(){
        startview = new Pane();
        startview.setMinSize(500,500);
        startview.setStyle("-fx-background-color: #d3ccff;");
        nullscreen = new Screen(new Node[]{errorMsgLBL});
        screen1 = new Screen(new Node[] {continueBTN, studentOrCourseLBL, selStudentOrCourseCOMB, scr1InstructionsLBL});
        screen2 = new Screen(new Node[] {selectCourseLBL, selectCourseCOMB, displayCourseInfoTXT, confirmCourseBTN, addCourseGradeBTN, scr2InstructionsLBL});
        screen3 = new Screen(new Node[] {selectStudentLBL, selectStudentCOMB, displayStudentInfoTXT, addGradeBTN, confirmStudentBTN, scr3InstructionsLBL});
        screen4 = new Screen(new Node[] {selectedStudentLBL, selectNullCourseLBL, selectNullCourseCOMB, selectGradeLBL, selectGradeCOMB, setGradeBTN, confirmGradeUpdate, scr4InstructionsLBL});
        screen2.setPrev(screen1);
        screen3.setPrev(screen1);
        screen4.setPrev(screen3);
        nullscreen.setPrev(screen1);




        screens.put("Courses", screen2);
        screens.put("Students", screen3);
        screens.put(null, nullscreen);


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

        // Screen 1
        continueBTN.relocate(250,350);
        //continueBTN.setStyle("-fx-foreground-color: red;");

        studentOrCourseLBL.relocate(100, 200);
        selStudentOrCourseCOMB.relocate(250, 200);
        ObservableList<String> courseOrStudent = makeObsList("Students", "Courses");
        selStudentOrCourseCOMB.setItems(courseOrStudent);
        scr1InstructionsLBL.relocate(100, 25);
        scr1InstructionsLBL.setWrapText(true);
        scr1InstructionsLBL.setMaxWidth(350);
        Font font = Font.font("Arial", 26); //, FontWeight.BLACK
        scr1InstructionsLBL.setFont(font);


        // Screen 2
        selectCourseLBL.relocate(100, 100);
        selectCourseCOMB.relocate(250, 100);
        ObservableList<String> courses = control.getCourseIDs();
        selectCourseCOMB.setItems(courses);
        displayCourseInfoTXT.relocate(100, 200);
        displayCourseInfoTXT.setEditable(false);
        displayCourseInfoTXT.setMaxWidth(400);
        displayCourseInfoTXT.setMaxHeight(150);
        confirmCourseBTN.relocate(250, 150);
        addCourseGradeBTN.relocate(40,400);
        scr2InstructionsLBL.relocate(100, 30);
        scr2InstructionsLBL.setWrapText(true);
        scr2InstructionsLBL.setMaxWidth(400);
        scr2InstructionsLBL.setFont(new Font("Arial", 20));


        //return btn


        // screen 3
        selectStudentLBL.relocate(100,100);
        selectStudentCOMB.relocate(250, 100);
        ObservableList<String> students = control.getStudentNames();
        selectStudentCOMB.setItems(students);
        displayStudentInfoTXT.relocate(80,200);
        displayStudentInfoTXT.setEditable(false);
        displayStudentInfoTXT.setMaxWidth(400);
        displayStudentInfoTXT.setMaxHeight(150);
        addGradeBTN.relocate(40,400);
        confirmStudentBTN.relocate(250,150);
        scr3InstructionsLBL.relocate(100, 30);
        scr3InstructionsLBL.setWrapText(true);
        scr3InstructionsLBL.setMaxWidth(400);
        scr3InstructionsLBL.setFont(new Font("Arial", 20));


        // screen 4
        selectedStudentLBL.setFont(new Font("Arial", 20));
        selectedStudentLBL.setTextFill(Color.color(0.9,0,1));
        selectedStudentLBL.setTextAlignment(TextAlignment.CENTER);
        selectedStudentLBL.relocate(150,65);
        selectNullCourseCOMB.relocate(250,120);
        selectNullCourseLBL.relocate(100,120);
        selectGradeLBL.relocate(100,220);
        selectGradeCOMB.relocate(250,220);
        ObservableList<String> possibleGrades = makeObsList("-3", "00", "02", "4", "7", "10", "12");
        selectGradeCOMB.setItems(possibleGrades);
        setGradeBTN.relocate(100,320);
        confirmGradeUpdate.relocate(250,370);
        scr4InstructionsLBL.relocate(100, 30);
        scr4InstructionsLBL.setWrapText(true);
        scr4InstructionsLBL.setMaxWidth(400);
        scr4InstructionsLBL.setFont(new Font("Arial", 20));


    }

    public ObservableList<String> makeObsList(String... strings){
        ArrayList<String> names= new ArrayList<>();
        for (String str: strings) names.add(str);
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
