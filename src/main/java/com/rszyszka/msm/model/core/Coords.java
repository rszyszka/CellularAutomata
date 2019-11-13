package com.rszyszka.msm.model.core;

public class Coords {
    private int x;
    private int y;

    private Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Coords coords(int x, int y) {
        return new Coords(x, y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        Coords coords = (Coords) obj;
        return this.x == coords.x && this.y == coords.y;
    }

    @Override
    public int hashCode() {
        return (int) Math.sqrt(Math.pow(x, 2) - Math.pow(y, 2));
    }

}
