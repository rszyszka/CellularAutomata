package com.rszyszka.msm.model.generator.structures;

import com.rszyszka.msm.model.core.Cell;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


abstract public class StructureGenerator {

    protected List<Grain> grains;
    protected List<Cell> cellsToLock;


    public abstract void generate();


    protected void blockCellsFromGrowing() {
        cellsToLock.forEach(cell -> cell.setGrowable(false));
    }


    protected void determineCellsToLock() {
        this.cellsToLock = grains.stream()
                .map(Grain::getCells)
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(LinkedList::new));
    }

}
