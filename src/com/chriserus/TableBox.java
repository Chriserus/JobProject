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

    public List<SeatButton> getTableButtons() {
        return tableButtons;
    }


    private List<SeatButton> tableButtons;
    private Stage window;

    TableBox(){
        tableButtons = new ArrayList<>(4);
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
    }

    public void createNewButton(boolean occupied, List<SeatButton> list){
            for(int i = 0; i<4; i++){
                if(!list.get(i).occupied){
                    tableButtons.remove(i);
                    list.get(i).occupied = occupied;
                    tableButtons.add(i, list.get(i));
                }
            }
    }
    private void newCreateBox(){
        CreatingBox creatingBox = new CreatingBox(this);
        creatingBox.display();
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
                    SeatButton button = new SeatButton(false);
                    GridPane.setConstraints(button, i, j);
                    tableButtons.add(button);
                }
            }
            grid.getChildren().addAll(tableButtons);
            System.out.println("Size of list: " + tableButtons.size());
            System.out.println("The list: " + tableButtons);
        }else{
            int count = 0;
//            grid.getChildren().removeAll();
            //2 loops, bcs we want a 2x2 button placement
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    SeatButton button = tableButtons.get(count);
                    GridPane.setConstraints(button, i, j);
                    System.out.println("Coordinates: " + i + " " + j);
                    System.out.println("Removing button nr: " +count);
                    tableButtons.remove(count);
                    tableButtons.add(count, button);
                    count++;
                }
            }
            grid.getChildren().removeAll();
            System.out.println("Buttons exist");
            grid.getChildren().addAll(tableButtons);
        }



        Scene scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        if(!window.isShowing()){
            window.showAndWait();
        }
        window.setOnCloseRequest(e -> grid.getChildren().removeAll());
    }


    class SeatButton extends Button{
        boolean occupied;
        int number;
        SeatButton(boolean occupied){
            super();
            if(occupied){
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("resources/icons/person");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Image personIcon = new Image(inputStream);
            this.setGraphic(new ImageView(personIcon));

            this.setOnAction(e -> {
                OccupiedBox box = new OccupiedBox();
                this.occupied = box.display();
            });
            }else {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("resources/icons/addNew");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Image tableIcon = new Image(inputStream);
            this.setGraphic(new ImageView(tableIcon));

            this.setOnAction(e -> {
                number = tableButtons.indexOf(this);
                newCreateBox();
            });
        }
    }
}}