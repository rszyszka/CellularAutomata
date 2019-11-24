package com.rszyszka.msm.model.simulation;

import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;

import static com.rszyszka.msm.model.core.Coords.coords;


public class ShapeControlGrainGrowthTest extends TestCase {

    ShapeControlGrainGrowth grainGrowth;
    Coords coords = coords(1, 1);

    public void testFirstRule() {
        Space space = new Space(3, 3);
        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(0, 1)).setId(1);
        space.getCell(coords(0, 2)).setId(1);
        space.getCell(coords(1, 0)).setId(1);
        space.getCell(coords(1, 2)).setId(1);
        space.getCell(coords(2, 0)).setId(2);
        space.getCell(coords(2, 1)).setId(2);
        space.getCell(coords(2, 2)).setId(2);

        grainGrowth = new ShapeControlGrainGrowth(space, 0.1);
        grainGrowth.simulateGrainGrowth();
        assertEquals(1, grainGrowth.space.getCell(coords).getId());
    }

    public void testSecondRule() {
        Space space = new Space(3, 3);

        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(0, 1)).setId(2);
        space.getCell(coords(0, 2)).setId(3);
        space.getCell(coords(1, 0)).setId(2);
        space.getCell(coords(1, 2)).setId(2);
        space.getCell(coords(2, 0)).setId(1);
        space.getCell(coords(2, 1)).setId(1);
        space.getCell(coords(2, 2)).setId(1);

        grainGrowth = new ShapeControlGrainGrowth(space, 0.1);
        grainGrowth.simulateGrainGrowth();
        assertEquals(2, grainGrowth.space.getCell(coords).getId());
    }

    public void testThirdRule() {
        Space space = new Space(3, 3);

        space.getCell(coords(0, 0)).setId(1);
        space.getCell(coords(0, 1)).setId(2);
        space.getCell(coords(0, 2)).setId(1);
        space.getCell(coords(1, 0)).setId(3);
        space.getCell(coords(1, 2)).setId(3);
        space.getCell(coords(2, 0)).setId(1);
        space.getCell(coords(2, 1)).setId(2);
        space.getCell(coords(2, 2)).setId(3);

        grainGrowth = new ShapeControlGrainGrowth(space, 0.1);
        grainGrowth.simulateGrainGrowth();
        assertEquals(1, grainGrowth.space.getCell(coords).getId());
    }


    public void testFourthRule() {
        Space space = new Space(3, 3);

        space.getCell(coords(0, 0)).setId(3);
        space.getCell(coords(0, 1)).setId(2);
        space.getCell(coords(0, 2)).setId(3);
        space.getCell(coords(1, 0)).setId(4);
        space.getCell(coords(1, 2)).setId(1);
        space.getCell(coords(2, 0)).setId(2);
        space.getCell(coords(2, 1)).setId(1);
        space.getCell(coords(2, 2)).setId(1);

        grainGrowth = new ShapeControlGrainGrowth(space, 0);
        grainGrowth.performGrowthIfPossible(coords);
        assertTrue(grainGrowth.changed);

        grainGrowth = new ShapeControlGrainGrowth(space, 1);
        grainGrowth.simulateGrainGrowth();
        assertEquals(1, grainGrowth.space.getCell(coords).getId());
    }


    public void testSimulateGrowth() {
        Space space = new Space(10, 10);

        space.getCell(coords(4, 4)).setId(1);
        space.getCell(coords(1, 2)).setId(2);
        space.getCell(coords(7, 6)).setId(-1);
        space.getCell(coords(7, 7)).setId(-1);
        space.getCell(coords(8, 6)).setId(-1);
        space.getCell(coords(8, 7)).setId(-1);
        space.getCell(coords(0, 9)).setId(4);

        grainGrowth = new ShapeControlGrainGrowth(space, 0.1);
        grainGrowth.simulateGrainGrowth();
        assertEquals(100, countFilledCells());
    }


    private int countFilledCells() {
        return (int) grainGrowth.getSpace().getCellsByCoords().values().stream()
                .filter(cell -> cell.getId() != 0)
                .count();
    }

}