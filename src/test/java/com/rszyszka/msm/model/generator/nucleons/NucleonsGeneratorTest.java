package com.rszyszka.msm.model.generator.nucleons;

import com.rszyszka.msm.model.core.Space;
import junit.framework.TestCase;


public class NucleonsGeneratorTest extends TestCase {

    private Space space;


    @Override
    protected void setUp() {
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
        return (int) space.getCellsByCoords().values().stream()
                .filter(cell -> cell.getId() != 0)
                .count();
    }

    @Override
    protected void tearDown() {
        space = null;
    }

}