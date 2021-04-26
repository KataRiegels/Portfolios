
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

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

        view.screen2.hide();

        // event handlers:
        EventHandler<ActionEvent> studentOrCourse = e->goTo(view.screen1, view.screens.get(view.selStudentOrCourseCOMB.getValue()));
        EventHandler<ActionEvent> returnEvent     = e-> goTo(currentScreen, currentScreen.getPrev());

        //EventHandler<ActionEvent> studentOrCourse = e->HandlerPrintTrainRoutes(view.selStudentOrCourse.getValue(), view.test, view.selectCourseCOMB);
        //EventHandler<ActionEvent> PrintTrainTrips = e->handlerTest(view.selectCourseCOMB.getValue(), view.test);

        view.continueBTN.setOnAction(studentOrCourse);
        view.returnBTN.setOnAction(returnEvent);
        //view.continueBTN.setOnAction(PrintTrainTrips);

    }



    public void goTo(Screen oldscreen, Screen newscreen){
        oldscreen.hide();
        newscreen.show();
        newscreen.setPrev(oldscreen);
        currentScreen = newscreen;
    }


    public void handlerTest(String ID, TextArea test) {
        String avgGrade = model.getCourseAverage(ID);
        //view.test2.setVisible(true);
        test.clear();
        //btn.setVisible(true);
        test.appendText("Average course grade " + avgGrade);
    }
    public void HandlerPrintTrainRoutes(String ID, TextArea test, ComboBox<String> comb){
        //String avgGrade= model.getCourseAverage(ID);
        //view.test2.setVisible(true);
        test.clear();
        comb.setVisible(true);
        //test.appendText("Average course grade " + avgGrade);


        /*


        txtArea.clear();
        txtArea.appendText("Course average for " + ID + " \n");
        //double time=(double) Hour +((double) Minutes/100);
        String trips= model.getCourseAverage(ID);
        txtArea.appendText(trips);
        */
                /*
        for (int i=0;i<trips.size();i++){
            String deptime= String.format("%.2f", trips.get(i).departureTime);
            String arrtime=String.format("%.2f", trips.get(i).arrivalTime);
            txtArea.appendText(i+";"+ trips.get(i).FromSt + ": "+ deptime + " -> "+ trips.get(i).ToSt +": "+ arrtime + "\n");
        }

                 */
    }



    public ObservableList<String> getStudentNames(){
        ArrayList<String> names= model.SQLQueryStudentNames();
        ObservableList<String> studentNames= FXCollections.observableArrayList(names);
        return  studentNames;
    }


/*
    public void HandlerPrintTrainRoutes(String From, String To, Integer Hour, Integer Minutes, TextArea txtArea){
        txtArea.clear();
        txtArea.appendText(" Train, From Station: Departure -> To station: arrival \n");
        double time=(double) Hour +((double) Minutes/100);
        ArrayList<Traintrip> trips= model.FindTrainTrips2(From,To,time);
        for (int i=0;i<trips.size();i++){
            String deptime= String.format("%.2f", trips.get(i).departureTime);
            String arrtime=String.format("%.2f", trips.get(i).arrivalTime);
            txtArea.appendText(i+";"+ trips.get(i).FromSt + ": "+ deptime + " -> "+ trips.get(i).ToSt +": "+ arrtime + "\n");
        }
    }

 */







}