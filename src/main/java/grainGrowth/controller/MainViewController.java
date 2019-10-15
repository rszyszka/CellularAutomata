package grainGrowth.controller;

import grainGrowth.model.GrainGrowth;
import grainGrowth.model.core.NucleonsGenerator;
import grainGrowth.model.core.Space;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private Canvas canvas;
    private final int cellSize = 2;
    @FXML
    private TextField xSizeTextField;
    @FXML
    private TextField ySizeTextField;
    @FXML
    private TextField nucleonsNumberTextField;
    @FXML
    private Label spaceSizeLabel;
    @FXML
    private Button initializeButton;
    @FXML
    private Button generateNucleonsButton;
    @FXML
    private Button performGrainGrowthButton;


    private int xSize;
    private int ySize;
    private int nucleonsNumber;
    private Space space;

    private Map<Integer, Color> colorById;


    public void initializeEmptySpace() {
        xSize = Integer.parseInt(xSizeTextField.getText());
        ySize = Integer.parseInt(ySizeTextField.getText());

        space = new Space(xSize, ySize);
        colorById = new HashMap<>();

        spaceSizeLabel.setText("X Size: " + xSize + "  Y Size: " + ySize);

        canvas.setWidth(xSize * cellSize);
        canvas.setHeight(ySize * cellSize);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public void generateNucleons() {
        nucleonsNumber = Integer.parseInt(nucleonsNumberTextField.getText());
        NucleonsGenerator.putNucleonsRandomly(nucleonsNumber, space);
        draw();
    }


    public void performGrainGrowth() {
        disableNodes();
        GrainGrowth grainGrowth = new GrainGrowth(space);
        SimulationThread simulationThread = new SimulationThread(this, grainGrowth);
        simulationThread.start();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        xSize = 400;
        ySize = 400;

        xSizeTextField.setText(String.valueOf(xSize));
        ySizeTextField.setText(String.valueOf(ySize));

        xSizeTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!xSizeTextField.getText().matches("[1-9][0-9]{0,2}|1000")) {
                    xSizeTextField.setText(String.valueOf(xSize));
                }
            }
        });

        ySizeTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!ySizeTextField.getText().matches("[1-9][0-9]{0,2}|1000")) {
                    ySizeTextField.setText(String.valueOf(ySize));
                }
            }
        });

        initializeEmptySpace();

        nucleonsNumber = 100;
        nucleonsNumberTextField.setText(String.valueOf(nucleonsNumber));

        nucleonsNumberTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!nucleonsNumberTextField.getText().matches("[1-9][0-9]+")) {
                    nucleonsNumberTextField.setText(String.valueOf(nucleonsNumber));
                } else {
                    int newNucleonsNumber = Integer.parseInt(nucleonsNumberTextField.getText());
                    if (newNucleonsNumber > xSize * ySize) {
                        nucleonsNumberTextField.setText(String.valueOf(nucleonsNumber));
                    }
                }
            }
        });
    }


    public void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                int id = space.getCells()[i][j].getId();
                if (id != 0) {
                    if (!colorById.containsKey(id)) {
                        Random random = new Random();
                        Color newColor = Color.color(random.nextDouble(), random.nextDouble(), random.nextDouble());
                        colorById.put(id, newColor);
                    }
                    gc.setFill(colorById.get(id));
                    gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        enableNodes();
    }


    private void disableNodes() {
        nucleonsNumberTextField.setDisable(true);
        xSizeTextField.setDisable(true);
        ySizeTextField.setDisable(true);
        initializeButton.setDisable(true);
        generateNucleonsButton.setDisable(true);
        performGrainGrowthButton.setDisable(true);
    }


    private void enableNodes() {
        nucleonsNumberTextField.setDisable(false);
        xSizeTextField.setDisable(false);
        ySizeTextField.setDisable(false);
        initializeButton.setDisable(false);
        generateNucleonsButton.setDisable(false);
        performGrainGrowthButton.setDisable(false);
    }

}
