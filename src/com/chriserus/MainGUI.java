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

public class MainGUI extends Application{

    Stage window;
    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        window = primaryStage;
        window.setTitle("Restaurant manager");

        //Making a button with Table icon
        button = new Button();
        button.setText("Add a new customer");
        FileInputStream inputstream = null;
        try {
            inputstream = new FileInputStream("resources/icons/formattedTable");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Image tableIcon = new Image(inputstream);
        button.setGraphic(new ImageView(tableIcon));
        button.setOnAction(e -> {
           String result = CreatingBox.displayCreator();
           System.out.println(result);
        });

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 800, 400);
        window.setScene(scene);
        window.show();
    }

}
