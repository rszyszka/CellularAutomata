package com.rszyszka.msm.model.generator.energy;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Space;

import java.util.Random;


public class HomogeneousEnergyDistributor {

    public static void distribute(Space space, double energyValue) {
        Random random = new Random();
        for (Cell cell : space.getCellsByCoords().values()) {
            if (random.nextBoolean()) {
                double energy = energyValue + energyValue * random.nextDouble() * 0.1;
                cell.setEnergyH(energy);
            } else {
                double energy = energyValue - energyValue * random.nextDouble() * 0.1;
                cell.setEnergyH(energy);
            }
        }
    }

}
