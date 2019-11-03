package grainGrowth.model.core;


public class Cell {

    private int id;
    private boolean growable;


    public Cell() {
        id = 0;
        growable = true;
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


    public void copyPropertiesFromOtherCell(Cell cell) {
        this.id = cell.id;
        this.growable = cell.growable;
    }

}
