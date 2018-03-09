package com.chriserus;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;


public class OrderBox {

    public static void displayOrder(){
        Stage window = new Stage();



        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order creator");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Scene scene = new Scene(grid, 800, 900);
        window.setScene(scene);
        window.showAndWait();
    }
}
