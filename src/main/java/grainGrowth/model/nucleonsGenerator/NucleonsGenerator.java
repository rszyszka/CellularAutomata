package grainGrowth.model.nucleonsGenerator;

import grainGrowth.model.core.Cell;
import grainGrowth.model.core.Coords;
import grainGrowth.model.core.Space;
import grainGrowth.model.nucleonsGenerator.inclusions.InclusionsGenerator;

import java.util.*;


public class NucleonsGenerator {

    public static void putInclusionsRandomly(int number, int size, Space space) {
        List<Coords> availableCellCords = determineFreeCellCords(space);

        if (availableCellCords.isEmpty()) {
            availableCellCords = determineGrainBoundaryCells(space);
        }

        InclusionsGenerator generator = InclusionsGenerator.createInclusionsGenerator(number, size, space, availableCellCords);
        if (generator != null) {
            generator.putInclusionsInAvailableCells();
        }
    }


    public static void putNucleonsRandomly(int number, Space space) {
        List<Coords> freeCellCoords = determineFreeCellCords(space);

        int freeCellCoordsSize = freeCellCoords.size();
        int firstIdToPut = space.findMaxCellId() + 1;

        Random random = new Random();

        for (int i = 0; i < number && freeCellCoordsSize != 0; i++, freeCellCoordsSize--) {
            Coords randomizedCoords = freeCellCoords.remove(random.nextInt(freeCellCoordsSize));
            space.getCell(randomizedCoords).setId(firstIdToPut);

            firstIdToPut++;
        }
    }


    private static List<Coords> determineFreeCellCords(Space space) {
        List<Coords> freeCellCoords = new ArrayList<>();
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                if (space.getCells()[i][j].getId() == 0) {
                    freeCellCoords.add(new Coords(j, i));
                }
            }
        }
        return freeCellCoords;
    }


    public static List<Coords> determineGrainBoundaryCells(Space space) {
        Set<Coords> grainBoundaryCells = new HashSet<>();
        Coords coords = new Coords(0, 0);
        for (int i = 0; i < space.getSizeY(); i++) {
            coords.setY(i);
            for (int j = 0; j < space.getSizeX(); j++) {
                coords.setX(j);
                Cell cell = space.getCell(coords);
                if (cell.isGrainBoundary()) {
                    grainBoundaryCells.add(coords);
                }
            }
        }
        return new ArrayList<>(grainBoundaryCells);
    }

}
