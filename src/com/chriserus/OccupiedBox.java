package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import com.chriserus.hibernate.ItemEntity;
import com.chriserus.hibernate.PurchaseEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class OccupiedBox {

    private ClientEntity clientEntity;
    private Button finishedButton, cancelButton;
    private Label nameAndSurname, price, calories;
    private GridPane grid;
    private TableView<ItemEntity> orderTable;
    private int currentClientId;


    OccupiedBox(){
        finishedButton = new Button("Print receipt");
        cancelButton = new Button("Back");
        nameAndSurname = new Label();
        price = new Label();
        calories = new Label();
        grid = new GridPane();
        orderTable = new TableView<>();

    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    private void setOrder(){
        ObservableList<ItemEntity> products;
        //getting the factory
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        //getting list of x client items ordered
         //itemList = session.createQuery("from PurchaseEntity as p where p.clientByClientId=" + currentClientId)
               // .getResultList();

        List<PurchaseEntity> purchaseList = session.createQuery
                ("from PurchaseEntity where clientByClientid.id=" +
                        currentClientId)
                .getResultList();
        System.out.println(purchaseList);

        //Creating and sending an order
        session.getTransaction().commit();
        ArrayList<ItemEntity> itemList = new ArrayList<>();
        for(PurchaseEntity p : purchaseList){
            itemList.add(p.getItemByMenuid());
        }
        products = FXCollections.observableArrayList(itemList);
        orderTable.setItems(products);
        System.out.println(products);
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
        currentClientId = clientEntity.getId();
        System.out.println("I got id nr: "+currentClientId);
        //Creating and sending an order
        session.getTransaction().commit();

        setOrder();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Current client");

        System.out.println("I got client: " + clientEntity.getName() + clientEntity.getSurname() + clientEntity.getId() +
        " " + clientEntity.getOrderTotal());
        //creating gui components
        price.setText(clientEntity.getOrderTotal().toString());
        calories.setText(clientEntity.getCaloriesTotal().toString());
        nameAndSurname.setText(clientEntity.getName() + " " + clientEntity.getSurname());


        //Name
        TableColumn<ItemEntity, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price
        TableColumn<ItemEntity, Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(200);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Calories
        TableColumn<ItemEntity, Integer> caloriesCol = new TableColumn<>("Calories");
        caloriesCol.setMinWidth(200);
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

        //Vegetarian
        TableColumn<ItemEntity, Boolean> vegetarianCol = new TableColumn<>("Vegetarian");
        vegetarianCol.setMinWidth(200);
        vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        orderTable.setMinHeight(800);
        orderTable.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);





        GridPane.setConstraints(nameAndSurname, 0, 0);
        GridPane.setConstraints(calories, 1,1);
        GridPane.setConstraints(price,1,0);
        GridPane.setConstraints(finishedButton,2,1);
        GridPane.setConstraints(cancelButton,2,0);
        GridPane.setConstraints(orderTable, 3, 0, 1, 2);


        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(nameAndSurname,calories,price,finishedButton,cancelButton, orderTable);

        Scene scene = new Scene(grid, 800, 900);
        window.setScene(scene);
        window.showAndWait();
    }
}