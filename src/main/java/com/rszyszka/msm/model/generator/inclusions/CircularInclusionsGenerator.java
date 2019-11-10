package com.rszyszka.msm.model.generator.inclusions;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CircularInclusionsGenerator extends InclusionsGenerator {


    protected CircularInclusionsGenerator(int number, int size, Space space, List<Coords> availableCellCords) {
        super(number, size, space, availableCellCords);
    }


    public CircularInclusionsGenerator(Space space, List<Coords> availableCellCords, int size) {
        super(0, size, space, availableCellCords);
    }


    public void generateGrainBoundaries() {
        availableCellCords.forEach(this::placeInclusions);
    }


    @Override
    protected Set<Coords> determineCoordsSet(Coords randomizedCoords) {
        Set<Coords> coordsSet = new HashSet<>();

        if (size == 1) {
            coordsSet.add(new Coords(randomizedCoords.getX(), randomizedCoords.getY()));
            return coordsSet;
        }

        int modifier = size - 1;

        for (int y = randomizedCoords.getY() - modifier; y <= randomizedCoords.getY() + modifier; y++) {
            for (int x = randomizedCoords.getX() - modifier; x <= randomizedCoords.getX() + modifier; x++) {
                Coords coords = new Coords(x, y);
                double distance = computeDistance(coords, randomizedCoords);
                if (distance < modifier) {
                    space.getBoundaryCondition().correctCoordsIfNeeded(coords);
                    coordsSet.add(coords);
                }
            }
        }

        return coordsSet;
    }


    private double computeDistance(Coords coords1, Coords coords2) {
        return Math.sqrt(Math.pow(coords1.getX() - coords2.getX(), 2) + Math.pow(coords1.getY() - coords2.getY(), 2));
    }

}