package com.chriserus;

import com.chriserus.hibernate.ItemEntity;
import javafx.application.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MenuBox {
    private static TextField nameInput, priceInput, caloriesInput;
    private static CheckBox isVegBox;
    private static TableView<ItemEntity> table;

    public static void displayMenu(){
        Stage window = new Stage();

        //Adding labels and textFields
        nameInput = new TextField();
        priceInput = new TextField();
        caloriesInput = new TextField();
        isVegBox = new CheckBox("Is a vegetarian?");

        //Adding table
        table = new TableView<>();

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

        table.setItems(getProduct());
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

    public static ObservableList<ItemEntity> getProduct(){

        //getting the factory
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        //getting the list of items
        session.beginTransaction();
        List<ItemEntity> itemsList = session.createQuery("from ItemEntity ").getResultList();
        ObservableList<ItemEntity> products = FXCollections.observableArrayList(itemsList);
        session.getTransaction().commit();

        return products;
    }

    private static void addButtonClicked(){
        ItemEntity product = new ItemEntity();
        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setCalories(Integer.parseInt(caloriesInput.getText()));
        product.setVegetarian(isVegBox.isSelected());
        table.getItems().add(product);

        //Sending product to db
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
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

    private static void deleteButtonClicked(){
        ObservableList<ItemEntity> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();


        //Deleting product from db
        SessionFactory sessionFactory = HibernateFactory.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();
        ItemEntity product = table.getSelectionModel().getSelectedItem();
        session.delete(product);
        session.getTransaction().commit();

        productSelected.forEach(allProducts::remove);

    }
}
