package com.rszyszka.msm.model.utils;

import com.rszyszka.msm.model.core.Cell;
import com.rszyszka.msm.model.core.Coords;
import com.rszyszka.msm.model.core.Space;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class InputOutputUtils {

    private static final int EMPTY = -1;


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


    public static Space loadSpace(File file) throws IOException {
        if ("txt".equals(FilenameUtils.getExtension(file.getName()))) {
            return prepareSpaceBasedOnTxtFile(file);
        } else {
            BufferedImage image = ImageIO.read(file);
            return prepareSpaceBasedOnImage(image);
        }
    }


    private static Space prepareSpaceBasedOnTxtFile(File file) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        String line = bufferedReader.readLine();
        String[] numbers = line.split(";");
        Space newSpace = new Space(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));

        Coords coords;
        while ((line = bufferedReader.readLine()) != null) {
            numbers = line.split(";");
            coords = new Coords(Integer.parseInt(numbers[0]), Integer.parseInt(numbers[1]));
            Cell cell = newSpace.getCell(coords);
            cell.setId(Integer.parseInt(numbers[2]));
            if (cell.getId() < 0) {
                cell.setGrowable(false);
            }
        }
        bufferedReader.close();
        return newSpace;
    }


    private static Space prepareSpaceBasedOnImage(BufferedImage image) {
        Map<Integer, Integer> idByRGB = new HashMap<>();
        idByRGB.put(EMPTY, 0);
        idByRGB.put(Color.BLACK.getRGB(), -1);
        idByRGB.put(Color.MAGENTA.getRGB(), -2);
        int sizeX = image.getWidth() / 2;
        int sizeY = image.getHeight() / 2;
        Space newSpace = new Space(sizeX, sizeY);
        int idCounter = 1;
        for (int i = 0; i < sizeY; i++) {
            for (int j = 0; j < sizeX; j++) {
                int rgb = image.getRGB(j * 2, i * 2);
                if (!idByRGB.containsKey(rgb)) {
                    idByRGB.put(rgb, idCounter);
                    idCounter++;
                }
                Cell cell = newSpace.getCells()[i][j];
                cell.setId(idByRGB.get(rgb));
                if (cell.getId() < 0) {
                    cell.setGrowable(false);
                }
            }
        }
        return newSpace;
    }

}
