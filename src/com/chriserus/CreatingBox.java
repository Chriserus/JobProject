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
        window.setMinWidth(600);

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


        grid.getChildren().addAll(label, cancelButton, submitButton);
        grid.setAlignment(Pos.CENTER);

        Scene scene = new Scene(grid);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }


}
