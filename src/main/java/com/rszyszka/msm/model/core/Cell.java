package com.rszyszka.msm.model.core;


public class Cell {

    private int id;
    private boolean growable;
    private boolean grainBoundary;


    public Cell() {
        id = 0;
        growable = true;
        grainBoundary = false;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public boolean isGrowable() {
        return growable;
    }


    public void setGrowable(boolean growable) {
        this.growable = growable;
    }


    public boolean isGrainBoundary() {
        return grainBoundary;
    }


    public void setGrainBoundary(boolean grainBoundary) {
        this.grainBoundary = grainBoundary;
    }


    public void copyPropertiesFromOtherCell(Cell cell) {
        this.id = cell.id;
        this.growable = cell.growable;
    }

}
