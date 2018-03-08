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

public class TableButton extends Button {
    public TableButton() {
        super();
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("resources/icons/54901");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image tableIcon = new Image(inputstream);
        this.setGraphic(new ImageView(tableIcon));

        this.setOnAction(e -> {
            CreatingBox.displayCreator();
        });
    }
}