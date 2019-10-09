package grainGrowth.model.core;

import junit.framework.TestCase;

public class CellTest extends TestCase {

    private Cell cell;

    public void setUp() throws Exception {
        cell = new Cell();
    }

    public void testGetId() {
        assertEquals(0, cell.getId());
    }

    public void testSetId() {
        cell.setId(1);
        assertEquals(1, cell.getId());
    }

    public void tearDown() throws Exception {
        cell = null;
    }

}