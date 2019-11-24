package com.rszyszka.msm.model.generator;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class GeneratorUtils {

    public static List<Coords> determineFreeCellCords(Space space) {
        return space.getCellsByCoords().entrySet().stream()
                .filter(coordsCellEntry -> coordsCellEntry.getValue().getId() == 0)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    public static List<Coords> determineGrainBoundaryCellsCoords(Space space) {
        return space.getCellsByCoords().entrySet().stream()
                .filter(coordsCellEntry -> coordsCellEntry.getValue().isGrainBoundary())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
