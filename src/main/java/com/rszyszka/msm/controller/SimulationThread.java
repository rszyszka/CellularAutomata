package com.rszyszka.msm.controller;

import com.rszyszka.msm.model.simulation.GrainGrowth;
import javafx.application.Platform;


public class SimulationThread extends Thread {

    private MainViewController controller;
    private GrainGrowth grainGrowth;


    SimulationThread(MainViewController controller, GrainGrowth grainGrowth) {
        this.controller = controller;
        this.grainGrowth = grainGrowth;
    }


    @Override
    public void run() {
        grainGrowth.simulateGrainGrowth();
        Platform.runLater(() -> controller.draw());
    }

}
