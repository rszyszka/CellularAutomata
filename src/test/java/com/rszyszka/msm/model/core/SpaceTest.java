package com.rszyszka.msm.model.core;

import junit.framework.TestCase;

public class SpaceTest extends TestCase {

    Space space;


    @Override
    protected void setUp() {
        space = new Space(4, 4);
        space.getCells()[0][0].setId(1);
        space.getCells()[0][1].setId(1);
        space.getCells()[0][2].setId(1);
        space.getCells()[0][3].setId(2);
        space.getCells()[1][0].setId(1);
        space.getCells()[1][1].setId(1);
        space.getCells()[1][2].setId(2);
        space.getCells()[1][3].setId(2);
        space.getCells()[2][0].setId(1);
        space.getCells()[2][1].setId(2);
        space.getCells()[2][2].setId(2);
        space.getCells()[2][3].setId(2);
        space.getCells()[3][0].setId(2);
        space.getCells()[3][1].setId(2);
        space.getCells()[3][2].setId(2);
        space.getCells()[3][3].setId(2);
    }

    public void testDetermineBorderCells() {
        space.determineBorderCells();
        assertTrue(space.getCells()[0][2].isGrainBoundary());
        assertTrue(space.getCells()[0][3].isGrainBoundary());
        assertTrue(space.getCells()[1][1].isGrainBoundary());
        assertTrue(space.getCells()[1][2].isGrainBoundary());
        assertTrue(space.getCells()[1][2].isGrainBoundary());
        assertTrue(space.getCells()[2][0].isGrainBoundary());
        assertTrue(space.getCells()[2][1].isGrainBoundary());
        assertTrue(space.getCells()[3][0].isGrainBoundary());
        assertTrue(space.getCells()[3][1].isGrainBoundary());
        assertTrue(space.getCells()[2][2].isGrainBoundary());
        assertTrue(space.getCells()[1][0].isGrainBoundary());
        assertTrue(space.getCells()[1][3].isGrainBoundary());
        assertTrue(space.getCells()[0][1].isGrainBoundary());

        assertFalse(space.getCells()[3][2].isGrainBoundary());
        assertFalse(space.getCells()[3][3].isGrainBoundary());
        assertFalse(space.getCells()[2][3].isGrainBoundary());
        assertFalse(space.getCells()[0][0].isGrainBoundary());

    }
}