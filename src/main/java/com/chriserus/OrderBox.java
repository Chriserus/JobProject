package com.chriserus;

import com.chriserus.hibernate.Client;
import com.chriserus.hibernate.HibernateUtil;
import com.chriserus.hibernate.Item;
import com.chriserus.hibernate.Purchase;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class OrderBox extends MenuBox {
    private TableView<Item> table, tableOrder;
    private TextField caloriesSum, priceSum;
    private ObservableList<Item> productSelected, wholeOrder;
    private Client currentClient;
    private Stage window;
    private final List<TableBox.SeatButton> tableButtons;
    private final int number;

    OrderBox(TableBox tableBox, int number) {
        this.tableButtons = tableBox.getTableButtons();
        this.number = number;
    }

    public void display(Client client) {
        currentClient = client;
        window = new Stage();
        table = new TableView<>();
        tableOrder = new TableView<>();

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

        //SECOND TABLE COLUMNS, CAN'T REUSE COLUMN OBJECTS

        TableColumn<Item, String> nameColO = new TableColumn<>("Name");
        nameColO.setMinWidth(200);
        nameColO.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Item, Double> priceColO = new TableColumn<>("Price");
        priceColO.setMinWidth(200);
        priceColO.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Item, Integer> caloriesColO = new TableColumn<>("Calories");
        caloriesColO.setMinWidth(200);
        caloriesColO.setCellValueFactory(new PropertyValueFactory<>("calories"));

        TableColumn<Item, Boolean> vegetarianColO = new TableColumn<>("Vegetarian");
        vegetarianColO.setMinWidth(200);
        vegetarianColO.setCellValueFactory(new PropertyValueFactory<>("vegetarian"));

        table.getColumns().addAll(nameCol, priceCol, caloriesCol, vegetarianCol);
        tableOrder.getColumns().addAll(nameColO, priceColO, caloriesColO, vegetarianColO);

        if (client.isVegetarian()) {
            table.setItems(getProduct(true));
        } else {
            table.setItems(getProduct(false));
        }


        Button addButton = new Button("Add");
        Button removeButton = new Button("Remove");
        Button finalizeButton = new Button("Finalize");

        priceSum = new TextField();
        priceSum.setPromptText("Total");
        priceSum.setEditable(false);
        priceSum.setMinWidth(50);

        Label priceLabel = new Label("Total:");

        caloriesSum = new TextField();
        caloriesSum.setPromptText("Calories");
        caloriesSum.setEditable(false);
        caloriesSum.setMinWidth(50);

        Label caloriesLabel = new Label("Calories:");

        addButton.setOnAction(e -> addButtonClicked());
        removeButton.setOnAction(e -> deleteButtonClicked());
        finalizeButton.setOnAction(e -> finalizeButtonClicked());
        window.setOnCloseRequest(e -> {
            e.consume();
            deleteClient();
        });

        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(10);

        VBox vBox = new VBox();
        vBox.setSpacing(10);
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(addButton, removeButton);

        VBox vBox1 = new VBox();
        vBox1.setSpacing(10);
        vBox1.setAlignment(Pos.CENTER_LEFT);
        vBox1.getChildren().addAll(priceLabel, priceSum);

        VBox vBox2 = new VBox();
        vBox2.setSpacing(10);
        vBox2.setAlignment(Pos.CENTER_LEFT);
        vBox2.getChildren().addAll(caloriesLabel, caloriesSum);

        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER_RIGHT);
        hBox.setPadding(new Insets(10, 10, 10, 10));
        hBox.getChildren().addAll(vBox2, vBox1, finalizeButton);


        GridPane.setConstraints(table, 0, 0, 1, 3);
        GridPane.setConstraints(vBox, 1, 0);
        GridPane.setConstraints(tableOrder, 2, 0);
        GridPane.setConstraints(hBox, 2, 2);


        gp.getChildren().addAll(table, vBox, tableOrder, hBox);

        Scene scene = new Scene(gp);

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Order creator");

        window.setScene(scene);
        window.show();


    }

    private double sumPrice() {
        double sum = 0;
        wholeOrder = tableOrder.getItems();
        for (Item item : wholeOrder)
            sum += item.getPrice();
        sum = BigDecimal.valueOf(sum).setScale(3, RoundingMode.HALF_UP).doubleValue();
        return sum;
    }

    private int sumCalories() {
        int sum = 0;
        wholeOrder = tableOrder.getItems();
        for (Item item : wholeOrder)
            sum += item.getCalories();
        return sum;
    }

    private void deleteClient() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            session.delete(currentClient);
            session.getTransaction().commit();
            window.close();
        }
    }

    private void finalizeButtonClicked() {
        try (Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, currentClient.getId());
            client.setCaloriesTotal(sumCalories());
            client.setOrderTotal(sumPrice());
            for (Item item : tableOrder.getItems()) {
                Purchase purchase = new Purchase(currentClient, item);
                session.save(purchase);
            }
            session.getTransaction().commit();
        }
        window.close();
        TableBox.SeatButton button = tableButtons.get(number);
        button.setOccupied(true);
        button.updateButton();
        button.setClient(currentClient);
    }

    private void addButtonClicked() {
        productSelected = table.getSelectionModel().getSelectedItems();
        tableOrder.getItems().addAll(productSelected);
        String price = String.format("%.2f", sumPrice());
        String calories = Integer.toString(sumCalories());
        resetSum(price, calories);
    }

    private void deleteButtonClicked() {
        ObservableList<Item> allProducts = tableOrder.getItems();
        productSelected = tableOrder.getSelectionModel().getSelectedItems();
        productSelected.forEach(allProducts::remove);
        String price = String.format("%.2f", sumPrice());
        String calories = Integer.toString(sumCalories());
        resetSum(price, calories);
    }

    private void resetSum(String price, String calories) {
        priceSum.setText("$" + price);
        caloriesSum.setText(calories);
    }

}
