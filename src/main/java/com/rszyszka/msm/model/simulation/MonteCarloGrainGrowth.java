package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


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


    public void performIteration() {
        Random random = new Random();
        List<Map.Entry<Coords, Cell>> cellByCoordsEntryList = new ArrayList<>(space.getCellsByCoords().entrySet());

        for (int size = space.getSizeX() * space.getSizeY(); size > 0; size--) {
            Map.Entry<Coords, Cell> randomizedCellByCoords = cellByCoordsEntryList.remove(random.nextInt(size));
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
