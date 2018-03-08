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

public class CustomerButton extends Button {
    public CustomerButton(){
        super("Add a new customer");
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("resources/icons/formattedTable");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image tableIcon = new Image(inputstream);
        this.setGraphic(new ImageView(tableIcon));
        this.setOnAction(e -> {
            TableBox.displayTable();
        });
    }








}
