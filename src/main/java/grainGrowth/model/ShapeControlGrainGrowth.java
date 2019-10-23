package grainGrowth.model;

import grainGrowth.model.core.Cell;
import grainGrowth.model.core.Coords;
import grainGrowth.model.core.Space;

import java.util.HashMap;
import java.util.List;


public class ShapeControlGrainGrowth extends GrainGrowth {

    private double probabilityForForthRule;


    public ShapeControlGrainGrowth(Space space) {
        super(space);
        this.probabilityForForthRule = 0.5;
    }


    @Override
    protected void performGrowthIfPossible(Coords coords) {
        List<Cell> neighbours = space.findNeighbours(coords);
        int newId = getIdOccurringEqualToAmount(neighbours, 5);
        setNewIdIfDifferentThanZero(coords, newId);
        if (changed) {
            return;
        }

        neighbours = findNeighboursForSecondRule(coords);
        newId = getIdOccurringEqualToAmount(neighbours, 3);
        setNewIdIfDifferentThanZero(coords, newId);
        if (changed) {
            return;
        }

        neighbours = findNeighboursForThirdsRule(coords);
        newId = getIdOccurringEqualToAmount(neighbours, 3);
        setNewIdIfDifferentThanZero(coords, newId);
        if (changed) {
            return;
        }

        if (Math.random() < probabilityForForthRule) {
            neighbours = space.findNeighbours(coords);
            newId = getMostFrequentId(neighbours);
            setNewIdIfDifferentThanZero(coords, newId);
        }
    }


    private List<Cell> findNeighboursForSecondRule(Coords coords) {
        return space.getNeighboursCells(space.getMooreNeighbourHood().findNearestNeighboursCoords(coords));
    }


    private List<Cell> findNeighboursForThirdsRule(Coords coords) {
        return space.getNeighboursCells(space.getMooreNeighbourHood().findFurtherNeighboursCoords(coords));
    }


    private int getIdOccurringEqualToAmount(List<Cell> neighbours, int neededAmount) {
        HashMap<Integer, Integer> amountByGrainId = new HashMap<>();

        for (Cell cell : neighbours) {
            int id = cell.getId();
            if (id == 0 || id == -1) {
                continue;
            }
            if (amountByGrainId.containsKey(id)) {
                int amount = amountByGrainId.get(id) + 1;
                if (amount == neededAmount) {
                    return id;
                }
                amountByGrainId.put(id, amount);
            } else {
                amountByGrainId.put(id, 1);
            }
        }

        return 0;
    }


    public void setProbabilityForForthRule(double probabilityForForthRule) {
        this.probabilityForForthRule = probabilityForForthRule;
    }

}
