package com.chriserus;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MainGUI extends Application {


    public static void main(String[] args) {
         launch(args);
    }

    @Override
    public void start(Stage window){
        ArrayList<CustomerButton> customerButtons = new ArrayList<>();

        //setting whole dashboard
        window.setTitle("Restaurant manager");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        //creating and placing all the buttons
        for(int i = 0; i<2; i++){
            for(int j = 0; j<3; j++){
                CustomerButton button = new CustomerButton();
                GridPane.setConstraints(button, i, j);
                customerButtons.add(button);
            }
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
            MenuBox box = new MenuBox();
            box.displayMenu();
        });

        archive.setOnAction(e -> {
            ArchiveBox box = new ArchiveBox();
            box.displayArchive();
        });

        //adding buttons to the grid
        grid.getChildren().addAll(customerButtons);
        grid.getChildren().addAll(menu, save, load, archive);

        Scene scene = new Scene(grid, 900, 600);
        window.setOnCloseRequest(e -> appClose());
        window.setScene(scene);
        window.show();
    }

    private void appClose(){
        Platform.exit();
        HibernateFactory.close();
    }
}
