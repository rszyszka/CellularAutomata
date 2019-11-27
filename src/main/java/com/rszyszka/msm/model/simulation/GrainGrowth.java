package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Space;

import java.util.Observable;


public abstract class GrainGrowth extends Observable {
    protected Space space;

    public GrainGrowth(Space space) {
        this.space = space;
    }

    public abstract void simulateGrainGrowth();

    protected void updateProgress(double progress) {
        setChanged();
        notifyObservers(progress);
    }

    public Space getSpace() {
        return space;
    }
}
