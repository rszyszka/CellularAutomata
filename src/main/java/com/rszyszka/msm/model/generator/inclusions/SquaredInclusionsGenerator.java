package com.rszyszka.msm.model.generator.inclusions;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class SquaredInclusionsGenerator extends InclusionsGenerator {


    protected SquaredInclusionsGenerator(int number, int size, Space space, List<Coords> availableCellCords) {
        super(number, size, space, availableCellCords);
    }


    @Override
    protected Set<Coords> determineCoordsSet(Coords randomizedCoords) {
        Set<Coords> coordsSet = new HashSet<>();

        if (size == 1) {
            coordsSet.add(new Coords(randomizedCoords.getX(), randomizedCoords.getY()));
            return coordsSet;
        }

        int modifier = size - 1;

        for (int y = randomizedCoords.getY() - modifier; y < randomizedCoords.getY() + modifier; y++) {
            for (int x = randomizedCoords.getX() - modifier; x < randomizedCoords.getX() + modifier; x++) {
                Coords coords = new Coords(x, y);
                space.getBoundaryCondition().correctCoordsIfNeeded(coords);
                coordsSet.add(coords);
            }
        }

        return coordsSet;
    }

}
