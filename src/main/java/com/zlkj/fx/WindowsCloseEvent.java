package com.zlkj.fx;

import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import static com.zlkj.util.AlertInfo.closePane_confirmDialog;

public class WindowsCloseEvent implements EventHandler<WindowEvent> {
    private Stage stage;

    public WindowsCloseEvent(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void handle(WindowEvent event) {
        event.consume();
        closePane_confirmDialog(stage);
    }
}
