package com.chriserus;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class CustomerButton extends Button {
    public CustomerButton(){
        super("Add a new customer");
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("resources/icons/formattedTable");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image tableIcon = new Image(inputStream);
        this.setGraphic(new ImageView(tableIcon));
        this.setOnAction(e -> {
            TableBox box = new TableBox();
            box.display();
        });
    }








}
