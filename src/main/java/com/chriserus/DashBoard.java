package com.chriserus;

import com.chriserus.hibernate.HibernateUtil;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class DashBoard {
    private ArrayList<CustomerButton> customerButtons = new ArrayList<>();
    private Stage window;

    DashBoard(Stage window) {
        this.window = window;
        int count = 0;
        System.out.println("Creating new buttons...");
        //two loops, to make 2x3 window grid
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 3; j++) {
                CustomerButton button = new CustomerButton();
                GridPane.setConstraints(button, i, j);
                customerButtons.add(button);
                button.setNumber(count);
                count++;
            }
        }
    }

    public void displayDash() {
        //setting whole dashboard
        window.setTitle("Restaurant manager");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);

        Button menu = new Button("MENU");
        Button save = new Button("SAVE");
        Button load = new Button("LOAD");
        Button archive = new Button("ARCHIVE");


        GridPane.setConstraints(menu, 2, 0);
        GridPane.setConstraints(archive, 2, 1);
        GridPane.setConstraints(save, 0, 3);
        GridPane.setConstraints(load, 1, 3);

        //setting actions on click
        menu.setOnAction(e -> {
            MenuBox menuBox = new MenuBox();
            menuBox.displayMenu();
        });

        archive.setOnAction(e -> {
            ArchiveBox archiveBox = new ArchiveBox();
            archiveBox.display();
        });

        //adding buttons to the grid
        grid.getChildren().addAll(customerButtons);
        grid.getChildren().addAll(menu, save, load, archive);

        Scene scene = new Scene(grid, 900, 600);
        window.setOnCloseRequest(e -> appClose());
        window.setScene(scene);
        window.show();
    }

    private void appClose() {
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createQuery("delete from Purchase").executeUpdate();
        session.createQuery("delete from Client ").executeUpdate();
        session.getTransaction().commit();
        Platform.exit();
        HibernateUtil.shutdown();
    }

    class CustomerButton extends Button {
        int number;
        TableBox tableBox;

        CustomerButton() {
            super();
            this.setText("Add a new customer");
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("icons/table").getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Image tableIcon = new Image(inputStream);
            this.setGraphic(new ImageView(tableIcon));
            this.setOnAction(e -> {
                //shows the tableBox if present, creates new if not
                if (tableBox == null) {
                    tableBox = new TableBox();
                } else {
                    tableBox.getWindow().showAndWait();
                }

            });
        }

        void setNumber(int number) {
            this.number = number;
        }
    }
}

