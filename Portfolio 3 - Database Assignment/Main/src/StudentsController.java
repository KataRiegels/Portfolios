// importing required classes / packages
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.paint.Color;
import java.sql.SQLException;
import java.util.ArrayList;


// StudentsController class that uses StudentModel
public class StudentsController 
{
    // Variables
    StudentModel model;
    StudentsView view;
    Screen currentScreen;

    // Init
    public StudentsController(StudentModel model)
    {
        this.model=model;
        try 
        {
            model.connect();
            model.createStatement();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void setView(StudentsView view) {
        this.view = view;                                   // Set view and initial current screen
        currentScreen = view.screen1;
        view.exitBtn.setOnAction(e-> Platform.exit());      // Event for exit button

        // Initially hide each screen except Screen 1
        view.nullscreen.hide();
        view.screen2.hide();
        view.screen3.hide();
        view.screen4.hide();

        // Event handlers for each button:
        EventHandler<ActionEvent> returnEvent        = e->goTo(currentScreen, currentScreen.getPrev());
        EventHandler<ActionEvent> studentOrCourse    = e->goTo(view.screen1, view.screens.get(view.selStudentOrCourseCOMB.getValue()));
        EventHandler<ActionEvent> addingGrades       = e->goToStudentGrade(currentScreen, view.screen4, view.selectStudentCOMB.getValue());
        EventHandler<ActionEvent> addingCourseGrades = e->goToCourseGrade(currentScreen, view.screen4, view.selectCourseCOMB.getValue());
        EventHandler<ActionEvent> courseConfirm      = e->courseInfoBox(view.selectCourseCOMB.getValue());
        EventHandler<ActionEvent> studentConfirm     = e->studentInfoBox(view.selectStudentCOMB.getValue());
        EventHandler<ActionEvent> updateGrade        = e->setNewGrade(view.selectGradeCOMB.getValue());

        // setting events for each button
        view.continueBTN      .setOnAction(studentOrCourse);
        view.returnBTN        .setOnAction(returnEvent);
        view.addGradeBTN      .setOnAction(addingGrades);
        view.confirmCourseBTN .setOnAction(courseConfirm);
        view.confirmStudentBTN.setOnAction(studentConfirm);
        view.setGradeBTN      .setOnAction(updateGrade);
        view.addCourseGradeBTN.setOnAction(addingCourseGrades);
    }

    // Event for adding a grade
    public void setNewGrade(String grade)
    {
        if (view.selectNullCourseCOMB.getValue() == null || grade == null)                  // first checks if a course/student and a grade where selected
        {
            // error message if course/student and/or grade were not selected
            view.confirmGradeUpdate.setTextFill(Color.RED);
            view.confirmGradeUpdate.setText("Please select both a course and grade.");
        } else 
        {
            String student, courseID;
            if (currentScreen.getPrev().equals(view.screen2))                               // reading selected student and course, depending on previous screen
            {
                student  = view.selectNullCourseCOMB.getValue();
                courseID = view.selectCourseCOMB    .getValue();
            } 
            else 
            {
                student  = view.selectStudentCOMB   .getValue();
                courseID = view.selectNullCourseCOMB.getValue();
            }
            // Updating the grade in the database
            model.updateGrade(student, courseID, Integer.parseInt(grade));
            view.confirmGradeUpdate.setTextFill(Color.BLACK);
            view.confirmGradeUpdate.setText("Grade has been updated");
        }
        // Clearing the selection from combo boxes
        view.selectCourseCOMB .getSelectionModel().clearSelection();
        view.selectStudentCOMB.getSelectionModel().clearSelection();
    }

    // Queries course information from database and updates the text area to display information
    public void courseInfoBox(String courseID)
    {
        view.displayCourseInfoTXT.clear();
        if (courseID == null)                                                               // First checks if a course was selected
        {
            // Error message if no course was selected
            view.displayCourseInfoTXT.setStyle("-fx-text-inner-color: #ff0000;");
            view.displayCourseInfoTXT.appendText("No course selected");
        } 
        else 
        {
            // Get course name, teacher and course average from database
            ArrayList<String> courseName = model.getCourseName   (courseID);
            String teacherName           = model.getTeacherName  (courseID);
            String courseAvg             = model.getCourseAverage(courseID);
            
            // Update text area
            view.displayCourseInfoTXT.setStyle("-fx-text-inner-color: #000000;");
            view.displayCourseInfoTXT.appendText(
                    "Selected course: " + courseName.get(0) + " - " + courseName.get(2) + " - " + courseName.get(1) + "\n\n" +
                       "Teacher: " + teacherName + "\n\n" +
                       "Average grade: " + courseAvg);
        }
    }

    // Queries student information from database and updates the text area to display information
    public void studentInfoBox(String studentName) 
    {
        view.displayStudentInfoTXT.clear();
        
        if (studentName == null)                                                            // First checks if a student was selected
        {
            // Error message if no student was selected
            view.displayStudentInfoTXT.setStyle("-fx-text-inner-color: #ff0000;");
            view.displayStudentInfoTXT.appendText("No student selected");
        } 
        else
        {
            // Get grade for each course and student average from database
            ArrayList<String[]> courseGrade = model.getCourseAndGrade(studentName);
            String studentAvg               = model.getStudentAverage(studentName);

            ArrayList<String> courseName;
            view.displayStudentInfoTXT.setStyle("-fx-text-inner-color: #000000;");
            view.displayStudentInfoTXT.appendText("Selected student: " + studentName + "\n\nCourses:\n");
            
            // Display taken courses and grade for each
            for(String[] course : courseGrade)
            {
                courseName = model.getCourseName(course[0]);
                view.displayStudentInfoTXT.appendText(
                        courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2) +
                           " - Grade: " + course[1] +  "\n");
            }
            // Display student average
            view.displayStudentInfoTXT.appendText("\nAverage grade: " + studentAvg);
        }
    }

    // Goes to screen 4 for adding a grade for an ungraded student
    public void goToStudentGrade(Screen oldscreen, Screen newscreen, String name)
    {
        ObservableList<String> ungradedCourses = getUngradedCourseIDs(name);
        if (ungradedCourses.isEmpty())                                                      // First checks if there are ungraded courses for the student
        {
            // Error message if all courses are graded or no student was selected
            view.displayStudentInfoTXT.setStyle("-fx-text-inner-color: #ff0000;");
            view.displayStudentInfoTXT.clear();
            view.displayStudentInfoTXT.appendText(
                    "Error: Cannot add grade.\n" +
                       "Please review student selection.");
        }
        else 
        {
            // Add labels and combo box for selecting an ungraded course
            view.scr4InstructionsLBL .setText("Select a course and grade to add it.");
            view.selectedStudentLBL  .setText(name);
            view.selectNullCourseLBL .setText("Select course");
            view.selectNullCourseCOMB.setItems(ungradedCourses);
            
            // Update old and new screen
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
        }
    }

    // Goes to screen 4 for adding a grade for an ungraded course
    public void goToCourseGrade(Screen oldscreen, Screen newscreen, String courseID)
    {
        ObservableList<String> ungradedStudents = getUngradedStudents(courseID);
        if (ungradedStudents.isEmpty())                                                     // First checks if there are ungraded students for the course
        {
            // Error message if all students are graded or no course was selected
            view.displayCourseInfoTXT.clear();
            view.displayCourseInfoTXT.setStyle("-fx-text-inner-color: #ff0000;");
            view.displayCourseInfoTXT.appendText(
                    "Error: Cannot add grade.\n" +
                       "Please review course selection.");
        }
        else
        {
            // Get course name
            ArrayList<String> courseName = model.getCourseName(courseID);
            String course_ = courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2);
            
            // Add labels and combo box for selecting an ungraded course
            view.scr4InstructionsLBL.setText("Select a student and a grade to add it.");
            view.selectedStudentLBL  .setText(course_);
            view.selectNullCourseLBL .setText("Select student");
            view.selectNullCourseCOMB.setItems(ungradedStudents);
            
            // Update old and new screen
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
        }
    }

