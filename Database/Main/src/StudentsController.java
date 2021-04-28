
// importing required classes / packages
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import java.sql.SQLException;
import java.util.ArrayList;


public class StudentsController {
    StudentModel model;
    StudentsView view;
    Screen currentScreen;

    public StudentsController(StudentModel model){
        this.model=model;
        try {
            model.connect();
            model.createStatement();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }


    public void setView(StudentsView view) {
        // set view and initial current screen
        this.view = view;
        currentScreen = view.screen1;
        // event for exit button
        view.exitBtn.setOnAction(e-> Platform.exit());

        // initially hide each screen except Screen 1
        view.nullscreen.hide();
        view.screen2.hide();
        view.screen3.hide();
        view.screen4.hide();

        // event handlers for each button:
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

    // event for adding a grade
    public void setNewGrade(String grade){
        // first checks if a course/student and a grade where selected
        if (view.selectNullCourseCOMB.getValue() == null || grade == null){
            // error message if course/student and/or grade were not selected
            view.confirmGradeUpdate.setText("Please select both a course and grade.");
        } else {
            String student, courseID;
            // reading selected student and course, depending on previous screen
            if (currentScreen.getPrev().equals(view.screen2)){
                student  = view.selectNullCourseCOMB.getValue();
                courseID = view.selectCourseCOMB    .getValue();
            } else {
                student  = view.selectStudentCOMB   .getValue();
                courseID = view.selectNullCourseCOMB.getValue();
            }
            // updating the grade in the database
            model.updateGrade(student, courseID, Integer.parseInt(grade));
            view.confirmGradeUpdate.setText("Grade has been updated");
        }
        // clearing the selection from combo boxes
        view.selectCourseCOMB .getSelectionModel().clearSelection();
        view.selectStudentCOMB.getSelectionModel().clearSelection();
    }

    // queries course information from database and updates the text area to display information
    public void courseInfoBox(String courseID){
        view.displayCourseInfoTXT.clear();
        // first checks if a course was selected
        if (courseID == null){
            // error message if no course was selected
            view.displayCourseInfoTXT.appendText("No course selected");
        } else {
            // get course name, teacher and course average from database
            ArrayList<String> courseName = model.getCourseName   (courseID);
            String teacherName           = model.getTeacherName  (courseID);
            String courseAvg             = model.getCourseAverage(courseID);
            // update text area
            view.displayCourseInfoTXT.appendText(
                    "Selected course: " + courseName.get(0) + " - " + courseName.get(2) + " - " + courseName.get(1) + "\n\n" +
                       "Teacher: " + teacherName + "\n\n" +
                       "Average grade: " + courseAvg);
        }
    }

    // queries student information from database and updates the text area to display information
    public void studentInfoBox(String studentName) {
        view.displayStudentInfoTXT.clear();
        // first checks if a student was selected
        if (studentName == null) {
            // error message if no student was selected
            view.displayStudentInfoTXT.appendText("No student selected");
        } else{
            // get grade for each course and student average from database
            ArrayList<String[]> courseGrade = model.getCourseAndGrade(studentName);
            String studentAvg               = model.getStudentAverage(studentName);

            ArrayList<String> courseName;
            view.displayStudentInfoTXT.appendText("Selected student: " + studentName + "\n\nCourses:\n");
            // display taken courses and grade for each
            for(String[] course : courseGrade){
                courseName = model.getCourseName(course[0]);
                view.displayStudentInfoTXT.appendText(
                        courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2) +
                           " - Grade: " + course[1] +  "\n");
            }
            // display student average
            view.displayStudentInfoTXT.appendText("\nAverage grade: " + studentAvg);
        }
    }

    // goes to screen 4 for adding a grade for an ungraded student
    public void goToStudentGrade(Screen oldscreen, Screen newscreen, String name){
        // first checks if there are ungraded courses for the student
        ObservableList<String> ungradedCourses = getUngradedCourseIDs(name);
        if (ungradedCourses.isEmpty()) {
            // error message if all courses are graded or no student was selected
            view.displayStudentInfoTXT.clear();
            view.displayStudentInfoTXT.appendText(
                    "Error: Cannot add grade.\n" +
                       "Please review student selection.");
        }
        else {
            // add labels and combo box for selecting an ungraded course
            view.scr4InstructionsLBL .setText("Select a course and grade to add it.");
            view.selectedStudentLBL  .setText(name);
            view.selectNullCourseLBL .setText("Select course");
            view.selectNullCourseCOMB.setItems(ungradedCourses);
            // update old and new screen
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
        }
    }

    // goes to screen 4 for adding a grade for an ungraded course
    public void goToCourseGrade(Screen oldscreen, Screen newscreen, String courseID){
        // first checks if there are ungraded students for the course
        ObservableList<String> ungradedStudents = getUngradedStudents(courseID);
        if (ungradedStudents.isEmpty()) {
            // error message if all students are graded or no course was selected
            view.displayCourseInfoTXT.clear();
            view.displayCourseInfoTXT.appendText(
                    "Error: Cannot add grade.\n" +
                       "Please review course selection.");
        }
        else{
            // get course name
            ArrayList<String> courseName = model.getCourseName(courseID);
            String course_ = courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2);
            // add labels and combo box for selecting an ungraded course
            view.scr4InstructionsLBL.setText("Select a student and a grade to add it.");
            view.selectedStudentLBL  .setText(course_);
            view.selectNullCourseLBL .setText("Select student");
            view.selectNullCourseCOMB.setItems(ungradedStudents);
            // update old and new screen
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
        }
    }

    // switches to new screen
    public void goTo(Screen oldscreen, Screen newscreen){
        // display return button on all screens except screen 1
        if (oldscreen == view.screen1){
            view.returnBTN.setVisible(true);
        } else if (newscreen == view.screen1) {
            view.returnBTN.setVisible(false);
        }
        // update old and new screen
        oldscreen.hide();
        newscreen.show();
        currentScreen = newscreen;
        // clear text areas and selections from combo boxes
        view.displayCourseInfoTXT  .clear();
        view.displayStudentInfoTXT .clear();
        view.selStudentOrCourseCOMB.getSelectionModel().clearSelection();
        view.selectNullCourseCOMB  .getSelectionModel().clearSelection();
        view.selectGradeCOMB       .getSelectionModel().clearSelection();
    }

    // queries student names for combo box
    public ObservableList<String> getStudentNames(){
        ArrayList<String> names = model.SQLQueryStudentNames();
        ObservableList<String> studentNames = FXCollections.observableArrayList(names);
        return studentNames;
    }
    // queries course IDs for combo box
    public ObservableList<String> getCourseIDs(){
        ArrayList<String> names = model.SQLQueryCourseIDs();
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }
    // queries IDs of ungraded courses for combo box
    public ObservableList<String> getUngradedCourseIDs(String studentName){
        ArrayList<String> names= model.getUngradedCourses(studentName);
        ObservableList<String> courseNames = FXCollections.observableArrayList(names);
        return courseNames;
    }
    // queries names of ungraded students for combo box
    public ObservableList<String> getUngradedStudents(String courseID){
        ArrayList<String> names= model.getUngradedStudents(courseID);
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }
}
