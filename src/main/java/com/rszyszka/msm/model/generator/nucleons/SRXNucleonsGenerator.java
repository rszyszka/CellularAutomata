package com.rszyszka.msm.model.generator.nucleons;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import com.rszyszka.msm.model.generator.GeneratorUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class SRXNucleonsGenerator {

    public static void putNucleonsAnywhere(int number, Space space) {
        putNucleonsInAvailablePlace(number, space, new ArrayList<>(space.getCellsByCoords().keySet()));
    }


    public static void putNucleonsOnGrainBoundaries(int number, Space space) {
        putNucleonsInAvailablePlace(number, space, GeneratorUtils.determineGrainBoundaryCellsCoords(space));
    }


    private static void putNucleonsInAvailablePlace(int number, Space space, List<Coords> availableCellsCoords) {
        int firstIdToPut = space.findMaxCellId() + 1;
        int availableCellsCoordsSize = availableCellsCoords.size();
        Random random = new Random();
        for (int i = 0; i < number && availableCellsCoordsSize != 0; i++, availableCellsCoordsSize--) {
            Cell cell = space.getCell(availableCellsCoords.remove(random.nextInt(availableCellsCoordsSize)));
            cell.setId(firstIdToPut);
            cell.setRecrystallized(true);

            firstIdToPut++;
        }
    }

}
