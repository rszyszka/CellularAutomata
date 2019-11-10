package com.rszyszka.msm.model.generator.structures;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.LinkedList;
import java.util.List;


public class Grain {

    private int id;
    private List<Cell> cells;
    private List<Coords> coords;
    private StructureType structureType;


    public Grain(Space space, int id, StructureType structureType) {
        this.id = id;
        cells = new LinkedList<>();
        coords = new LinkedList<>();
        this.structureType = structureType;
        findGrainCells(space);
    }


    private void findGrainCells(Space space) {
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                Cell cell = space.getCells()[i][j];
                if (cell.getId() == id) {
                    cells.add(cell);
                    coords.add(new Coords(j, i));
                }
            }
        }
    }


    public List<Cell> getCells() {
        return cells;
    }


    public List<Coords> getCoords() {
        return coords;
    }


    public StructureType getStructureType() {
        return structureType;
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

}
