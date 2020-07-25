package com.chriserus;

import com.chriserus.hibernate.Client;
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

    private final List<SeatButton> tableButtons;
    private final Stage window;
    private final TableBox tableBox;

    TableBox() {
        tableButtons = new ArrayList<>(4);
        window = new Stage();
        GridPane grid = new GridPane();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table overview");
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);
        tableBox = this;

        System.out.println("Creating new buttons...");
        int count = 0;
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
        grid.getChildren().addAll(tableButtons);
        System.out.println("Size of list: " + tableButtons.size());
        System.out.println("The list: " + tableButtons);


        Scene scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        window.showAndWait();
    }

    public Stage getWindow() {
        return window;
    }

    public List<SeatButton> getTableButtons() {
        return tableButtons;
    }

    class SeatButton extends Button {
        boolean occupied;
        int number;
        OccupiedBox occupiedBox;
        CreatingBox creatingBox;
        Client client;

        SeatButton() {
            super();
            occupied = false;
        }

        public void setClient(Client client) {
            this.client = client;
        }

        public void setOccupied(boolean occupied) {
            this.occupied = occupied;
        }


        void setNumber(int number) {
            this.number = number;
            System.out.println("Number of " + this + " is " + number);
            creatingBox = new CreatingBox(tableBox, number);
            occupiedBox = new OccupiedBox();
        }

        public void updateButton() {
            FileInputStream inputStream = null;
            if (occupied) {
                try {
                    inputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("icons/person").getPath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image personIcon = new Image(inputStream);
                this.setGraphic(new ImageView(personIcon));
                this.setOnAction(e -> {
                    if (occupiedBox.getClient() == null) {
                        occupiedBox.setClient(client);
                    }
                    occupiedBox.display();
                });
            } else {
                try {
                    inputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("icons/addNew").getPath());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Image tableIcon = new Image(inputStream);
                this.setGraphic(new ImageView(tableIcon));

                this.setOnAction(e -> creatingBox.display());
            }
        }
    }
}