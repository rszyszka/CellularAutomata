package com.rszyszka.msm.model.core;

import junit.framework.TestCase;

import static com.rszyszka.msm.model.core.Coords.coords;

public class SpaceTest extends TestCase {

    Space space;


    @Override
    protected void setUp() {
        space = new Space(4, 4);

        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(0, 1)).setId(1);
        space.getCell(coords(0, 2)).setId(1);
        space.getCell(coords(0, 3)).setId(2);
        space.getCell(coords(1, 0)).setId(1);
        space.getCell(coords(1, 1)).setId(1);
        space.getCell(coords(1, 2)).setId(2);
        space.getCell(coords(1, 3)).setId(2);
        space.getCell(coords(2, 0)).setId(1);
        space.getCell(coords(2, 1)).setId(2);
        space.getCell(coords(2, 2)).setId(2);
        space.getCell(coords(2, 3)).setId(2);
        space.getCell(coords(3, 0)).setId(2);
        space.getCell(coords(3, 1)).setId(2);
        space.getCell(coords(3, 2)).setId(2);
        space.getCell(coords(3, 3)).setId(2);
    }

    public void testDetermineBorderCells() {
        space.determineBorderCells();
        assertTrue(space.getCell(coords(0, 2)).isGrainBoundary());
        assertTrue(space.getCell(coords(0, 3)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 1)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 2)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 1)).isGrainBoundary());
        assertTrue(space.getCell(coords(3, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(3, 1)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 2)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 3)).isGrainBoundary());
        assertTrue(space.getCell(coords(0, 1)).isGrainBoundary());

        assertFalse(space.getCell(coords(3, 2)).isGrainBoundary());
        assertFalse(space.getCell(coords(3, 3)).isGrainBoundary());
        assertFalse(space.getCell(coords(2, 3)).isGrainBoundary());
        assertFalse(space.getCell(coords(0, 0)).isGrainBoundary());
    }

    public void testFindMaxCellId() {
        assertEquals(2, space.findMaxCellId());
    }
}