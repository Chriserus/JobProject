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
    private Label nameAndSurname, price,  calories;
    private GridPane grid;
    private TableView<ItemEntity> orderTable;
    private int currentClientId;
    private Scene scene;
    private Stage window;
    private VBox vBox;


    OccupiedBox(){
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

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

    public ClientEntity getClientEntity(){
        return clientEntity;
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

        window.setTitle("Current client");

        System.out.println("I got client: " + clientEntity.getName() + clientEntity.getSurname() + clientEntity.getId() +
        " " + clientEntity.getOrderTotal());
        //creating gui components
        price.setText("Total price: " + "\n"+"$"+clientEntity.getOrderTotal().toString());
        price.setMinWidth(200);
        calories.setText("Total calories: " +"\n"+clientEntity.getCaloriesTotal().toString());
        calories.setMinWidth(200);
        nameAndSurname.setText("Name and surname: "+"\n"+clientEntity.getName() + " " + clientEntity.getSurname());
        nameAndSurname.setMinWidth(200);

        if(vBox.getChildren().isEmpty()){
            vBox.setSpacing(10);
            vBox.setAlignment(Pos.CENTER_LEFT);
            vBox.getChildren().addAll(nameAndSurname, price, calories);
        }





        finishedButton.setMinWidth(200);
        cancelButton.setMinWidth(100);

        if(orderTable.getColumns().isEmpty()){
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
        }






        GridPane.setConstraints(vBox, 0, 0);
        GridPane.setConstraints(finishedButton,2,3);
        GridPane.setConstraints(cancelButton,0,3);
        GridPane.setConstraints(orderTable, 1, 0, 2, 2);


        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        if(grid.getChildren().isEmpty()){
            grid.getChildren().addAll(vBox,finishedButton,cancelButton, orderTable);
            scene = new Scene(grid, 1000, 900);
        }



        window.setScene(scene);
        window.showAndWait();
    }
}