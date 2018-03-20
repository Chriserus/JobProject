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
        nameColO.setMinWidth(200);
        nameColO.setCellValueFactory(new PropertyValueFactory<>("name"));

        //Price
        TableColumn<ItemEntity, Double> priceColO = new TableColumn<>("Price");
        priceColO.setMinWidth(200);
        priceColO.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Calories
        TableColumn<ItemEntity, Integer> caloriesColO = new TableColumn<>("Calories");
        caloriesColO.setMinWidth(200);
        caloriesColO.setCellValueFactory(new PropertyValueFactory<>("calories"));

        //Vegetarian
        TableColumn<ItemEntity, Boolean> vegetarianColO = new TableColumn<>("Vegetarian");
        vegetarianColO.setMinWidth(200);
        vegetarianColO.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        table.getColumns().addAll(nameCol,priceCol,caloriesCol,vegetarianCol);
        tableOrder.getColumns().addAll(nameColO,priceColO,caloriesColO,vegetarianColO);
        table.setItems(getProduct());



        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button finalizeButton = new Button("Finalize");

        //Adding text fields for order
        TextField priceSum = new TextField();
        priceSum.setPromptText("Total");
        priceSum.setEditable(false);
        priceSum.setMinWidth(100);

        TextField caloriesSum = new TextField();
        caloriesSum.setPromptText("Calories");
        caloriesSum.setEditable(false);
        caloriesSum.setMinWidth(100);

        addButton.setOnAction(e->addButtonClicked());
        removeButton.setOnAction(e->deleteButtonClicked());
        finalizeButton.setOnAction(e -> finalizeButtonClicked());


        //Layout
        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);
       // gp.setPadding(new Insets(10,10,10,10));

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(addButton, removeButton);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.getChildren().addAll(caloriesSum, priceSum, finalizeButton);


        GridPane.setConstraints(table, 0, 0, 1, 2);
        GridPane.setConstraints(vBox, 1, 0);
        GridPane.setConstraints(tableOrder, 2, 0);
        GridPane.setConstraints(hBox,2,1);


        gp.getChildren().addAll(table, vBox, tableOrder, hBox);

        Scene scene = new Scene(gp);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order creator");

        window.setScene(scene);
        window.show();
    }

    private static void finalizeButtonClicked() {

    }

    private static void addButtonClicked(){
        ObservableList<ItemEntity> productSelected;
        productSelected = table.getSelectionModel().getSelectedItems();
        tableOrder.getItems().addAll(productSelected);
    }

    private static void deleteButtonClicked(){
        ObservableList<ItemEntity> productSelected, allProducts;
        allProducts = tableOrder.getItems();
        productSelected = tableOrder.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);

    }

}
