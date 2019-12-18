package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.controller.NucleationType;
import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import com.rszyszka.msm.model.generator.nucleons.SRXNucleonsGenerator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;


public class SRXGrainGrowth extends MonteCarloGrainGrowth {

    public static NucleationType NUCLEATION_TYPE = NucleationType.AT_START;

    private int nucleonsNumber;
    private int currentNucleonsNumberToPut;


    public SRXGrainGrowth(Space space, int numberOfIterations, double gbEnergy, int nucleonsNumber) {
        super(space, numberOfIterations, gbEnergy);
        this.nucleonsNumber = nucleonsNumber;
        currentNucleonsNumberToPut = nucleonsNumber;
    }


    @Override
    public void simulateGrainGrowth() {
        for (int i = 1; i <= numberOfIterations; i++) {
            updateProgress(i / (double) numberOfIterations);
            performIteration();
            applyNucleationModuleIfNeeded(i);
        }
    }

    private void applyNucleationModuleIfNeeded(int iterationNumber) {
        if (NUCLEATION_TYPE == NucleationType.CONSTANT) {
            if (iterationNumber % 3 == 0) {
                SRXNucleonsGenerator.putNucleons(nucleonsNumber, space);
            }
        } else if (NUCLEATION_TYPE == NucleationType.INCREASING) {
            if (iterationNumber % 3 == 0) {
                currentNucleonsNumberToPut += nucleonsNumber;
                SRXNucleonsGenerator.putNucleons(currentNucleonsNumberToPut, space);
            }
        }
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
