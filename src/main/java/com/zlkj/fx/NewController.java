package com.zlkj.fx;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.lang.reflect.Array;

public class NewController {
    @FXML
    private AnchorPane pane;

    @FXML
    public void initialize() throws Exception {
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(5));
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);
        String[][] array = new String[][]{{"a", "b"}, {"c", "d"}};
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {
                Label label = new Label(array[x][y]);
                gridpane.add(label, x, y);
            }
        }
    }
}
