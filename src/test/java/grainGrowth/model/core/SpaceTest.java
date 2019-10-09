package grainGrowth.model.core;

import junit.framework.TestCase;

public class SpaceTest extends TestCase {

    private Space space;
    private int sizeX = 300;
    private int sizeY = 300;


    @Override
    protected void setUp() throws Exception {
        space = new Space(sizeX, sizeY);
    }


    public void testCopyConstructor() {
        space.getCells()[0][0].setId(1);
        Space newSpace = new Space(space);
        assertEquals(sizeX, newSpace.getSizeX());
        space.getCells()[0][0].setId(0);
        assertEquals(1, newSpace.getCells()[0][0].getId());
    }


    public void testGetSizeX() {
        assertEquals(sizeX, space.getSizeX());
    }


    public void testGetSizeY() {
        assertEquals(sizeY, space.getSizeY());
    }


    @Override
    protected void tearDown() throws Exception {
        space = null;
    }
}