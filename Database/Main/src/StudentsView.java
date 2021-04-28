
// importing required classes / packages
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.paint.Color;
import java.util.*;


public class StudentsView {
    private StudentsController control;
    private Pane startview;
    public Screen nullscreen, screen1, screen2, screen3, screen4;
    HashMap<String, Screen> screens = new HashMap<>();

    // Creating buttons present on each screen
    Button exitBtn   = new Button("Exit");
    Button returnBTN = new Button("Return");

    // Creating labels, buttons etc. for each individual screen:

    // Screen 1: for choosing to view information about either courses or students
    ComboBox<String> selStudentOrCourseCOMB =new ComboBox<>();
    Button continueBTN        = new Button("Continue");
    Label studentOrCourseLBL  = new Label ("Select \"Courses\" or \"Students\"");
    Label scr1InstructionsLBL = new Label ("Select an option to see information or add grades.");

    // Error screen: if nothing is selected on screen 1
    Label errorMsgLBL = new Label(
            "Error: No option selected.\n" +
                "Select \"Courses\" to view information about a course or add grades for a course.\n" +
                "Select \"Students\" to view information about a student or add grades for a student." );

    // Screen 2: selecting a course to view information about it
    Label selectCourseLBL             = new Label("Select course");
    ComboBox<String> selectCourseCOMB = new ComboBox<>();
    TextArea displayCourseInfoTXT     = new TextArea();
    Button confirmCourseBTN           = new Button("Confirm");
    Button addCourseGradeBTN          = new Button("Add grades for course");
    Label scr2InstructionsLBL         = new Label("Choose a course to view information or add grades.");

    // Screen 3: selecting a student to view information about them
    Label selectStudentLBL             = new Label("Select student");
    ComboBox<String> selectStudentCOMB = new ComboBox<>();
    TextArea displayStudentInfoTXT     = new TextArea();
    Button addGradeBTN                 = new Button("Add grade for student");
    Button confirmStudentBTN           = new Button("Confirm");
    Label scr3InstructionsLBL          = new Label("Choose a student to view information or add grades.");

    // Screen 4: adding grades to ungraded courses/students
    Label selectedStudentLBL              = new Label("");
    Label selectNullCourseLBL             = new Label("Select course");
    ComboBox<String> selectNullCourseCOMB = new ComboBox<>();
    Label selectGradeLBL                  = new Label("Select grade");
    ComboBox<String> selectGradeCOMB      = new ComboBox<>();
    Button setGradeBTN                    = new Button("Set grade for student");
    Label confirmGradeUpdate              = new Label("");
    Label scr4InstructionsLBL             = new Label("");


    public StudentsView(StudentsController control){
        this.control = control;
        createAndConfigure();
    }

    // Configuring the settings for everything (i.e. position, color, font size etc.)
    private void createAndConfigure(){
        // Settings for the view
        startview = new Pane();
        startview.setMinSize(500,500);
        startview.setStyle("-fx-background-color: #d3ccff;");

        // Adding all nodes to their corresponding screen
        nullscreen = new Screen(new Node[]{errorMsgLBL});
        screen1    = new Screen(new Node[]{continueBTN, studentOrCourseLBL, selStudentOrCourseCOMB, scr1InstructionsLBL});
        screen2    = new Screen(new Node[]{selectCourseLBL, selectCourseCOMB, displayCourseInfoTXT, confirmCourseBTN, addCourseGradeBTN, scr2InstructionsLBL});
        screen3    = new Screen(new Node[]{selectStudentLBL, selectStudentCOMB, displayStudentInfoTXT, addGradeBTN, confirmStudentBTN, scr3InstructionsLBL});
        screen4    = new Screen(new Node[]{selectedStudentLBL, selectNullCourseLBL, selectNullCourseCOMB, selectGradeLBL, selectGradeCOMB, setGradeBTN, confirmGradeUpdate, scr4InstructionsLBL});

        // Setting initial previous screen for each screen
        screen2.setPrev(screen1);
        screen3.setPrev(screen1);
        screen4.setPrev(screen3);
        nullscreen.setPrev(screen1);

        screens.put("Courses", screen2);
        screens.put("Students", screen3);
        screens.put(null, nullscreen);

        // Adding all nodes to the view
        startview.getChildren().addAll(screen1.getNodes());
        startview.getChildren().addAll(screen2.getNodes());
        startview.getChildren().addAll(screen3.getNodes());
        startview.getChildren().addAll(screen4.getNodes());
        startview.getChildren().addAll(nullscreen.getNodes());
        startview.getChildren().add(exitBtn);
        startview.getChildren().add(returnBTN);

        // Exit button and Return button settings
        exitBtn.relocate(550, 20);
        returnBTN.relocate(530, 400);
        returnBTN.setVisible(false);

        // Error screen settings
        errorMsgLBL.relocate(50, 100);
        errorMsgLBL.setTextFill(Color.RED);
        Font font = Font.font("Arial", 14); //, FontWeight.BLACK
        errorMsgLBL.setFont(font);

        // Screen 1 settings
        continueBTN.relocate(250,350);
        studentOrCourseLBL.relocate(85, 200);
        selStudentOrCourseCOMB.relocate(250, 200);
        ObservableList<String> courseOrStudent = makeObsList("Students", "Courses");
        selStudentOrCourseCOMB.setItems(courseOrStudent);
        scr1InstructionsLBL.relocate(100, 25);
        scr1InstructionsLBL.setWrapText(true);
        scr1InstructionsLBL.setMaxWidth(350);
        scr1InstructionsLBL.setFont(font);

        // Screen 2 settings
        selectCourseLBL.relocate(140, 100);
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

        // Screen 3 settings
        selectStudentLBL.relocate(140,100);
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

        // screen 4 settings
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

// Screen class for storing nodes and previous screen
class Screen{
    Node[] nodes;
    Screen prev;
    //String trigger = "none";

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
