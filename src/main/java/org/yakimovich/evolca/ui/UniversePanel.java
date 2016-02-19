package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.GraphicsUtils;

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
        updateSize();
	}

	private void updateSize(){
        Dimension size = new Dimension(universe.getWidth() * zoom,
                universe.getHeight() * zoom);
        setMinimumSize(size);
        setSize(size);
        setMaximumSize(size);
    }
	
	public Universe getUniverse() {
		return universe;
	}

	public void setUniverse(Universe universe) {
        this.universe = universe;
        updateSize();
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
        this.zoom = zoom;
        updateSize();
	}

	public void paint(Graphics g) {
		super.paint(g);
		Image img = GraphicsUtils.createImage(universe, zoom);
		g.drawImage(img, 0, 0, universe.getWidth() * zoom,
                universe.getHeight() * zoom,  this);
	}
	

}
