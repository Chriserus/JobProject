package com.chriserus;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import java.util.regex.*;

public class CreatingBox {

    static String answer;
    public static String displayCreator(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adding new customer");

        Label label = new Label();
        label.setText("Complete ALL fields to add a customer");

        //Cancel button create
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        //Submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

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
        GridPane.setConstraints(cancelButton, 0, 3);

        //Submit button placement
        GridPane.setConstraints(submitButton, 1, 3);

        //isVeg checkbox
        CheckBox isVegBox = new CheckBox("Is a vegetarian?");
        GridPane.setConstraints(isVegBox, 1, 2);


        //Taking input via submitButton and creating new Customer object
        submitButton.setOnAction(e-> {
            answer = "New customer created";
            if(isName(nameInput, surnameInput, window)){
                Customer customer = new Customer(nameInput.getText(), surnameInput.getText(), isVegBox.isSelected());
                System.out.println("Hello " + customer.getName() + " " + customer.getSurname() + " Are you are veg?: " + customer.getIsVeg());
            }else{
                System.out.println("This is not a name/surname");
            }
            });

        //Cancel button action
        cancelButton.setOnAction(e-> {
            answer = "New customer not created";
            window.close();
        });


        grid.getChildren().addAll(nameLabel, nameInput, surnameLabel, surnameInput, cancelButton, submitButton, isVegBox);


        Scene scene = new Scene(grid, 500, 200);
        window.setScene(scene);
        window.showAndWait();

        return answer;
    }

    static private boolean isName(TextField inputN, TextField inputS, Stage window){
        if(!(inputN.getText() + " " + inputS.getText()).matches("[A-Z][a-z]+( [A-Z][a-z]+)?")){
            inputN.setText("");
            inputS.setText("");
            return false;
        }else{
            window.close();
            return true;
        }
    }


}