    // Switches to new screen
    public void goTo(Screen oldscreen, Screen newscreen)
    {
        // Display return button on all screens except screen 1
        if (oldscreen == view.screen1)
        {
            view.returnBTN.setVisible(true);
        } 
        else if (newscreen == view.screen1) 
        {
            view.returnBTN.setVisible(false);
        }
        // Update old and new screen
        oldscreen.hide();
        newscreen.show();
        currentScreen = newscreen;
        
        // Clear text areas, labels and selections from combo boxes
        view.displayCourseInfoTXT  .clear();
        view.displayStudentInfoTXT .clear();
        view.selStudentOrCourseCOMB.getSelectionModel().clearSelection();
        view.selectNullCourseCOMB  .getSelectionModel().clearSelection();
        view.selectGradeCOMB       .getSelectionModel().clearSelection();
        view.confirmGradeUpdate    .setText("");
    }

    // Queries student names for combo box
    public ObservableList<String> getStudentNames()
    {
        ArrayList<String> names = model.SQLQueryStudentNames();
        ObservableList<String> studentNames = FXCollections.observableArrayList(names);
        return studentNames;
    }

    // Queries course IDs for combo box
    public ObservableList<String> getCourseIDs()
    {
        ArrayList<String> names = model.SQLQueryCourseIDs();
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }

    // Queries IDs of ungraded courses for combo box
    public ObservableList<String> getUngradedCourseIDs(String studentName)
    {
        ArrayList<String> names= model.getUngradedCourses(studentName);
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }

    // Queries names of ungraded students for combo box
    public ObservableList<String> getUngradedStudents(String courseID)
    {
        ArrayList<String> names= model.getUngradedStudents(courseID);
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }
}
