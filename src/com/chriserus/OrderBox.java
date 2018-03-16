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

    public static void displayOrder(){
        Stage window = new Stage();
        //Adding table
        TableView<ItemEntity> table = new TableView<>();

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
        table.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table);
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order creator");




        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
    }

}
