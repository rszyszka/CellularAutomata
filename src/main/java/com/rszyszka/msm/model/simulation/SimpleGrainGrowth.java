package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimpleGrainGrowth extends GrainGrowth {

    protected boolean changed;
    private Space nextIterationSpace;


    public SimpleGrainGrowth(Space space) {
        super(space);
        nextIterationSpace = new Space(space);
    }


    @Override
    public void simulateGrainGrowth() {
        space.resetBorderProperty();
        changed = true;
        while (changed) {
            performIteration();
        }
        space.determineBorderCells();
    }


    private void performIteration() {
        changed = false;
        space.getCellsByCoords().entrySet().stream()
                .filter(coordsCellEntry -> coordsCellEntry.getValue().getId() == 0)
                .forEach(coordsCellEntry -> performGrowthIfPossible(coordsCellEntry.getKey()));
        updateSpace();
    }


    protected void performGrowthIfPossible(Coords coords) {
        List<Cell> neighbours = space.findNeighbours(coords);
        int newId = getMostFrequentId(neighbours);
        setNewIdIfDifferentThanZero(coords, newId);
    }


    private int getMostFrequentId(List<Cell> neighbours) {
        HashMap<Integer, Integer> amountByGrainId = new HashMap<>();

        for (Cell cell : neighbours) {
            int id = cell.getId();
            if (id == 0 || !cell.isGrowable()) {
                continue;
            }
            if (amountByGrainId.containsKey(id)) {
                int amount = amountByGrainId.get(id) + 1;
                amountByGrainId.put(id, amount);
            } else {
                amountByGrainId.put(id, 1);
            }
        }

        int mostFrequentId = 0;
        int mostFrequentIdCounter = 0;
        for (Map.Entry<Integer, Integer> amountByGrainIdEntry : amountByGrainId.entrySet()) {
            int grainId = amountByGrainIdEntry.getKey();
            int amount = amountByGrainIdEntry.getValue();
            if (amount > mostFrequentIdCounter) {
                mostFrequentId = grainId;
                mostFrequentIdCounter = amount;
            }
        }

        return mostFrequentId;
    }


    protected boolean setNewIdIfDifferentThanZero(Coords coords, int newId) {
        if (newId != 0) {
            nextIterationSpace.getCell(coords).setId(newId);
            changed = true;
            return true;
        }
        return false;
    }


    private void updateSpace() {
        space.getCellsByCoords().forEach((coords, cell) -> cell.setId(nextIterationSpace.getCell(coords).getId()));
    }


}
