package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import com.chriserus.hibernate.ItemEntity;
import com.chriserus.hibernate.PurchaseEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;

public class OccupiedBox {

    private ClientEntity clientEntity;
    private Button finishedButton, cancelButton;
    private Label nameAndSurname1, nameAndSurname2, price1, price2, calories1, calories2;
    private GridPane grid;
    private TableView<ItemEntity> orderTable;
    private int currentClientId;


    OccupiedBox(){
        finishedButton = new Button("Print receipt");
        cancelButton = new Button("Back");
        nameAndSurname1 = new Label();
        price1 = new Label();
        calories1 = new Label();
        nameAndSurname2 = new Label();
        price2 = new Label();
        calories2 = new Label();
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
                ("from PurchaseEntity where clientByClientid.id="+currentClientId).getResultList();
        System.out.println(purchaseList);
        ArrayList<ItemEntity> itemList = new ArrayList<>();
        for(PurchaseEntity p : purchaseList){
            itemList.add(p.getItemByMenuid());
        }
        products = FXCollections.observableArrayList(itemList);
        orderTable.setItems(products);
        System.out.println(products);
        //Creating and sending an order
        session.getTransaction().commit();


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
        price1.setText("Total price: ");
        price1.setMinWidth(200);
        price2.setText(clientEntity.getOrderTotal().toString() + "\n" + "\n" );
        price2.setMinWidth(200);
        calories1.setText("Total calories: ");
        calories1.setMinWidth(200);
        calories2.setText(clientEntity.getCaloriesTotal().toString()+ "\n"+ "\n" );
        calories2.setMinWidth(200);
        nameAndSurname1.setText("Name and surname:");
        nameAndSurname1.setMinWidth(200);
        nameAndSurname2.setText(clientEntity.getName() + " " + clientEntity.getSurname()+ "\n"+ "\n" );
        nameAndSurname2.setMinWidth(200);

        VBox vBox = new VBox();
        //vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.getChildren().addAll(nameAndSurname1, nameAndSurname2, price1, price2, calories1, calories2);




        finishedButton.setMinWidth(200);
        cancelButton.setMinWidth(100);

        //Name
        TableColumn<ItemEntity, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(180);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price
        TableColumn<ItemEntity, Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(180);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Calories
        TableColumn<ItemEntity, Integer> caloriesCol = new TableColumn<>("Calories");
        caloriesCol.setMinWidth(180);
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

        //Vegetarian
        TableColumn<ItemEntity, Boolean> vegetarianCol = new TableColumn<>("Vegetarian");
        vegetarianCol.setMinWidth(180);
        vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        orderTable.setMinHeight(800);
        orderTable.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);





        GridPane.setConstraints(vBox, 0, 0);
        GridPane.setConstraints(finishedButton,2,3);
        GridPane.setConstraints(cancelButton,0,3);
        GridPane.setConstraints(orderTable, 1, 0, 2, 2);


        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        grid.getChildren().addAll(vBox,finishedButton,cancelButton, orderTable);

        Scene scene = new Scene(grid, 1000, 900);
        window.setScene(scene);
        window.showAndWait();
    }
}