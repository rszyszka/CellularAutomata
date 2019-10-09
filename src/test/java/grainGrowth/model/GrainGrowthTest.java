package grainGrowth.model;

import grainGrowth.model.core.AbsorbentBoundaryCondition;
import grainGrowth.model.core.MooreNeighbourhood;
import grainGrowth.model.core.Space;
import junit.framework.TestCase;

public class GrainGrowthTest extends TestCase {

    private GrainGrowth grainGrowth;
    private Space space;
    private int sizeX;
    private int sizeY;

    @Override
    protected void setUp() throws Exception {
        sizeX = 10;
        sizeY = 10;
        space = new Space(sizeX, sizeY);
        grainGrowth = new GrainGrowth(space);
    }


    public void testPerformStep() {
        AbsorbentBoundaryCondition boundaryCondition = new AbsorbentBoundaryCondition(sizeX, sizeY);
        MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(boundaryCondition);
        space.getCells()[5][5].setId(1);
        grainGrowth = new GrainGrowth(space);
        grainGrowth.getSpace().setMooreNeighbourHood(mooreNeighbourhood);

        assertEquals(1, determineCounter());

        grainGrowth.performIteration();

        assertEquals(9, determineCounter());
    }


    public void testPerformThreeSteps() {
        AbsorbentBoundaryCondition boundaryCondition = new AbsorbentBoundaryCondition(sizeX, sizeY);
        MooreNeighbourhood mooreNeighbourhood = new MooreNeighbourhood(boundaryCondition);
        space.getCells()[0][0].setId(1);
        space.getCells()[2][2].setId(1);
        grainGrowth = new GrainGrowth(space);
        grainGrowth.getSpace().setMooreNeighbourHood(mooreNeighbourhood);

        grainGrowth.performIteration();
        assertEquals(12, determineCounter());
        grainGrowth.performIteration();
        assertEquals(25, determineCounter());
        grainGrowth.performIteration();
        assertEquals(36, determineCounter());

    }


    private int determineCounter() {
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


    @Override
    protected void tearDown() throws Exception {
        grainGrowth = null;
    }

}