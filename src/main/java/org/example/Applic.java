package org.example;

import javafx.application.Application;
import javafx.stage.Stage;

public class Applic extends Application {
    private static Applic applic = new Applic();

    public static Applic getInstance(){return applic;}
    private Applic(){}

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.show();
    }
}
