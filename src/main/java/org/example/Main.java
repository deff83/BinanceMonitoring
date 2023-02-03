package org.example;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.Asset;
import com.binance.api.client.domain.market.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public Info task;

    public static void main(String[] args) {

        Application.launch();



    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane root = new AnchorPane();
        FlowPane root_flow_up = new FlowPane();
        FlowPane root_flow_up2 = new FlowPane();//вторая строка
        VBox vBox = new VBox();


        ProgressBar bar = new ProgressBar();
        Label label = new Label();
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(false);
        CheckBox checkBox2 = new CheckBox();
        checkBox2.setSelected(true);
        checkBox2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                task.setBool_isk(checkBox2.isSelected());
            }
        });


        Button button = new Button();
        button.setText("Start");
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                root_flow_up2.setDisable(true);
                new Thread(task).start();
            }
        });

        TextField textField = new TextField();
        textField.setText("USDT");


        task = new Info(label);

        checkBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                task.setTextfield(textField.getText()+"");
                task.setCheckbox_filter(checkBox.isSelected());
            }
        });



        label.setText("Waiting to start");


        bar.prefWidthProperty().bind(root_flow_up.widthProperty().subtract(20));;
        bar.progressProperty().bind(task.progressProperty());

        root_flow_up2.getChildren().addAll(checkBox, textField, button, checkBox2);//вторая строка
        vBox.getChildren().addAll(root_flow_up, root_flow_up2);
        AnchorPane.setTopAnchor(vBox, 10.0);



        root_flow_up.setLayoutX(10.0);
        root_flow_up.setLayoutY(10.0);
        root_flow_up.getChildren().addAll(bar,label);
        root.getChildren().addAll(vBox);

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