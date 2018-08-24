package com.zlkj.fx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField userName;
    @FXML
    private PasswordField password;
    @FXML
    private Text actiontarget;

    @FXML
    public void init() {
    }

    //监听整个panel的key 事件，而不是某一个元素的
    @FXML
    public void onEnter(KeyEvent e) throws Exception {
        if (e.getCode() == KeyCode.ENTER) {
            loginConfirm();
        }

    }

    @FXML
    protected void handleSubmitButtonAction(ActionEvent event) throws Exception {
        loginConfirm();
    }

    public void loginConfirm() throws Exception {
        if (userName.getText().equals("") || password.getText().equals("")) {
            actiontarget.setText("用户名/密码不能为空");
        } else if (userName.getText().equals("admin") && password.getText().equals("111111")) {
            App open = new App();
            open.start(new Stage());
            Login.primaryStage.hide();
        } else {
            actiontarget.setText("用户名或密码错误");
        }
    }
}
