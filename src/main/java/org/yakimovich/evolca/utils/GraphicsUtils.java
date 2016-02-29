package org.yakimovich.evolca.utils;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.ui.Colors;
import shine.htetaung.giffer.Giffer;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Iterator;

/**
 * Helpful functions to work with graphics.
 */
public class GraphicsUtils {
    public static BufferedImage createImage(Universe universe, int zoom){
        BufferedImage img = new BufferedImage(universe.getWidth() * zoom,
                universe.getHeight() * zoom, BufferedImage.TYPE_INT_RGB);

        WritableRaster wr = img.getRaster();
        int[] pArray = new int[universe.getWidth() * universe.getHeight() * 3 * zoom * zoom];
        for(int i = 0; i < universe.getHeight(); i++){
            for(int j = 0; j < universe.getWidth(); j++){
                Color color = Colors.getColor(universe.getValue(i, j));
                for(int n = 0; n < zoom; n++){
                    for(int m = 0; m < zoom; m++){
                        pArray[(universe.getWidth() * zoom * (i * zoom + n) + (j * zoom + m)) * 3] = color.getRed();
                        pArray[(universe.getWidth() * zoom * (i * zoom + n) + (j * zoom + m)) * 3 + 1] = color.getGreen();
                        pArray[(universe.getWidth() * zoom * (i * zoom + n) + (j * zoom + m)) * 3 + 2] = color.getBlue();
                    }
                }
            }
        }
        wr.setPixels(0, 0, universe.getWidth() * zoom,
                universe.getHeight() * zoom, pArray);
        return img;
    }

    public static void exportToGif(Universe u, int ageFrom, int ageTo, String fileName, int zoom, int delay, boolean loop)
            throws IIOException, IOException{
        BufferedImage[] images = new BufferedImage[1 + ageTo - ageFrom];
        u.resetToInitialState();
        u.tick(ageFrom - 1);
        for(int i = 0; i < images.length; i++){
            images[i] = createImage(u, zoom);
            u.tick();
        }
        Giffer.generateFromBI(images, fileName, delay, loop);
    }
}
