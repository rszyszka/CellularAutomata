package grainGrowth.model.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;


public class InputOutputUtils {

    public static void saveSpace(Space space, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.println(space.getSizeX() + ";" + space.getSizeY());
        for (int i = 0; i < space.getSizeY(); i++) {
            for (int j = 0; j < space.getSizeX(); j++) {
                writer.println(j + ";" + i + ";" + space.getCells()[i][j].getId());
            }
        }
        writer.close();
    }

}
