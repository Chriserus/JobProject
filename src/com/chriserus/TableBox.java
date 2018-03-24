package com.chriserus;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class TableBox {

    //private boolean occupied;
    private List<Button> tableButtons;
    private boolean test;
    private Stage window;

    public void setTest(boolean b){
        test = b;
    }
    public boolean getTest(){
        return test;
    }
    TableBox(){
        tableButtons = new ArrayList<>();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
    }
    public void display() {

        window.setTitle("Table overview");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        if(tableButtons.isEmpty()){
            System.out.println("Creating new buttons...");
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Button button = new Button();
                    button = checkSeat(button/*, occupied*/);
                    GridPane.setConstraints(button, i, j);
                    tableButtons.add(button);
                }
            }
            grid.getChildren().addAll(tableButtons);
            System.out.println("Size of list: " + tableButtons.size());
            System.out.println("The list: " + tableButtons);
        }else{
            int count = 0;
            grid.getChildren().removeAll();
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Button button = tableButtons.get(0);
                    button = checkSeat(button/*, occupied*/);
                    GridPane.setConstraints(button, i, j);
                    System.out.println("Coordinates: " + i + " " + j);
                    System.out.println("Removing button nr: " +count);
                    tableButtons.remove(0);
                    tableButtons.add(button);
                    count++;
                }
            }
            System.out.println("Buttons exist");
            grid.getChildren().addAll(tableButtons);
        }



        Scene scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        window.showAndWait();
        window.setOnCloseRequest(e -> grid.getChildren().removeAll());
    }

    private Button checkSeat(Button button/*, boolean occupied*/) {
        if(button == null){
            button = new Button();
        }
//        //if(occupied){
//            //this.occupied = false;
//            FileInputStream inputStream = null;
//            try {
//                inputStream = new FileInputStream("resources/icons/person");
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//
//            Image personIcon = new Image(inputStream);
//            button.setGraphic(new ImageView(personIcon));
//
//            button.setOnAction(e -> {
//                OccupiedBox box = new OccupiedBox();
//                this.occupied = box.display();
//            });
//            occupied = false;
//            return button;
        //}else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("resources/icons/addNew");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Image tableIcon = new Image(inputStream);
            button.setGraphic(new ImageView(tableIcon));

            button.setOnAction(e -> {
                CreatingBox creatingBox = new CreatingBox();
                /*this.occupied =*/ creatingBox.display();
            });

            return button;
        //}
    }

    class SeatButton extends Button{
        SeatButton(){
            super();

        }
    }
}