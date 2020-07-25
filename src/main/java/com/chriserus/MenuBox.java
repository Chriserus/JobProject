package com.chriserus;

import com.chriserus.hibernate.HibernateUtil;
import com.chriserus.hibernate.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MenuBox {
    private  TextField nameInput, priceInput, caloriesInput;
    private  CheckBox isVegBox;
    private  TableView<Item> table;

    public  void displayMenu(){
        Stage window = new Stage();

        //Adding labels and textFields
        nameInput = new TextField();
        priceInput = new TextField();
        caloriesInput = new TextField();
        isVegBox = new CheckBox("Is a vegetarian?");

        //Adding table
        table = new TableView<>();

        //Name
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price
        TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(200);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Calories
        TableColumn<Item, Integer> caloriesCol = new TableColumn<>("Calories");
        caloriesCol.setMinWidth(200);
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

        //Vegetarian
        TableColumn<Item, Boolean> vegetarianCol = new TableColumn<>("Vegetarian");
        vegetarianCol.setMinWidth(200);
        vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        table.setItems(getProduct(false));
        table.setMinHeight(800);
        table.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);

        //Creating input fields
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        priceInput.setPromptText("Price");
        priceInput.setMinWidth(100);

        caloriesInput.setPromptText("Calories");
        caloriesInput.setMinWidth(100);

        //Add and delete buttons
        Button addButton = new Button("Add");
        addButton.setMinWidth(100);
        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(100);

        //Adding functionality to buttons
        addButton.setOnAction(e->addButtonClicked());
        deleteButton.setOnAction(e->deleteButtonClicked());



        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, caloriesInput, isVegBox);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(addButton,deleteButton);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Menu of the restaurant");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox, hBox2);
        Scene scene = new Scene(vBox, 802, 900);
        window.setScene(scene);
        window.show();
    }

    ObservableList<Item> getProduct(boolean isVeg){

        List<Item> itemsList;
        ObservableList<Item> products;
        //getting the factory
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        //getting the list of items

        if(isVeg){
            session.beginTransaction();
            itemsList = session.createQuery("from Item where vegetarian=true ").getResultList();
            products = FXCollections.observableArrayList(itemsList);
            session.getTransaction().commit();
        }else{
            session.beginTransaction();
            itemsList = session.createQuery("from Item ").getResultList();
            products = FXCollections.observableArrayList(itemsList);
            session.getTransaction().commit();
        }
        return products;
    }

    private void addButtonClicked(){
        Item product = new Item();
        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setCalories(Integer.parseInt(caloriesInput.getText()));
        product.setVegetarian(isVegBox.isSelected());
        table.getItems().add(product);

        //Sending product to db
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        session.save(product);
        session.getTransaction().commit();

        //Clearing fields
        nameInput.clear();
        priceInput.clear();
        caloriesInput.clear();
        isVegBox.setSelected(false);

    }

    private void deleteButtonClicked(){
        ObservableList<Item> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();

try{
    //Deleting product from db
    SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
    Session session = sessionFactory.getCurrentSession();

    session.beginTransaction();
    Item product = table.getSelectionModel().getSelectedItem();
    session.delete(product);
    session.getTransaction().commit();

    productSelected.forEach(allProducts::remove);
}catch(Exception e){
    AlertBox alert = new AlertBox();
    alert.display("You can't delete that entry! \n Someone ordered it!");
}


    }
}
