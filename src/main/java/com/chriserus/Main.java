package com.chriserus;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        DashBoard dashBoard = new DashBoard(window);
        dashBoard.displayDash();
    }
}
