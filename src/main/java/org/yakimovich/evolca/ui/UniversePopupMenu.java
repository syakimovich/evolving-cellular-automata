package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UniversePopupMenu extends JPopupMenu{
    private JMenuItem startAll;
    private JMenuItem stopAll;
    private JMenuItem oneStep;
    private JMenuItem saveUniverseInitialState;
    private JMenuItem loadUniverseInitialState;
    private JMenuItem saveUniverseCurrentState;
    private JMenuItem exportToGif;
    private JMenuItem fullScreen;
    private JMenuItem closeFullScreen;

    public UniversePopupMenu(UniversePanel universePanel){
        if(universePanel.isFullScreen()){
            startAll = new JMenuItem("Start");
            startAll.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    FullScreenFrame topFrame = (FullScreenFrame) SwingUtilities.getWindowAncestor(universePanel);
                    topFrame.start();
                }
            });
            add(startAll);

            stopAll = new JMenuItem("Stop");
            stopAll.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    FullScreenFrame topFrame = (FullScreenFrame) SwingUtilities.getWindowAncestor(universePanel);
                    topFrame.stop();
                }
            });
            add(stopAll);
        } else {
            startAll = new JMenuItem("Start all");
            startAll.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(universePanel);
                    topFrame.startAll();
                }
            });
            add(startAll);

            stopAll = new JMenuItem("Stop all");
            stopAll.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    MainWindow topFrame = (MainWindow) SwingUtilities.getWindowAncestor(universePanel);
                    topFrame.stopAll();
                }
            });
            add(stopAll);
        }

        oneStep = new JMenuItem("Do one step");
        oneStep.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.getUniverse().tick();
                universePanel.repaint();
            }
        });
        add(oneStep);

        saveUniverseInitialState = new JMenuItem("Reset to initial state");
        saveUniverseInitialState.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.getUniverse().resetToInitialState();
                universePanel.repaint();
            }
        });
        add(saveUniverseInitialState);

        saveUniverseCurrentState = new JMenuItem("Save the universe");
        saveUniverseCurrentState.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showSaveDialog(universePanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Universe.saveToFile(universePanel.getUniverse(), c.getSelectedFile());
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "The universe is not saved. Error: " + e1);
                    }
                }
                universePanel.repaint();
            }
        });
        add(saveUniverseCurrentState);

        loadUniverseInitialState = new JMenuItem("Load universe");
        loadUniverseInitialState.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                int rVal = c.showOpenDialog(universePanel);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        universePanel.setUniverse(Universe.loadFromFile(c.getSelectedFile()));
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
                    } catch (ClassNotFoundException e1) {
                        JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
                    }

                }
                universePanel.repaint();
            }
        });
        add(loadUniverseInitialState);

        exportToGif = new JMenuItem("Export to GIF");
        exportToGif.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportToGifDialog dialog = new ExportToGifDialog(universePanel.getUniverse());
                dialog.setVisible(true);
            }
        });
        add(exportToGif);


        if(universePanel.isFullScreen()){
            closeFullScreen = new JMenuItem("Close full screen");
            closeFullScreen.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(universePanel);
                    topFrame.dispose();
                }
            });
            add(closeFullScreen);
        } else {
            fullScreen = new JMenuItem("Full screen");
            fullScreen.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    FullScreenFrame fullScreenFrame = new FullScreenFrame(universePanel.getUniverse());
                    fullScreenFrame.setVisible(true);
                }
            });
            add(fullScreen);
        }

    }
}

