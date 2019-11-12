package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;


public class GrainGrowthTest extends TestCase {

    GrainGrowth grainGrowth;

    public void setUp() {
        Space space = new Space(10, 10);
        grainGrowth = new GrainGrowth(space);
    }


    public void testSimulateGrainGrowth() {
        assertEquals(0, countFilledCells());
        grainGrowth.simulateGrainGrowth();
        assertEquals(0, countFilledCells());

        grainGrowth.getSpace().getCells()[1][1].setId(1);
        assertEquals(1, countFilledCells());
        grainGrowth.simulateGrainGrowth();
        assertEquals(100, countFilledCells());

        Space space = new Space(10, 10);
        space.getCells()[1][1].setId(1);
        space.getCells()[1][1].setGrowable(false);
        grainGrowth = new GrainGrowth(space);
        assertEquals(1, countFilledCells());
        grainGrowth.simulateGrainGrowth();
        assertEquals(1, countFilledCells());
    }


    private int countFilledCells() {
        int counter = 0;
        for (int i = 0; i < grainGrowth.getSpace().getSizeY(); i++) {
            for (int j = 0; j < grainGrowth.getSpace().getSizeX(); j++) {
                if (grainGrowth.getSpace().getCells()[i][j].getId() != 0) {
                    counter++;
                }
            }
        }
        return counter;
    }


    public void testSetNewIdIfDifferentThanZero() {
        assertFalse(grainGrowth.setNewIdIfDifferentThanZero(new Coords(0, 0), 0));
        assertTrue(grainGrowth.setNewIdIfDifferentThanZero(new Coords(0, 0), 1));
    }


    public void testPerformGrowthIfPossible() {
        grainGrowth.getSpace().getCells()[1][1].setId(1);

        grainGrowth.performGrowthIfPossible(new Coords(1, 1));
        assertFalse(grainGrowth.changed);

        grainGrowth.performGrowthIfPossible(new Coords(0, 0));
        assertTrue(grainGrowth.changed);
    }
}