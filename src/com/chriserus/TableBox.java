package com.chriserus;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;

public class TableBox {
    public void display(){
        ArrayList<TableButton> tableButtons = new ArrayList<>();
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table overview");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        for(int i = 0; i<2; i++){
            for(int j = 0; j<2; j++) {
                TableButton button = new TableButton();
                GridPane.setConstraints(button, i, j);
                tableButtons.add(button);
            }
        }

        grid.getChildren().addAll(tableButtons);

        Scene scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        window.showAndWait();
    }
}
