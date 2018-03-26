package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import com.chriserus.hibernate.ItemEntity;
import com.chriserus.hibernate.PurchaseEntity;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OccupiedBox {

    private ClientEntity clientEntity;
    private Button finishedButton, cancelButton;
    private Label nameAndSurname, price, calories;
    private GridPane grid;


    OccupiedBox(){
        finishedButton = new Button("Print receipt");
        cancelButton = new Button("Back");
        nameAndSurname = new Label();
        price = new Label();
        calories = new Label();
        grid = new GridPane();


    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }


    public void display(){
        Stage window = new Stage();

        //getting the factory
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        //Updating the customer
        ClientEntity client = session.get(ClientEntity.class, clientEntity.getId());
        clientEntity.setCaloriesTotal(client.getCaloriesTotal());
        clientEntity.setOrderTotal(client.getOrderTotal());

        //Creating and sending an order
        session.getTransaction().commit();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Current client");

        System.out.println("I got client: " + clientEntity.getName() + clientEntity.getSurname() + clientEntity.getId() +
        " " + clientEntity.getOrderTotal());
        //creating gui components
        price.setText(clientEntity.getOrderTotal().toString());
        calories.setText(clientEntity.getCaloriesTotal().toString());
        nameAndSurname.setText(clientEntity.getName() + " " + clientEntity.getSurname());






        GridPane.setConstraints(nameAndSurname, 0, 0);
        GridPane.setConstraints(calories, 1,1);
        GridPane.setConstraints(price,1,0);
        GridPane.setConstraints(finishedButton,2,1);
        GridPane.setConstraints(cancelButton,2,0);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(nameAndSurname,calories,price,finishedButton,cancelButton);

        Scene scene = new Scene(grid, 800, 900);
        window.setScene(scene);
        window.showAndWait();
    }
}