package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.GraphicsUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;

public class UniversePanel extends JPanel{
	
	private Universe universe;
	private int zoom;
    private boolean isFullScreen;
	
	public UniversePanel(Universe universe, int zoom, boolean isFullScreen){
		this.universe = universe;
		this.zoom = zoom;
        this.isFullScreen = isFullScreen;
        updateSize();

		addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e){
				if (e.isPopupTrigger())
					doPop(e);
			}

			public void mouseReleased(MouseEvent e){
				if (e.isPopupTrigger())
					doPop(e);
			}

			private void doPop(MouseEvent e){
				UniversePopupMenu menu = new UniversePopupMenu(UniversePanel.this);
				menu.show(e.getComponent(), e.getX(), e.getY());
			}
		});
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

    public boolean isFullScreen(){
        return isFullScreen;
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
