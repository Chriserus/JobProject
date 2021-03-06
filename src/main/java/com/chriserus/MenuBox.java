package com.chriserus;

import com.chriserus.hibernate.HibernateUtil;
import com.chriserus.hibernate.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.util.List;

public class MenuBox {
    private TextField nameInput, priceInput, caloriesInput;
    private CheckBox isVegBox;
    private TableView<Item> table;

    public void displayMenu() {
        Stage window = new Stage();

        nameInput = new TextField();
        priceInput = new TextField();
        caloriesInput = new TextField();
        isVegBox = new CheckBox("Is a vegetarian?");

        table = new TableView<>();

        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Double> priceCol = new TableColumn<>("Price");
        priceCol.setMinWidth(200);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Integer> caloriesCol = new TableColumn<>("Calories");
        caloriesCol.setMinWidth(200);
        caloriesCol.setCellValueFactory(new PropertyValueFactory<>("calories"));

        TableColumn<Item, Boolean> vegetarianCol = new TableColumn<>("Vegetarian");
        vegetarianCol.setMinWidth(200);
        vegetarianCol.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        table.setItems(getProduct(false));
        table.setMinHeight(800);
        table.getColumns().addAll(nameCol, priceCol, caloriesCol, vegetarianCol);

        nameInput.setPromptText("Name");
        nameInput.setMinWidth(100);

        priceInput.setPromptText("Price");
        priceInput.setMinWidth(100);

        caloriesInput.setPromptText("Calories");
        caloriesInput.setMinWidth(100);

        Button addButton = new Button("Add");
        addButton.setMinWidth(100);
        Button deleteButton = new Button("Delete");
        deleteButton.setMinWidth(100);

        addButton.setOnAction(e -> addButtonClicked());
        deleteButton.setOnAction(e -> deleteButtonClicked());


        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nameInput, priceInput, caloriesInput, isVegBox);

        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10, 10, 10, 10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(addButton, deleteButton);


        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Menu of the restaurant");

        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox, hBox2);
        Scene scene = new Scene(vBox, 802, 900);
        window.setScene(scene);
        window.show();
    }

    public ObservableList<Item> getProduct(boolean isVeg) {
        List<Item> itemsList;
        ObservableList<Item> products;
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            if (isVeg) {
                session.beginTransaction();
                itemsList = session.createQuery("from Item where vegetarian=true ").getResultList();
            } else {
                session.beginTransaction();
                itemsList = session.createQuery("from Item ").getResultList();
            }
            products = FXCollections.observableArrayList(itemsList);
            session.getTransaction().commit();
        }
        return products;
    }

    private void addButtonClicked() {
        Item product = new Item();
        product.setName(nameInput.getText());
        product.setPrice(Double.parseDouble(priceInput.getText()));
        product.setCalories(Integer.parseInt(caloriesInput.getText()));
        product.setVegetarian(isVegBox.isSelected());
        table.getItems().add(product);
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
        nameInput.clear();
        priceInput.clear();
        caloriesInput.clear();
        isVegBox.setSelected(false);
    }

    private void deleteButtonClicked() {
        ObservableList<Item> productSelected, allProducts;
        allProducts = table.getItems();
        productSelected = table.getSelectionModel().getSelectedItems();
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Item product = table.getSelectionModel().getSelectedItem();
            session.delete(product);
            session.getTransaction().commit();
            productSelected.forEach(allProducts::remove);
        } catch (Exception e) {
            AlertBox alert = new AlertBox();
            alert.display("You can't delete that entry! \n Someone ordered it!");
        }


    }
}
