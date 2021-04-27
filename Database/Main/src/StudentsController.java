
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
            //model.PreparedStmtFindTripsQuert();
        }catch (SQLException e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public void hide(Node[] screenArray){
        for(Node node : screenArray ){
            node.setVisible(false);
        }
    }

    public void show(Node[] screenArray){
        for(Node node : screenArray ){
            node.setVisible(true);
        }
    }

    public void setView(StudentsView view) {
        this.view = view;
        currentScreen = view.screen1;
        view.exitBtn.setOnAction(e-> Platform.exit());

        view.nullscreen.hide();
        view.screen2.hide();
        view.screen3.hide();
        view.screen4.hide();

        // event handlers:
        EventHandler<ActionEvent> returnEvent       = e->goTo(currentScreen, currentScreen.getPrev());

        EventHandler<ActionEvent> studentOrCourse   = e->goTo(view.screen1, view.screens.get(view.selStudentOrCourseCOMB.getValue()));
        EventHandler<ActionEvent> addingGrades      = e->goToStudentGrade(currentScreen, view.screen4, view.selectStudentCOMB.getValue());
        EventHandler<ActionEvent> addingCourseGrades = e->goToCourseGrade(currentScreen, view.screen4, view.selectCourseCOMB.getValue());

        EventHandler<ActionEvent> courseConfirm     = e->courseInfoBox(view.selectCourseCOMB.getValue());
        EventHandler<ActionEvent> studentConfirm    = e->studentInfoBox(view.selectStudentCOMB.getValue());
        EventHandler<ActionEvent> updateGrade       = e->setNewGrade(view.selectNullCourseCOMB.getValue(), view.selectGradeCOMB.getValue());



        view.continueBTN.setOnAction(studentOrCourse);
        view.returnBTN.setOnAction(returnEvent);
        view.addGradeBTN.setOnAction(addingGrades);
        view.confirmCourseBTN.setOnAction(courseConfirm);
        view.confirmStudentBTN.setOnAction(studentConfirm);
        view.setGradeBTN.setOnAction(updateGrade);
        view.addCourseGradeBTN.setOnAction(addingCourseGrades);


        //view.continueBTN.setOnAction(PrintTrainTrips);

    }


    public void setNewGrade(String courseID, String grade){
        String student = view.selectedStudentLBL.getText();
        model.updateGrade(student, courseID, Integer.parseInt(grade));
        view.confirmGradeUpdate.setText("Grade has been updated");
    }



    public void courseInfoBox(String courseID){
        view.displayCourseInfoTXT.clear();
        if (courseID == null){
            view.displayCourseInfoTXT.appendText("No course selected");
        } else {
            ArrayList<String> courseName = model.getCourseName(courseID);
            String teacherName = model.getTeacherName(courseID);
            String courseAvg = model.getCourseAverage(courseID);


            view.displayCourseInfoTXT.appendText("Selected course: " + courseName.get(0) + " - " + courseName.get(1) + " - " + courseName.get(2) + "\n");
            view.displayCourseInfoTXT.appendText("Course teacher: " + teacherName + "\n");
            view.displayCourseInfoTXT.appendText("Course average: " + courseAvg);
        }
    }

    public void studentInfoBox(String studentName) {
        view.displayStudentInfoTXT.clear();
        if (studentName == null) view.displayStudentInfoTXT.appendText("No student selected");
        else{
            ArrayList<String[]> courseGrade = model.getCourseAndGrade(studentName);
            ArrayList<String> courseName;
            String studentAvg = model.getStudentAverage(studentName);

            view.displayStudentInfoTXT.appendText("Selected student: " + studentName + "\n");
            for(String[] course : courseGrade){
                 courseName = model.getCourseName(course[0]);
                 String course_ = "Course: " + courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2);
                view.displayStudentInfoTXT.appendText(course_ + " - Got grade: " + course[1] +  "\n");
            }
            view.displayStudentInfoTXT.appendText("Average grade: " + studentAvg);
        }

    }


    public void goToStudentGrade(Screen oldscreen, Screen newscreen, String name){
        ObservableList<String> ungradedCourses = getUngradedCourseIDs(name);
        if (ungradedCourses.isEmpty()) {
            view.displayStudentInfoTXT.clear();
            view.displayStudentInfoTXT.appendText("Cannot add grade. Please review student selection");
        }
        else {
            view.selectNullCourseCOMB.setItems(ungradedCourses);

            view.selectedStudentLBL.setText(name);
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
            view.selectNullCourseLBL.setText("Select Course");
        }
    }


    public void goToCourseGrade(Screen oldscreen, Screen newscreen, String courseID){
        ObservableList<String> ungradedStudents = getUngradedStudents(courseID);
        if (ungradedStudents.isEmpty()) {
            view.displayCourseInfoTXT.clear();
            view.displayCourseInfoTXT.appendText("Cannot add grade. Please review course selection");
        }
        else{
            view.selectNullCourseCOMB.setItems(ungradedStudents);
            ArrayList<String> courseName = model.getCourseName(courseID);
            String course_ = courseName.get(0) + " - "  + courseName.get(1) + " - " + courseName.get(2);
            view.selectedStudentLBL.setText(course_);
            newscreen.setPrev(oldscreen);
            goTo(oldscreen, newscreen);
            view.selectNullCourseLBL.setText("Select Student");
        }
    }


    public void goTo(Screen oldscreen, Screen newscreen){
        oldscreen.hide();
        newscreen.show();
        newscreen.setPrev(oldscreen);
        currentScreen = newscreen;
    }


    public ObservableList<String> getStudentNames(){
        ArrayList<String> names= model.SQLQueryStudentNames();
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }

    public ObservableList<String> getCourseIDs(){
        ArrayList<String> names= model.SQLQueryCourseIDs();
        ObservableList<String> courseNames= FXCollections.observableArrayList(names);
        return  courseNames;
    }

    public ObservableList<String> getUngradedCourseIDs(String studentName){
        ArrayList<String> names= model.getUngradedCourses(studentName);
        ObservableList<String> courseNames= FXCollections.observableArrayList(names);
        return  courseNames;
    }


    public ObservableList<String> getUngradedStudents(String courseID){
        ArrayList<String> names= model.getUngradedStudents(courseID);
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }





}
