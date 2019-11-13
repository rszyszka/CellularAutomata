package com.rszyszka.msm.model.generator;


import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.ArrayList;
import java.util.List;


public class GeneratorUtils {

    public static List<Coords> determineFreeCellCords(Space space) {
        List<Coords> freeCellCoords = new ArrayList<>();
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                if (space.getCells()[i][j].getId() == 0) {
                    freeCellCoords.add(Coords.coords(j, i));
                }
            }
        }
        return freeCellCoords;
    }


    public static List<Coords> determineGrainBoundaryCellsCoords(Space space) {
        List<Coords> grainBoundaryCellsCoords = new ArrayList<>();
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                Cell cell = space.getCells()[i][j];
                if (cell.isGrainBoundary()) {
                    grainBoundaryCellsCoords.add(Coords.coords(j, i));
                }
            }
        }
        return grainBoundaryCellsCoords;
    }

}
