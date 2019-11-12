package com.rszyszka.msm.model.core;

import junit.framework.TestCase;

import static org.junit.Assert.assertNotEquals;

public class CoordsTest extends TestCase {

    private Coords coords2;
    private Coords coords1;

    @Override
    public void setUp() {
        coords1 = new Coords(1, 1);
        coords2 = new Coords(1, 1);
    }

    public void testTestEquals() {
        assertEquals(coords1, coords2);
        coords2.setX(2);
        assertNotEquals(coords1, coords2);
        assertNotEquals(coords1, new Object());
        assertNotEquals(coords1, null);
    }

    public void testTestHashCode() {
        assertEquals(coords1.hashCode(), coords2.hashCode());
        coords2.setX(2);
        assertNotEquals(coords1.hashCode(), coords2.hashCode());
    }
}