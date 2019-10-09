package grainGrowth.controller;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollBar;

import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private Canvas canvas;
    @FXML
    private ScrollBar xScrollBar;
    @FXML
    private ScrollBar yScrollBar;
    @FXML
    private Label xSize;
    @FXML
    private Label ySize;


    public void drawButtonAction() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        xSize.setText("X: " + (int) xScrollBar.getValue());
        xScrollBar.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            xSize.setText("X: " + (int) xScrollBar.getValue());
        });
        ySize.setText("Y: " + (int) yScrollBar.getValue());
        yScrollBar.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            ySize.setText("Y: " + (int) yScrollBar.getValue());
        });
    }

}
