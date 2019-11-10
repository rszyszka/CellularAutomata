package com.rszyszka.msm.model.core;

import junit.framework.TestCase;

public class CellTest extends TestCase {

    private Cell cell;

    public void setUp() {
        cell = new Cell();
    }

    public void testGetId() {
        assertEquals(0, cell.getId());
    }

    public void testSetId() {
        cell.setId(1);
        assertEquals(1, cell.getId());
    }

    public void tearDown() {
        cell = null;
    }

}