package grainGrowth.model.core;

import java.util.Random;


public class NucleonsGenerator {

    public static void putNucleonsRandomly(int number, Space space) {
        Random random = new Random();
        int firstIdToPut = space.getMaxCellId() + 1;
        int x, y;
        for (int i = firstIdToPut; i < number + firstIdToPut; i++) {
            x = random.nextInt(space.getSizeX());
            y = random.nextInt(space.getSizeY());
            space.getCells()[y][x].setId(i);
            space.setMaxCellId(i);
        }
    }

}
