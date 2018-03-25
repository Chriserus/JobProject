package com.chriserus;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        DashBoard dashBoard = new DashBoard();
        dashBoard.displayDash(window);
}
}
