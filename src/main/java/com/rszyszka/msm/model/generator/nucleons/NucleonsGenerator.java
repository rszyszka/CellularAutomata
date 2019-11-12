package com.rszyszka.msm.model.generator.nucleons;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import com.rszyszka.msm.model.generator.GeneratorUtils;

import java.util.List;
import java.util.Random;

public class NucleonsGenerator {

    public static void putNucleonsRandomly(int number, Space space) {
        List<Coords> freeCellCoords = GeneratorUtils.determineFreeCellCords(space);

        int freeCellCoordsSize = freeCellCoords.size();
        int firstIdToPut = space.findMaxCellId() + 1;

        Random random = new Random();

        for (int i = 0; i < number && freeCellCoordsSize != 0; i++, freeCellCoordsSize--) {
            Coords randomizedCoords = freeCellCoords.remove(random.nextInt(freeCellCoordsSize));
            space.getCell(randomizedCoords).setId(firstIdToPut);

            firstIdToPut++;
        }
    }

}
