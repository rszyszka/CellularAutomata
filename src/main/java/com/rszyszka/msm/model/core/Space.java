package com.rszyszka.msm.model.core;

import java.util.LinkedList;
import java.util.List;


public class Space {

    private final int sizeX;
    private final int sizeY;

    private AbsorbentBoundaryCondition boundaryCondition;
    private MooreNeighbourhood mooreNeighbourHood;
    private Cell[][] cells;


    public Space(int sizeX, int sizeY) {
        boundaryCondition = new AbsorbentBoundaryCondition(sizeX, sizeY);
        mooreNeighbourHood = new MooreNeighbourhood(boundaryCondition);

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        cells = new Cell[sizeY][sizeX];

        initializeCells();
    }


    public Space(Space otherSpace) {
        sizeX = otherSpace.getSizeX();
        sizeY = otherSpace.getSizeY();
        cells = new Cell[sizeY][sizeX];
        boundaryCondition = otherSpace.getBoundaryCondition();
        mooreNeighbourHood = otherSpace.getMooreNeighbourHood();
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                cells[i][j] = new Cell();
                cells[i][j].copyPropertiesFromOtherCell(otherSpace.getCells()[i][j]);
            }
        }
    }


    private void initializeCells() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                cells[i][j] = new Cell();
            }
        }
    }


    public int getSizeX() {
        return sizeX;
    }


    public int getSizeY() {
        return sizeY;
    }


    public Cell[][] getCells() {
        return cells;
    }


    public void determineBorderCells() {
        Coords coords = new Coords(0, 0);
        for (int i = 0; i < sizeY; i++) {
            coords.setY(i);
            for (int j = 0; j < sizeX; j++) {
                coords.setX(j);
                List<Coords> neighbours = mooreNeighbourHood.findNeighboursCoords(coords);
                for (Coords neighbourCoords : neighbours) {
                    Cell cell = getCell(coords);
                    Cell neighbour = getCell(neighbourCoords);
                    if (cell.getId() != neighbour.getId()) {
                        neighbour.setGrainBoundary(true);
                        cell.setGrainBoundary(true);
                    }
                }
            }
        }
    }


    public void resetBorderProperty() {
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                cells[i][j].setGrainBoundary(false);
            }
        }
    }


    public int findMaxCellId() {
        int maxCellsId = 0;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                int id = cells[i][j].getId();
                if (id > maxCellsId) {
                    maxCellsId = id;
                }
            }
        }
        return maxCellsId;
    }


    public List<Cell> findNeighbours(Coords coords) {
        return getNeighboursCells(mooreNeighbourHood.findNeighboursCoords(coords));
    }


    public List<Cell> getNeighboursCells(List<Coords> neighboursCoords) {
        List<Cell> neighbours = new LinkedList<>();

        for (Coords c : neighboursCoords) {
            neighbours.add(getCell(c));
        }
        return neighbours;
    }


    public Cell getCell(Coords coords) {
        return cells[coords.getY()][coords.getX()];
    }


    public AbsorbentBoundaryCondition getBoundaryCondition() {
        return boundaryCondition;
    }


    public MooreNeighbourhood getMooreNeighbourHood() {
        return mooreNeighbourHood;
    }


    public void setMooreNeighbourHood(MooreNeighbourhood mooreNeighbourHood) {
        this.mooreNeighbourHood = mooreNeighbourHood;
    }

}
