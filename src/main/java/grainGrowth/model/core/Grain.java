package grainGrowth.model.core;

import java.util.LinkedList;
import java.util.List;


public class Grain {

    private int id;
    private List<Cell> cells;


    public Grain(Space space, Cell cell) {
        this.id = cell.getId();
        cells = new LinkedList<>();
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

}
