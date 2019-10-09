package grainGrowth.model.core;

import junit.framework.TestCase;


public class NucleonsGeneratorTest extends TestCase {

    private Space space;


    @Override
    protected void setUp() throws Exception {
        space = new Space(300, 300);
    }

    public void testPutNucleonsRandomly() {
        int counter = determineCounter();
        assertEquals(0, counter);

        NucleonsGenerator.putNucleonsRandomly(10, space);

        counter = determineCounter();
        assertEquals(10, counter);
    }

    private int determineCounter() {
        int counter = 0;
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                if (space.getCells()[i][j].getId() != 0) {
                    counter++;
                }
            }
        }
        return counter;
    }

    @Override
    protected void tearDown() throws Exception {
        space = null;
    }

}