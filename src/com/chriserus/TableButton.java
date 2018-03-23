package com.chriserus;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

 class TableButton extends Button {
     TableButton() {
        super();
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("resources/icons/54901");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Image tableIcon = new Image(inputStream);
        this.setGraphic(new ImageView(tableIcon));

        this.setOnAction(e -> {
            CreatingBox box = new CreatingBox();
            box.display();
        });
    }
}