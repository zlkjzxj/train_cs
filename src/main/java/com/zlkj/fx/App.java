package com.zlkj.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class App extends Application {


    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("高铁数据导出系统");
        AnchorPane root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (App.class.getClassLoader().getResource("css/app.css").toExternalForm());
        primaryStage.setScene(scene);
        //设置窗口的图标.
        primaryStage.getIcons().add(new Image(
                App.class.getClassLoader().getResourceAsStream("image/icon.jpg")));
        primaryStage.show();

//        //menu 按钮组
//        HBox hbox = new HBox(8);// space
//        Button button1 = new Button("初始化               ");
//        Button button2 = new Button("数据导出   ");
//        HBox.setHgrow(button1, Priority.ALWAYS);
//        HBox.setHgrow(button2, Priority.ALWAYS);
//        button1.setMaxWidth(Double.MAX_VALUE);
//        button2.setMaxWidth(Double.MAX_VALUE);
//        hbox.getChildren().addAll(button1, button2);
//
//        //进度条组
//
//        //1.
//        Label label = new Label("初始化进度:");
//
//        hbox.setPrefWidth(1000);
//        hbox.setPrefHeight(800);
//
//        HBox hbox1 = new HBox(20);
//        hbox1.setPrefHeight(700);
//        hbox1.setAlignment(Pos.CENTER);
//        hbox1.setPadding(new Insets(50, 5, 0, 400));
//        //2.
//        ProgressBar pb = new ProgressBar(0.6);
////        ProgressIndicator pi = new ProgressIndicator(0.6);
//
//        //3.
//        Button button3 = new Button("初始化");
//        hbox1.getChildren().addAll(label, pb, button3);
//
//        root.getChildren().add(hbox);
//        root.getChildren().add(hbox1);
//        primaryStage.setScene(scene);
//        primaryStage.setResizable(false);
////        primaryStage.setFullScreen(true);
//        primaryStage.show();

    }

}

