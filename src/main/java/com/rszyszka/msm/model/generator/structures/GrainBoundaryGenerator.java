package com.rszyszka.msm.model.generator.structures;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import com.rszyszka.msm.model.generator.inclusions.InclusionsGenerator;

import java.util.*;
import java.util.stream.Collectors;


public class GrainBoundaryGenerator extends StructureGenerator {

    private LinkedList<Integer> idsToInclusionsConversion;
    private List<Coords> coordsToLock;
    private Space space;
    private int boundarySize;


    public GrainBoundaryGenerator(Collection<Grain> grains, Space space, int boundarySize) {
        idsToInclusionsConversion = new LinkedList<>();
        coordsToLock = new LinkedList<>();
        this.space = space;
        this.boundarySize = boundarySize;
        this.grains = grains.stream()
                .filter(grain -> grain.getStructureType() == StructureType.GRAIN_BOUNDARY)
                .collect(Collectors.toCollection(LinkedList::new));
    }


    public void generateAllBoundaries() {
        determineAllCellsCoordsToLock();
        setGrainBoundariesIds();
        blockCellsFromGrowing();
    }


    private void determineAllCellsCoordsToLock() {
        coordsToLock = grains.stream()
                .map(Grain::getCoords)
                .flatMap(Collection::stream)
                .filter(coords -> space.getCell(coords).isGrainBoundary())
                .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    public void generate() {
        determineSelectedCellsCoordsToLock();
        setGrainBoundariesIds();
        blockCellsFromGrowing();
    }


    protected void determineSelectedCellsCoordsToLock() {
        Set<Coords> boundariesCoords = grains.stream()
                .map(Grain::getCoords)
                .flatMap(Collection::stream)
                .filter(coords -> space.getCell(coords).isGrainBoundary())
                .collect(Collectors.toCollection(HashSet::new));

        Set<Coords> additionalBoundariesCoords = new HashSet<>(boundariesCoords);
        boundariesCoords.forEach(coords -> {
            List<Coords> neighboursCoords = space.getMooreNeighbourHood().findNeighboursCoords(coords);
            neighboursCoords.forEach(neighbourCoords -> {
                Cell cell = space.getCell(neighbourCoords);
                if (cell.isGrainBoundary()) {
                    additionalBoundariesCoords.add(neighbourCoords);
                }
            });
        });

        coordsToLock.addAll(additionalBoundariesCoords);
    }


    private void setGrainBoundariesIds() {
        grains.forEach(grain -> {
            if (grain.getId() != -1) {
                idsToInclusionsConversion.add(grain.getId());
                grain.setId(-1);
            }
        });
    }


    @Override
    protected void blockCellsFromGrowing() {
        InclusionsGenerator inclusionsGenerator = InclusionsGenerator.createInstance(0, boundarySize, space);
        coordsToLock.forEach(inclusionsGenerator::placeInclusions);
    }


    public int computeGbPercentage() {
        int counter = 0;
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                if (space.getCells()[i][j].getId() == -1) {
                    counter++;
                }
            }
        }
        return counter * 100 / (space.getSizeX() * space.getSizeY());
    }


    public LinkedList<Integer> getIdsToInclusionsConversion() {
        return idsToInclusionsConversion;
    }

}
