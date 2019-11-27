package com.rszyszka.msm.controller;

import com.rszyszka.msm.model.simulation.GrainGrowth;
import javafx.application.Platform;

import java.util.Observable;
import java.util.Observer;


public class SimulationThread extends Thread implements Observer {

    private MainViewController controller;
    private GrainGrowth grainGrowth;


    SimulationThread(MainViewController controller, GrainGrowth grainGrowth) {
        this.controller = controller;
        this.grainGrowth = grainGrowth;
        this.grainGrowth.addObserver(this);
    }


    @Override
    public void run() {
        grainGrowth.simulateGrainGrowth();
        Platform.runLater(() -> controller.draw());
    }


    @Override
    public void update(Observable o, Object arg) {
        controller.getProgressBar().setProgress((double) arg);
    }

}
