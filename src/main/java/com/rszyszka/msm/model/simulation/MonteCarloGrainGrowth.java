package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.*;


public class MonteCarloGrainGrowth extends GrainGrowth {

    private int numberOfIterations;
    private double gbEnergy;


    public MonteCarloGrainGrowth(Space space, int numberOfIterations, double gbEnergy) {
        super(space);
        this.numberOfIterations = numberOfIterations;
        this.gbEnergy = gbEnergy;
    }


    @Override
    public void simulateGrainGrowth() {
        for (int i = 0; i < numberOfIterations; i++) {
            performIteration();
        }
    }


    private void performIteration() {
        Random random = new Random();
        List<Map.Entry<Coords, Cell>> cellByCoordsEntryList = new ArrayList<>(space.getCellsByCoords().entrySet());
        Collections.shuffle(cellByCoordsEntryList);

        for (Map.Entry<Coords, Cell> randomizedCellByCoords : cellByCoordsEntryList) {
            List<Cell> neighbours = space.findNeighbours(randomizedCellByCoords.getKey());
            Cell cell = randomizedCellByCoords.getValue();

            double currentEnergy = countEnergy(cell.getId(), neighbours);
            int newId = neighbours.get(random.nextInt(neighbours.size())).getId();
            double newEnergy = countEnergy(newId, neighbours);

            if (newEnergy <= currentEnergy) {
                cell.setId(newId);
            }
        }

    }


    private double countEnergy(int id, List<Cell> neighbours) {
        int count = 0;
        for (Cell cell : neighbours) {
            if (cell.getId() != id) {
                count++;
            }
        }
        return gbEnergy * count;
    }

}
