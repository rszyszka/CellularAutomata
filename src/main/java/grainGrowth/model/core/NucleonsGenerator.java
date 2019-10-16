package grainGrowth.model.core;

import java.util.*;


public class NucleonsGenerator {

    public static void putInclusionsRandomly(int number, Space space) {
        List<Coords> availableCellCords = determineFreeCellCords(space);

        if (availableCellCords.isEmpty()) {
            availableCellCords = determineGrainBoundaryCells(space);
        }

        putInclusionsInAvailableCells(number, space, availableCellCords);
    }


    public static void putNucleonsRandomly(int number, Space space) {
        List<Coords> freeCellCoords = determineFreeCellCords(space);

        int freeCellCoordsSize = freeCellCoords.size();
        int firstIdToPut = space.determineMaxCellId() + 1;

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


    private static List<Coords> determineGrainBoundaryCells(Space space) {
        Set<Coords> grainBoundaryCells = new HashSet<>();
        Coords coords = new Coords(0, 0);
        for (int i = 0; i < space.getSizeY(); i++) {
            coords.setY(i);
            for (int j = 0; j < space.getSizeX(); j++) {
                coords.setX(j);
                List<Coords> neighbours = space.getMooreNeighbourHood().findNeighboursCoords(coords);
                for (Coords neighbourCoords : neighbours) {
                    Cell cell = space.getCell(coords);
                    if (cell.getId() != space.getCell(neighbourCoords).getId()) {
                        grainBoundaryCells.add(neighbourCoords);
                        grainBoundaryCells.add(coords);
                    }
                }
            }
        }
        return new ArrayList<>(grainBoundaryCells);
    }


    private static void putInclusionsInAvailableCells(int number, Space space, List<Coords> availableCellCords) {
        int freeCellCoordsSize = availableCellCords.size();
        Random random = new Random();

        for (int i = 0; i < number && freeCellCoordsSize != 0; i++, freeCellCoordsSize--) {
            Coords randomizedCoords = availableCellCords.remove(random.nextInt(freeCellCoordsSize));
            space.getCell(randomizedCoords).setId(-1);
        }
    }

}
