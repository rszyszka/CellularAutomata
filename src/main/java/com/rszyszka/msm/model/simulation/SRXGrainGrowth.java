package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class SRXGrainGrowth extends MonteCarloGrainGrowth {

    public SRXGrainGrowth(Space space, int numberOfIterations, double gbEnergy) {
        super(space, numberOfIterations, gbEnergy);
    }


    @Override
    protected void performIteration() {
        List<Map.Entry<Coords, Cell>> cellByCoordsEntryList = space.getCellsByCoords().entrySet()
                .stream()
                .filter(coordsCellEntry -> coordsCellEntry.getValue().isGrowable())
                .collect(Collectors.toList());

        Collections.shuffle(cellByCoordsEntryList);

        cellByCoordsEntryList.forEach(randomizedCellByCoords -> {
            List<Cell> neighbours = space.findNeighbours(randomizedCellByCoords.getKey())
                    .stream()
                    .filter(Cell::isRecrystallized)
                    .collect(Collectors.toList());
            if (neighbours.size() > 0) {
                performSRXGrowth(neighbours, randomizedCellByCoords);
            }
        });
    }

    private void performSRXGrowth(List<Cell> neighbours, Map.Entry<Coords, Cell> cellByCoords) {
        Random random = new Random();

        double currentEnergy = countEnergyBeforeSRX(cellByCoords);
        int newId = neighbours.get(random.nextInt(neighbours.size())).getId();
        double newEnergy = countEnergyAfterSRX(newId, cellByCoords);

        if (newEnergy <= currentEnergy) {
            Cell cell = cellByCoords.getValue();
            cell.setId(newId);
            cell.setRecrystallized(true);
            cell.setEnergyH(0.0);
        }
    }

    private double countEnergyBeforeSRX(Map.Entry<Coords, Cell> cellByCoords) {
        List<Cell> neighbours = space.findNeighbours(cellByCoords.getKey());
        return super.countEnergy(cellByCoords.getValue().getId(), neighbours) + cellByCoords.getValue().getEnergyH();
    }

    private double countEnergyAfterSRX(int newId, Map.Entry<Coords, Cell> cellByCoords) {
        List<Cell> neighbours = space.findNeighbours(cellByCoords.getKey());
        return super.countEnergy(newId, neighbours);
    }

}
