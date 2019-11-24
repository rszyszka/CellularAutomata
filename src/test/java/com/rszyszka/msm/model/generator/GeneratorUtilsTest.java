package com.rszyszka.msm.model.generator;

import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;

import java.util.Arrays;

import static com.rszyszka.msm.model.core.Coords.coords;

public class GeneratorUtilsTest extends TestCase {

    public void testDetermineFreeCellCords() {
        Space space = new Space(3, 3);
        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(1, 1)).setId(2);
        space.getCell(coords(2, 2)).setId(3);

        assertEquals(Arrays.asList(
                coords(0, 1),
                coords(1, 0),
                coords(2, 0),
                coords(0, 2),
                coords(2, 1),
                coords(1, 2)
        ), GeneratorUtils.determineFreeCellCords(space));
    }

    public void testDetermineGrainBoundaryCellsCoords() {
        Space space = new Space(3, 3);
        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(0, 1)).setId(1);
        space.getCell(coords(0, 2)).setId(2);
        space.getCell(coords(1, 0)).setId(1);
        space.getCell(coords(1, 1)).setId(2);
        space.getCell(coords(1, 2)).setId(2);
        space.getCell(coords(2, 0)).setId(2);
        space.getCell(coords(2, 1)).setId(2);
        space.getCell(coords(2, 2)).setId(2);

        space.determineBorderCells();

        assertTrue(space.getCell(coords(0, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 1)).isGrainBoundary());
        assertTrue(space.getCell(coords(1, 2)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 0)).isGrainBoundary());
        assertTrue(space.getCell(coords(2, 1)).isGrainBoundary());

        assertFalse(space.getCell(coords(2, 2)).isGrainBoundary());
    }
}