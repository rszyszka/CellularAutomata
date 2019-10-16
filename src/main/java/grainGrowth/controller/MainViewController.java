package grainGrowth.controller;

import grainGrowth.model.GrainGrowth;
import grainGrowth.model.core.InputOutputUtils;
import grainGrowth.model.core.Space;
import grainGrowth.model.nucleonsGenerator.NucleonsGenerator;
import grainGrowth.model.nucleonsGenerator.inclusions.InclusionType;
import grainGrowth.model.nucleonsGenerator.inclusions.InclusionsGenerator;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
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
    private TextField inclusionsNumberTextField;
    @FXML
    private TextField inclusionSizeTextField;
    @FXML
    private Label spaceSizeLabel;
    @FXML
    private Button initializeButton;
    @FXML
    private Button generateNucleonsButton;
    @FXML
    private Button generateInclusionsButton;
    @FXML
    private Button performGrainGrowthButton;
    @FXML
    private MenuBar menuBar;

    private int xSize;
    private int ySize;
    private int nucleonsNumber;
    private int inclusionsNumber;
    private int inclusionSize;
    private Space space;
    private FileChooser fileChooser;

    private final Map<Integer, Color> colorById = new HashMap<>();


    public void initializeEmptySpace() {
        xSize = Integer.parseInt(xSizeTextField.getText());
        ySize = Integer.parseInt(ySizeTextField.getText());

        space = new Space(xSize, ySize);

        adjustNodesToSpaceSize();

        clearCanvas();
    }


    private void adjustNodesToSpaceSize() {
        spaceSizeLabel.setText("X Size: " + xSize + "  Y Size: " + ySize);
        canvas.setWidth(xSize * cellSize);
        canvas.setHeight(ySize * cellSize);
    }


    private void clearCanvas() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    public void generateNucleons() {
        nucleonsNumber = Integer.parseInt(nucleonsNumberTextField.getText());
        NucleonsGenerator.putNucleonsRandomly(nucleonsNumber, space);
        draw();
    }


    public void generateInclusions() {
        inclusionsNumber = Integer.parseInt(inclusionsNumberTextField.getText());
        inclusionSize = Integer.parseInt(inclusionSizeTextField.getText());
        InclusionsGenerator.TYPE = InclusionType.CIRCULAR;
        NucleonsGenerator.putInclusionsRandomly(inclusionsNumber, inclusionSize, space);
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
        colorById.put(-1, Color.BLACK);

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("TXT", "*.txt"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
        fileChooser.setInitialDirectory(new File("."));

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
        initializeGeneratorTextField(nucleonsNumberTextField, nucleonsNumber);

        inclusionsNumber = 50;
        initializeGeneratorTextField(inclusionsNumberTextField, inclusionsNumber);

        inclusionSize = 5;
        inclusionSizeTextField.setText(String.valueOf(inclusionSize));
        inclusionSizeTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!inclusionSizeTextField.getText().matches("[1-9][0-9]?")) {
                    inclusionSizeTextField.setText(String.valueOf(inclusionSize));
                }
            }
        });
    }


    private void initializeGeneratorTextField(TextField generatorTextField, int number) {
        generatorTextField.setText(String.valueOf(number));

        generatorTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!generatorTextField.getText().matches("[1-9][0-9]?+")) {
                    generatorTextField.setText(String.valueOf(number));
                } else {
                    int newNucleonsNumber = Integer.parseInt(generatorTextField.getText());
                    if (newNucleonsNumber > xSize * ySize) {
                        generatorTextField.setText(String.valueOf(number));
                    }
                }
            }
        });
    }


    void draw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (int i = 0; i < ySize; i++) {
            for (int j = 0; j < xSize; j++) {
                int id = space.getCells()[i][j].getId();
                if (id != 0) {
                    if (!colorById.containsKey(id)) {
                        generateNewColor(id);
                    }
                    gc.setFill(colorById.get(id));
                    gc.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }
        enableNodes();
    }


    private void generateNewColor(int id) {
        Random random = new Random();
        int red = random.nextInt(240) + 10;
        int green = random.nextInt(240) + 10;
        int blue = random.nextInt(240) + 10;
        Color newColor = Color.rgb(red, green, blue);
        while (colorById.containsValue(newColor)) {
            red = random.nextInt(240) + 10;
            green = random.nextInt(240) + 10;
            blue = random.nextInt(240) + 10;
            newColor = Color.rgb(red, green, blue);
        }
        colorById.put(id, newColor);
    }


    private void disableNodes() {
        nucleonsNumberTextField.setDisable(true);
        inclusionsNumberTextField.setDisable(true);
        inclusionSizeTextField.setDisable(true);
        xSizeTextField.setDisable(true);
        ySizeTextField.setDisable(true);
        initializeButton.setDisable(true);
        generateNucleonsButton.setDisable(true);
        generateInclusionsButton.setDisable(true);
        performGrainGrowthButton.setDisable(true);
        menuBar.setDisable(true);
    }


    private void enableNodes() {
        nucleonsNumberTextField.setDisable(false);
        inclusionsNumberTextField.setDisable(false);
        inclusionSizeTextField.setDisable(false);
        xSizeTextField.setDisable(false);
        ySizeTextField.setDisable(false);
        initializeButton.setDisable(false);
        generateNucleonsButton.setDisable(false);
        generateInclusionsButton.setDisable(false);
        performGrainGrowthButton.setDisable(false);
        menuBar.setDisable(false);
    }


    public void loadSpace() {
        fileChooser.setTitle("Load space");
        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file != null) {
            try {
                space = InputOutputUtils.loadSpace(file);
                xSize = space.getSizeX();
                ySize = space.getSizeY();
                adjustNodesToSpaceSize();
                clearCanvas();
                draw();
            } catch (IOException ignored) {
            }
        }
    }


    public void saveSpace() {
        fileChooser.setTitle("Save space");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());
        if (file != null) {
            String extension = FilenameUtils.getExtension(file.getName());
            try {
                if ("txt".equals(extension)) {
                    InputOutputUtils.saveSpace(space, file);
                } else {
                    WritableImage image = canvas.snapshot(new SnapshotParameters(), null);
                    ImageIO.write(SwingFXUtils.fromFXImage(image, null), extension, file);
                }
            } catch (IOException ignored) {
            }
        }
    }


    public void closeApp() {
        Platform.exit();
    }

}
