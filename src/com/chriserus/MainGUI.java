package com.chriserus;

import com.chriserus.hibernate.ClientEntity;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.image.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MainGUI /*extends Application*/ {

    //Stage window;

    public static void main(String[] args) {
        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(ClientEntity.class)
                .buildSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            System.out.println("Creating new client object...");
            ClientEntity tempClient = new ClientEntity();
            tempClient.setName("Marek");
            tempClient.setSurname("Browarek");
            tempClient.setVegetarian(true);
            session.beginTransaction();
            System.out.println("Saving new client object...");
            session.save(tempClient);
            session.getTransaction().commit();
            System.out.println("Done new client object...");

        } finally {
            factory.close();
        }
        //  launch(args);
    }

  /*  @Override
    public void start(Stage primaryStage){
        //setting whole dashboard
        window = primaryStage;
        window.setTitle("Restaurant manager");

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);


        //creating all the buttons
        CustomerButton button1 = new CustomerButton();
        CustomerButton button2 = new CustomerButton();
        CustomerButton button3 = new CustomerButton();
        CustomerButton button4 = new CustomerButton();
        CustomerButton button5 = new CustomerButton();
        CustomerButton button6 = new CustomerButton();

        Button menu = new Button("MENU");
        Button save = new Button("SAVE");
        Button load = new Button("LOAD");
        Button archive = new Button("ARCHIVE");



        //where to put buttons on grid?
        GridPane.setConstraints(button1, 0, 0);
        GridPane.setConstraints(button2, 1, 0);
        GridPane.setConstraints(button3, 0, 1);
        GridPane.setConstraints(button4, 1, 1);
        GridPane.setConstraints(button5, 0, 2);
        GridPane.setConstraints(button6, 1, 2);

        GridPane.setConstraints(menu, 2, 0);
        GridPane.setConstraints(archive, 2, 1);
        GridPane.setConstraints(save, 0, 3);
        GridPane.setConstraints(load, 1, 3);

        //setting actions on click
        menu.setOnAction(e -> {
            MenuBox.displayMenu();
        });

        archive.setOnAction(e -> {
            ArchiveBox.displayArchive();
        });

        //adding buttons to the grid
        grid.getChildren().addAll(button1, button2, button3, button4, button5, button6, menu, save, load, archive);

        Scene scene = new Scene(grid, 900, 600);
        window.setScene(scene);
        window.show();
    }
*/
}
