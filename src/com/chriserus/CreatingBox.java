package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CreatingBox {
    private TextField nameInput, surnameInput;
    private Stage window;
    private CheckBox isVegBox;

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adding new customer");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        //Cancel button create
        Button cancelButton = new Button();
        cancelButton.setText("Cancel");

        //Submit button
        Button submitButton = new Button();
        submitButton.setText("Submit");

        //Name label
        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        //Name input
        nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        //Surname label
        Label surnameLabel = new Label("Surname:");
        GridPane.setConstraints(surnameLabel, 0, 1);

        //Surname input
        surnameInput = new TextField();
        GridPane.setConstraints(surnameInput, 1, 1);

        //Cancel button placement
        GridPane.setConstraints(cancelButton, 0, 3);

        //Submit button placement
        GridPane.setConstraints(submitButton, 1, 3);

        //isVeg checkbox
        isVegBox = new CheckBox("Is a vegetarian?");
        GridPane.setConstraints(isVegBox, 1, 2);


        submitButton.setOnAction(e->submitButtonClicked());

        //Cancel button action
        cancelButton.setOnAction(e->window.close());

        grid.getChildren().addAll(nameLabel, nameInput, surnameLabel, surnameInput, cancelButton, submitButton, isVegBox);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }

    //Taking input via submitButton and creating new Customer object
    private void submitButtonClicked(){
        if(isName(nameInput, surnameInput)){
            window.close();
            //getting the factory
            SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            //Creating a customer
            ClientEntity tempClient = new ClientEntity(nameInput.getText(), surnameInput.getText(), isVegBox.isSelected());
            //Sending a customer
            session.beginTransaction();
            session.save(tempClient);
            session.getTransaction().commit();

            OrderBox box = new OrderBox();
            box.display(tempClient);
        }else{
            AlertBox box = new AlertBox();
            box.display("This is not a valid name/surname!");
        }
    }

    //Name and surname validation
    private boolean isName(TextField inputName, TextField inputSurname){
        if(!(inputName.getText() + " " + inputSurname.getText()).matches("[A-Z][a-z]+( [A-Z][a-z]+)?")){
            inputName.setText("");
            inputSurname.setText("");
            return false;
        }else{
            return true;
        }
    }
}
