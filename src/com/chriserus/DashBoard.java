package com.chriserus;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DashBoard {
    private ArrayList<CustomerButton> customerButtons = new ArrayList<>();

    private TableBox tableBox;

    DashBoard(){
    }

    public void displayDash(Stage window){
        //setting whole dashboard
        window.setTitle("Restaurant manager");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        //creating and placing all the buttons
        if (customerButtons.isEmpty()) {
            int count;
            System.out.println("Creating new buttons...");
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    CustomerButton button = new CustomerButton();
                    GridPane.setConstraints(button, i, j);
                    customerButtons.add(button);
                    
                }
            }
        } else {
            System.out.println("Buttons exist");
        }


        Button menu = new Button("MENU");
        Button save = new Button("SAVE");
        Button load = new Button("LOAD");
        Button archive = new Button("ARCHIVE");


        GridPane.setConstraints(menu, 2, 0);
        GridPane.setConstraints(archive, 2, 1);
        GridPane.setConstraints(save, 0, 3);
        GridPane.setConstraints(load, 1, 3);

        //setting actions on click
        menu.setOnAction(e -> {
            MenuBox menuBox = new MenuBox();
            menuBox.displayMenu();
        });

        archive.setOnAction(e -> {
            ArchiveBox archiveBox = new ArchiveBox();
            archiveBox.display();
        });

        //adding buttons to the grid
        grid.getChildren().addAll(customerButtons);
        grid.getChildren().addAll(menu, save, load, archive);

        Scene scene = new Scene(grid, 900, 600);
        window.setOnCloseRequest(e -> appClose());
        window.setScene(scene);
        window.show();
    }

    private void appClose() {
        Platform.exit();
        HibernateFactory.close();
    }

    class CustomerButton extends Button{

        CustomerButton() {
            super();
            this.setText("Add a new customer");
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream("resources/icons/table");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image tableIcon = new Image(inputStream);
            this.setGraphic(new ImageView(tableIcon));
            this.setOnAction(e -> {
                //shows the tableBox if present, creates new if not
                if(tableBox==null){
                    tableBox = new TableBox();
                }else{
                    tableBox.getWindow().showAndWait();
                }

            });
        }



    }
    }

