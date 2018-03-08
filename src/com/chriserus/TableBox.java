package com.chriserus;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class TableBox {
    public static void displayTable(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Table overview");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        TableButton button1 = new TableButton();
        TableButton button2 = new TableButton();
        TableButton button3 = new TableButton();
        TableButton button4 = new TableButton();

        GridPane.setConstraints(button1, 0, 0);
        GridPane.setConstraints(button2, 1, 0);
        GridPane.setConstraints(button3, 0, 1);
        GridPane.setConstraints(button4, 1, 1);

        grid.getChildren().addAll(button1, button2, button3, button4);

        Scene scene = new Scene(grid, 360, 360);
        window.setScene(scene);
        window.showAndWait();
    }
}
