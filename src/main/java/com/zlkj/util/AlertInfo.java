package com.zlkj.util;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Optional;

public class AlertInfo {
    /**
     * 弹出一个通用的确定对话框
     *
     * @param p_message 对话框的信息
     * @return 用户点击了是或否
     */
    public static void f_alert_confirmDialog(AnchorPane mainPane, TabPane tabPane, Tab initTab, String p_message, boolean isInit) {
//        按钮部分可以使用预设的也可以像这样自己 new 一个
        Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, p_message, new ButtonType("取消", ButtonBar.ButtonData.NO),
                new ButtonType("确定", ButtonBar.ButtonData.YES));
//        设置窗口的标题
        _alert.setTitle("操作提示");
        _alert.setHeaderText("确认信息");
//        设置对话框的 icon 图标，参数是主窗口的 stage
        Stage stage = (Stage) mainPane.getScene().getWindow();
        _alert.initOwner(stage);
//        showAndWait() 将在对话框消失以前不会执行之后的代码
        Optional<ButtonType> _buttonType = _alert.showAndWait();
//        根据点击结果返回
        if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
            isInit = true;
            System.out.println("跳转到初始化");
            SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();
            selectionModel.select(initTab); //select by object
            selectionModel.select(0); //select by index starting with 0
            selectionModel.clearSelection(1);//clear
        } else {
            System.out.println("shit");
//            return false;
        }
    }

    //弹出一个信息对话框
    public static void f_alert_informationDialog(AnchorPane mainPane, String p_message) {
        Alert _alert = new Alert(Alert.AlertType.INFORMATION);
        _alert.setTitle("操作提示");
        _alert.setHeaderText("提示");
        _alert.setContentText(p_message);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        _alert.initOwner(stage);
        _alert.showAndWait();
    }
}
