package grainGrowth.model.core;

import grainGrowth.controller.StructureType;

import java.util.LinkedList;
import java.util.List;


public class Grain {

    private int id;
    private List<Cell> cells;
    private StructureType structureType;


    public Grain(Space space, int id, StructureType structureType) {
        this.id = id;
        cells = new LinkedList<>();
        this.structureType = structureType;
        findGrainCells(space);
    }


    private void findGrainCells(Space space) {
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                Cell cell = space.getCells()[i][j];
                if (cell.getId() == id) {
                    cells.add(cell);
                }
            }
        }
    }


    public List<Cell> getCells() {
        return cells;
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
