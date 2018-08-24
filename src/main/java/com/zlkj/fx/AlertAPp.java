package com.zlkj.fx;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.util.Optional;


public class AlertAPp extends Application {
    Stage primaryStage;
    @Override
    public void start(Stage stage) throws Exception {
        StackPane pane = new StackPane();
        primaryStage = stage;
        Scene scene = new Scene(pane, 500, 500);
        stage.setScene(scene);

        Button button = new Button("ok");
        pane.getChildren().add(button);
        stage.setTitle("高铁数据导出系统");
        //设置窗口的图标.
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                Alert alert = new Alert(Alert.AlertType.WARNING, "设备已经初始化！");
//                alert.setTitle("操作提示"); //设置标题，不设置默认标题为本地语言的information
//                alert.setHeaderText("警告"); //设置头标题，默认标题为本地语言的information
//                Button infor = new Button("show Information");
//                infor.setOnAction((ActionEvent) -> {
//                    alert.showAndWait(); //显示弹窗，同时后续代码等挂起
//                });
//                alert.show();
//                f_alert_confirmDialog("xxxx", "vvvvvv");
                try {
                    f_alert_informationDialog("fuck");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        stage.getIcons().add(new Image(
                AlertAPp.class.getClassLoader().getResourceAsStream("image/icon.jpg")));

        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * 弹出一个通用的确定对话框
     *
     * @param p_header  对话框的信息标题
     * @param p_message 对话框的信息
     * @return 用户点击了是或否
     */
    public void f_alert_confirmDialog(String p_header, String p_message) {
//        按钮部分可以使用预设的也可以像这样自己 new 一个
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, p_message, new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
//        设置窗口的标题
        _alert.setTitle("确认");
        _alert.setHeaderText(p_header);
//        设置对话框的 icon 图标，参数是主窗口的 stage
//        _alert.initOwner(d_stage);
//        showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> _buttonType = _alert.showAndWait();
//        根据点击结果返回
        if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            System.out.println("fuck");
//            return true;
        } else {
            System.out.println("shit");
//            return false;
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        _alert.hide();
    }
    //弹出一个信息对话框
    public void f_alert_informationDialog(String p_message) throws InterruptedException {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("操作提示");
        _alert.setHeaderText("提示");
        _alert.setContentText(p_message);
        _alert.initOwner(primaryStage);
        _alert.showAndWait();
//        Thread.sleep(3000);
//        _alert.hide();
    }


}
