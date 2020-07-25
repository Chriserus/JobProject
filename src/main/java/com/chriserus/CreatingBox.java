package com.chriserus;

import com.chriserus.hibernate.Client;
import com.chriserus.hibernate.HibernateUtil;
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

public class CreatingBox {
    private TextField nameInput, surnameInput;
    private Stage window;
    private CheckBox isVegBox;
    private final TableBox tableBox;
    private final int number;

    CreatingBox(TableBox tableBox, int number) {
        this.tableBox = tableBox;
        this.number = number;
    }

    public void display() {
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Adding new customer");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(10);
        grid.setHgap(10);

        Button cancelButton = new Button();
        cancelButton.setText("Cancel");

        Button submitButton = new Button();
        submitButton.setText("Submit");

        Label nameLabel = new Label("Name:");
        GridPane.setConstraints(nameLabel, 0, 0);

        nameInput = new TextField();
        GridPane.setConstraints(nameInput, 1, 0);

        Label surnameLabel = new Label("Surname:");
        GridPane.setConstraints(surnameLabel, 0, 1);

        surnameInput = new TextField();
        GridPane.setConstraints(surnameInput, 1, 1);

        GridPane.setConstraints(cancelButton, 0, 3);

        GridPane.setConstraints(submitButton, 1, 3);

        isVegBox = new CheckBox("Is a vegetarian?");
        GridPane.setConstraints(isVegBox, 1, 2);

        submitButton.setOnAction(e -> submitButtonClicked());

        cancelButton.setOnAction(e -> window.close());

        grid.getChildren().addAll(nameLabel, nameInput, surnameLabel, surnameInput, cancelButton, submitButton, isVegBox);

        Scene scene = new Scene(grid, 300, 200);
        window.setScene(scene);
        window.showAndWait();
    }

    private void submitButtonClicked() {
        if (isName(nameInput, surnameInput)) {
            window.close();
            try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
                Client tempClient = new Client(nameInput.getText(), surnameInput.getText(), isVegBox.isSelected());
                session.beginTransaction();
                session.save(tempClient);
                session.getTransaction().commit();
                OrderBox orderBox = new OrderBox(tableBox, number);
                orderBox.display(tempClient);
            }
        } else {
            AlertBox boxA = new AlertBox();
            boxA.display("This is not a valid name/surname!");
        }
    }

    private boolean isName(TextField inputName, TextField inputSurname) {
        if (!(inputName.getText() + " " + inputSurname.getText()).matches("[A-Z][a-z]+( [A-Z][a-z]+)?")) {
            inputName.setText("");
            inputSurname.setText("");
            return false;
        } else {
            return true;
        }
    }
}
