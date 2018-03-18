package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
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


public class OrderBox extends MenuBox{

    private static TableView<ItemEntity> table, tableOrder;

    public static void displayOrder(){
        Stage window = new Stage();
        //Adding table
         table = new TableView<>();
         tableOrder = new TableView<>();

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

        //SECOND TABLE COLUMNS, CAN'T REUSE COLUMN OBJECTS

        //Name
        TableColumn<ItemEntity, String> nameColO = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price
        TableColumn<ItemEntity, Double> priceColO = new TableColumn<>("Price");
        priceCol.setMinWidth(200);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Calories
        TableColumn<ItemEntity, Integer> caloriesColO = new TableColumn<>("Calories");
        caloriesCol.setMinWidth(200);
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

        //Vegetarian
        TableColumn<ItemEntity, Boolean> vegetarianColO = new TableColumn<>("Vegetarian");
        vegetarianCol.setMinWidth(200);
        vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        table.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);
        tableOrder.getColumns().addAll(nameColO,priceColO,caloriesColO,vegetarianColO);
        table.setItems(getProduct());



        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");

        addButton.setOnAction(e->addButtonClicked());
       removeButton.setOnAction(e->deleteButtonClicked());

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(addButton, removeButton);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(table, vBox, tableOrder);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order creator");


        
        Scene scene = new Scene(hBox);

        window.setScene(scene);
        window.show();
    }

    private static void addButtonClicked(){
        ObservableList<ItemEntity> productSelected;
        productSelected = table.getSelectionModel().getSelectedItems();
        tableOrder.setItems(productSelected);
    }

    private static void deleteButtonClicked(){
        ObservableList<ItemEntity> productSelected, allProducts;
        allProducts = tableOrder.getItems();
        productSelected = tableOrder.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);

    }

}
