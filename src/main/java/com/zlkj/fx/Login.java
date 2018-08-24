package com.zlkj.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Login extends Application {
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("fxml/login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        scene.getStylesheets().add
                (Login.class.getClassLoader().getResource("css/login.css").toExternalForm());
        stage.setTitle("高铁数据导出系统");
        //设置窗口的图标.
        stage.getIcons().add(new Image(
                Login.class.getClassLoader().getResourceAsStream("image/icon.jpg")));

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
