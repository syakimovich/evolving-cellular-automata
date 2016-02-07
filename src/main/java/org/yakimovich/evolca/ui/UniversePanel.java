package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class UniversePanel extends JPanel{
	
	private Universe universe;
	private int zoom;
	
	public UniversePanel(Universe universe, int zoom){
		this.universe = universe;
		this.zoom = zoom;
		this.setSize(universe.getWidth() * zoom, 
				universe.getHeight() * zoom);
	}
	
	public Universe getUniverse() {
		return universe;
	}

	public void setUniverse(Universe universe) {
		this.universe = universe;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public void paint(Graphics g) {
		super.paint(g);
		Image img = createImage();
		g.drawImage(img, 0, 0, this);
	}
	
	private Image createImage(){
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
}
