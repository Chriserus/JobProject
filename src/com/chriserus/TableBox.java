package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
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
import java.util.ArrayList;
import java.util.List;

public class TableBox {

    private List<SeatButton> tableButtons;
    private Stage window;
    private TableBox tableBox;
    private GridPane grid;
    private Scene scene;

    //HUGE constructor
    TableBox(){
        //buttons array 0,1,2,3
        tableButtons = new ArrayList<>(4);
        //gui stuff
        window = new Stage();
        grid = new GridPane();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table overview");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        //variable for storing THIS object
        tableBox = this;

        System.out.println("Creating new buttons...");
        //Loop for creating button grid 2x2, invoked once
        int count= 0;
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                SeatButton button = new SeatButton();
                button.updateButton();
                GridPane.setConstraints(button, i, j);
                tableButtons.add(button);
                button.setNumber(count);
                count++;
            }
        }
        //adding whole table of buttons to the grid
        grid.getChildren().addAll(tableButtons);
        System.out.println("Size of list: " + tableButtons.size());
        System.out.println("The list: " + tableButtons);


        scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        window.showAndWait();
    }


    //window getter to check on dash if created earlier
    public Stage getWindow() {
        return window;
    }

    public List<SeatButton> getTableButtons() {
        return tableButtons;
    }

    //inner SeatButton class
    class SeatButton extends Button{
        boolean occupied;
        int number;
        OccupiedBox occupiedBox;
        CreatingBox creatingBox;
        ClientEntity client;

        SeatButton(){
            super();
            //default after creation false, then we can change it with setter
            occupied = false;
        }

        public void setClientEntity(ClientEntity client){
            this.client = client;
        }

        public void setOccupied(boolean occupied) {
            this.occupied = occupied;
        }


        void setNumber(int number){
            this.number = number;
            System.out.println("Number of " + this + " is " + number);
            //creation of creatingBox
            creatingBox = new CreatingBox(tableBox, number);
            //creation of occupiedBox
            occupiedBox = new OccupiedBox();
        }

        public void updateButton(){
            if(occupied){
                //setting graphic. occupied (person)...
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream("resources/icons/person");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image personIcon = new Image(inputStream);
                this.setGraphic(new ImageView(personIcon));

                //button action when occupied (click)
                this.setOnAction(e -> {
                    occupiedBox.display();
                    //display if client is in place, which client is here
                    System.out.println("Client name: " + client.getName() + " And surname: "
                            + client.getSurname() + " " + client.getId());
                });
            }else {
                //or not occupied (addNew)...
                FileInputStream inputStream = null;
                try {
                    inputStream = new FileInputStream("resources/icons/addNew");
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                Image tableIcon = new Image(inputStream);
                this.setGraphic(new ImageView(tableIcon));

                this.setOnAction(e -> {
                    creatingBox.display();
                });
            }
        }
}}