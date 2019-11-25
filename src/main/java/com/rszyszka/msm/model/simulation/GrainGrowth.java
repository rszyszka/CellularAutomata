package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Space;


public abstract class GrainGrowth {
    protected Space space;

    public GrainGrowth(Space space) {
        this.space = space;
    }

    public abstract void simulateGrainGrowth();

    public Space getSpace() {
        return space;
    }
}
