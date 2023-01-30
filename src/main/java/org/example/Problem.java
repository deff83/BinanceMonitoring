package org.example;

import javafx.application.Platform;
import javafx.scene.control.Label;

public class Problem {

    private Label labal_p;
    private String text_p = "";
    public Problem(Label labal_p, String text_p){
        this.labal_p = labal_p;
        this.text_p = text_p;
    }


    public void problemInternet()  {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labal_p.setText(text_p);
            }
        });
        try {
            System.out.println("Problem Intenet");
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
