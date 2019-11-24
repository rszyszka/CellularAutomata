package com.rszyszka.msm.model.generator.structures;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


abstract public class StructureGenerator {

    protected List<Grain> grains;
    protected Map<Coords, Cell> cellsByCoordsToLock;


    public abstract void generate();


    protected void blockCellsFromGrowing() {
        cellsByCoordsToLock.values().forEach(cell -> cell.setGrowable(false));
    }


    protected void determineCellsByCoordsToLock() {
        cellsByCoordsToLock = grains.stream()
                .map(Grain::getCellsByCoords)
                .flatMap(coordsCellMap -> coordsCellMap.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

}
