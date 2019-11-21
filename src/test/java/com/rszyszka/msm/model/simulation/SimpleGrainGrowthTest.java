package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;


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

        simpleGrainGrowth.getSpace().getCells()[1][1].setId(1);
        assertEquals(1, countFilledCells());
        simpleGrainGrowth.simulateGrainGrowth();
        assertEquals(100, countFilledCells());

        Space space = new Space(10, 10);
        space.getCells()[1][1].setId(1);
        space.getCells()[1][1].setGrowable(false);
        simpleGrainGrowth = new SimpleGrainGrowth(space);
        assertEquals(1, countFilledCells());
        simpleGrainGrowth.simulateGrainGrowth();
        assertEquals(1, countFilledCells());
    }


    private int countFilledCells() {
        int counter = 0;
        for (int i = 0; i < simpleGrainGrowth.getSpace().getSizeY(); i++) {
            for (int j = 0; j < simpleGrainGrowth.getSpace().getSizeX(); j++) {
                if (simpleGrainGrowth.getSpace().getCells()[i][j].getId() != 0) {
                    counter++;
                }
            }
        }
        return counter;
    }


    public void testSetNewIdIfDifferentThanZero() {
        assertFalse(simpleGrainGrowth.setNewIdIfDifferentThanZero(Coords.coords(0, 0), 0));
        assertTrue(simpleGrainGrowth.setNewIdIfDifferentThanZero(Coords.coords(0, 0), 1));
    }


    public void testPerformGrowthIfPossible() {
        simpleGrainGrowth.getSpace().getCells()[1][1].setId(1);

        simpleGrainGrowth.performGrowthIfPossible(Coords.coords(1, 1));
        assertFalse(simpleGrainGrowth.changed);

        simpleGrainGrowth.performGrowthIfPossible(Coords.coords(0, 0));
        assertTrue(simpleGrainGrowth.changed);
    }
}