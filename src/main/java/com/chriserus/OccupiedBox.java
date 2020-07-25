package com.chriserus;

import com.chriserus.hibernate.Client;
import com.chriserus.hibernate.HibernateUtil;
import com.chriserus.hibernate.Item;
import com.chriserus.hibernate.Purchase;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class OccupiedBox {

    private Client client;
    private final Button finishedButton;
    private final Button cancelButton;
    private final Label nameAndSurname;
    private final Label price;
    private final Label calories;
    private final GridPane grid;
    private final TableView<Item> orderTable;
    private Long currentClientId;
    private Scene scene;
    private final Stage window;
    private final VBox vBox;


    OccupiedBox() {
        finishedButton = new Button("Print receipt");
        cancelButton = new Button("Back");
        nameAndSurname = new Label();
        price = new Label();
        calories = new Label();
        grid = new GridPane();
        orderTable = new TableView<>();
        window = new Stage();
        vBox = new VBox();
        window.initModality(Modality.APPLICATION_MODAL);
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() {
        return client;
    }


    private void setOrder() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            List<Purchase> purchaseList = session.createQuery
                    ("from Purchase p where p.client.id=" + currentClientId).getResultList();
            System.out.println(purchaseList);
            ArrayList<Item> itemList = new ArrayList<>();
            for (Purchase p : purchaseList) {
                itemList.add(p.getItem());
            }
            ObservableList<Item> products = FXCollections.observableArrayList(itemList);
            orderTable.setItems(products);
            System.out.println(products);
            session.getTransaction().commit();
        }
    }

    public void display() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, this.client.getId());
            this.client.setCaloriesTotal(client.getCaloriesTotal());
            this.client.setOrderTotal(client.getOrderTotal());
            currentClientId = this.client.getId();
            System.out.println("I got id nr: " + currentClientId);
            session.getTransaction().commit();
        }
        setOrder();
        window.setTitle("Current client");
        System.out.println("I got client: " + this.client.getFirstName() + this.client.getSecondName() + this.client.getId() +
                " " + this.client.getOrderTotal());
        price.setText("Total price: " + "\n" + "$" + this.client.getOrderTotal().toString());
        price.setMinWidth(200);
        calories.setText("Total calories: " + "\n" + this.client.getCaloriesTotal().toString());
        calories.setMinWidth(200);
        nameAndSurname.setText("Name and surname: " + "\n" + this.client.getFirstName() + " " + this.client.getSecondName());
        nameAndSurname.setMinWidth(200);
        if (vBox.getChildren().isEmpty()) {
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.getChildren().addAll(nameAndSurname, price, calories);
        }
        finishedButton.setMinWidth(200);
        cancelButton.setMinWidth(100);
        if (orderTable.getColumns().isEmpty()) {
            TableColumn<Item, String> nameCol = new TableColumn<>("Name");
            nameCol.setMinWidth(180);
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
            priceCol.setMinWidth(180);
            priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
            TableColumn<Item, Integer> caloriesCol = new TableColumn<>("Calories");
            caloriesCol.setMinWidth(180);
            caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));
            TableColumn<Item, Boolean> vegetarianCol = new TableColumn<>("Vegetarian");
            vegetarianCol.setMinWidth(180);
            vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));
            orderTable.setMinHeight(800);
            orderTable.getColumns().addAll(nameCol, priceCol, caloriesCol, vegetarianCol);
        }
        GridPane.setConstraints(vBox, 0, 0);
        GridPane.setConstraints(finishedButton, 2, 3);
        GridPane.setConstraints(cancelButton, 0, 3);
        GridPane.setConstraints(orderTable, 1, 0, 2, 2);
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        if (grid.getChildren().isEmpty()) {
            grid.getChildren().addAll(vBox, finishedButton, cancelButton, orderTable);
            scene = new Scene(grid, 1000, 900);
        }
        window.setScene(scene);
        window.showAndWait();
    }
}