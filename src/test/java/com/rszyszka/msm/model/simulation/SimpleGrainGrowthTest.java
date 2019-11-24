package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;

import static com.rszyszka.msm.model.core.Coords.coords;


public class SimpleGrainGrowthTest extends TestCase {

    SimpleGrainGrowth simpleGrainGrowth;

    public void setUp() {
        Space space = new Space(10, 10);
        simpleGrainGrowth = new SimpleGrainGrowth(space);
    }


    public void testSimulateGrainGrowth() {
        assertEquals(0, countFilledCells());
        simpleGrainGrowth.simulateGrainGrowth();
        assertEquals(0, countFilledCells());

        simpleGrainGrowth.getSpace().getCell(coords(1, 1)).setId(1);
        assertEquals(1, countFilledCells());
        simpleGrainGrowth.simulateGrainGrowth();
        assertEquals(100, countFilledCells());

        Space space = new Space(10, 10);
        space.getCell(coords(1, 1)).setId(1);
        space.getCell(coords(1, 1)).setGrowable(false);
        simpleGrainGrowth = new SimpleGrainGrowth(space);
        assertEquals(1, countFilledCells());
        simpleGrainGrowth.simulateGrainGrowth();
        assertEquals(1, countFilledCells());
    }


    private int countFilledCells() {
        return (int) simpleGrainGrowth.getSpace().getCellsByCoords().values().stream()
                .filter(cell -> cell.getId() != 0)
                .count();
    }


    public void testSetNewIdIfDifferentThanZero() {
        assertFalse(simpleGrainGrowth.setNewIdIfDifferentThanZero(coords(0, 0), 0));
        assertTrue(simpleGrainGrowth.setNewIdIfDifferentThanZero(coords(0, 0), 1));
    }


    public void testPerformGrowthIfPossible() {
        simpleGrainGrowth.getSpace().getCell(coords(1, 1)).setId(1);

        simpleGrainGrowth.performGrowthIfPossible(coords(1, 1));
        assertFalse(simpleGrainGrowth.changed);

        simpleGrainGrowth.performGrowthIfPossible(coords(0, 0));
        assertTrue(simpleGrainGrowth.changed);
    }
}