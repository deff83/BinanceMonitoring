package org.example;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public Task task;

    public static void main(String[] args) {

        Application.launch();



    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();
        FlowPane root_flow_up = new FlowPane();



        ProgressBar bar = new ProgressBar();
        Label label = new Label();
        label.setText("test");

        task = new Info(label);

        bar.prefWidthProperty().bind(root_flow_up.widthProperty().subtract(20));;
        bar.progressProperty().bind(task.progressProperty());
        AnchorPane.setTopAnchor(root_flow_up, 10.0);
        new Thread(task).start();
        root_flow_up.setLayoutX(10.0);
        root_flow_up.setLayoutY(10.0);
        root_flow_up.getChildren().addAll(bar,label);
        root.getChildren().addAll(root_flow_up);

        Scene scene = new Scene(root, 500, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        primaryStage.show();


    }

    @Override
    public void stop() throws Exception {
        task.cancel();
        super.stop();
        System.exit(0);
    }
}