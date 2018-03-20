package com.chriserus;

import com.chriserus.hibernate.ClientEntity;

import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CreatingBox {


    static ClientEntity tempClient;
    public static void displayCreator(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adding new customer");


        //Cancel button create
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
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

            if(isName(nameInput, surnameInput, window)){
                //getting the factory
                SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
                Session session = sessionFactory.getCurrentSession();
                //Creating a customer
                    ClientEntity tempClient = new ClientEntity();
                    tempClient.setName(nameInput.getText());
                    tempClient.setSurname(surnameInput.getText());
                    tempClient.setVegetarian(isVegBox.isSelected());
                    session.beginTransaction();
                    session.save(tempClient);
                    session.getTransaction().commit();

                OrderBox.displayOrder(tempClient);
            }else{
                AlertBox.display("This is not a valid name/surname!");
            }
            });

        //Cancel button action
        cancelButton.setOnAction(e-> {
            window.close();
        });


        grid.getChildren().addAll(nameLabel, nameInput, surnameLabel, surnameInput, cancelButton, submitButton, isVegBox);


        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.showAndWait();

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
