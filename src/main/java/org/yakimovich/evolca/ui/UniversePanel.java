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
				UniversePopupMenu menu = new UniversePopupMenu();
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

	private class UniversePopupMenu extends JPopupMenu{
        private JMenuItem startAll;
        private JMenuItem stopAll;
		private JMenuItem oneStep;
		private JMenuItem saveUniverseInitialState;
		private JMenuItem loadUniverseInitialState;
		private JMenuItem saveUniverseCurrentState;
		private JMenuItem exportToGif;
		private JMenuItem fullScreen;
        private JMenuItem closeFullScreen;

		public UniversePopupMenu(){
            if(UniversePanel.this.isFullScreen()){
                startAll = new JMenuItem("Start");
                startAll.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FullScreenFrame topFrame = (FullScreenFrame) SwingUtilities.getWindowAncestor(UniversePanel.this);
                        topFrame.start();
                    }
                });
                add(startAll);

                stopAll = new JMenuItem("Stop");
                stopAll.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FullScreenFrame topFrame = (FullScreenFrame) SwingUtilities.getWindowAncestor(UniversePanel.this);
                        topFrame.stop();
                    }
                });
                add(stopAll);
            } else {
                startAll = new JMenuItem("Start all");
                startAll.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(UniversePanel.this);
                        topFrame.startAll();
                    }
                });
                add(startAll);

                stopAll = new JMenuItem("Stop all");
                stopAll.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(UniversePanel.this);
                        topFrame.stopAll();
                    }
                });
                add(stopAll);
            }

			oneStep = new JMenuItem("Do one step");
			oneStep.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
                    UniversePanel.this.getUniverse().tick();
                    UniversePanel.this.repaint();
				}
			});
			add(oneStep);

			saveUniverseInitialState = new JMenuItem("Reset to initial state");
			saveUniverseInitialState.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
                    UniversePanel.this.getUniverse().resetToInitialState();
                    UniversePanel.this.repaint();
				}
			});
			add(saveUniverseInitialState);

			saveUniverseCurrentState = new JMenuItem("Save the universe");
			saveUniverseCurrentState.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser c = new JFileChooser();
					int rVal = c.showSaveDialog(UniversePanel.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						try {
							Universe.saveToFile(UniversePanel.this.getUniverse(), c.getSelectedFile());
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "The universe is not saved. Error: " + e1);
						}
					}
                    UniversePanel.this.repaint();
				}
			});
			add(saveUniverseCurrentState);

			loadUniverseInitialState = new JMenuItem("Load universe");
			loadUniverseInitialState.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					JFileChooser c = new JFileChooser();
					int rVal = c.showOpenDialog(UniversePanel.this);
					if (rVal == JFileChooser.APPROVE_OPTION) {
						try {
                            UniversePanel.this.setUniverse(Universe.loadFromFile(c.getSelectedFile()));
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
						} catch (ClassNotFoundException e1) {
							JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
						}

					}
                    UniversePanel.this.repaint();
				}
			});
			add(loadUniverseInitialState);

			exportToGif = new JMenuItem("Export to GIF");
			exportToGif.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					ExportToGifDialog dialog = new ExportToGifDialog(UniversePanel.this.getUniverse());
					dialog.setVisible(true);
				}
			});
			add(exportToGif);


            if(UniversePanel.this.isFullScreen()){
                closeFullScreen = new JMenuItem("Close full screen");
                closeFullScreen.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(UniversePanel.this);
                        topFrame.dispose();
                    }
                });
                add(closeFullScreen);
            } else {
                fullScreen = new JMenuItem("Full screen");
                fullScreen.addActionListener(new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        FullScreenFrame fullScreenFrame = new FullScreenFrame(UniversePanel.this.getUniverse());
                        fullScreenFrame.setVisible(true);
                    }
                });
                add(fullScreen);
            }

		}
	}
}
