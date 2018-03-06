package com.chriserus;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;


public class CreatingBox {

    static String answer;
    public static String displayCreator(){
        Stage window = new Stage();


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adding new customer");

        Label label = new Label();
        label.setText("Complete ALL fields to add a customer");

        Button cancelButton = new Button();
        cancelButton.setText("Cancel");
        cancelButton.setOnAction(e-> {
            answer = "New customer not created";
            window.close();
        });

        Button submitButton = new Button();
        submitButton.setText("Submit");
        submitButton.setOnAction(e-> {
            answer = "New customer created";
            window.close();
        });


        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Name label
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name input
        TextField nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        //Surname label
        Label surnameLabel = new Label("Surname:");
        GridPane.setConstraints(surnameLabel, 0, 1);

        //Surname input
        TextField surnameInput = new TextField();
        GridPane.setConstraints(surnameInput, 1, 1);

        //Cancel button placement
        GridPane.setConstraints(cancelButton, 0, 2);

        //Submit button placement
        GridPane.setConstraints(submitButton, 1, 2);

        //isVeg checkbox
        CheckBox isVegBox = new CheckBox("Are you a vegetarian?");
        GridPane.setConstraints(isVegBox, 2, 1);

        grid.getChildren().addAll(nameLabel, nameInput, surnameLabel, surnameInput, cancelButton, submitButton, isVegBox);


        Scene scene = new Scene(grid, 800, 800);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }


}
