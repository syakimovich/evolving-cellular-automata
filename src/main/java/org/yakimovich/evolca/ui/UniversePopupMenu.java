package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class UniversePopupMenu extends JPopupMenu {
    public UniversePopupMenu(UniversePanel universePanel){
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.start();
            }
        });
        add(start);

        JMenuItem stop = new JMenuItem("Stop");
        stop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.stop();
            }
        });
        add(stop);

        JMenuItem oneStep = new JMenuItem("Do one step");
        oneStep.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.getUniverse().tick();
                universePanel.repaint();
            }
        });
        add(oneStep);

        JMenuItem resetToUniverseInitialState = new JMenuItem("Reset to initial state");
        resetToUniverseInitialState.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                universePanel.getUniverse().resetToInitialState();
                universePanel.repaint();
            }
        });
        add(resetToUniverseInitialState);

        JMenuItem saveUniverse = new JMenuItem("Save the universe");
        saveUniverse.addActionListener(new ActionListener(){
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
        add(saveUniverse);

        JMenuItem loadUniverse = new JMenuItem("Load universe");
        loadUniverse.addActionListener(new ActionListener(){
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
        add(loadUniverse);

        JMenuItem exportToGif = new JMenuItem("Export to GIF");
        exportToGif.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ExportToGifDialog dialog = new ExportToGifDialog(universePanel.getUniverse());
                dialog.setVisible(true);
            }
        });
        add(exportToGif);
    }

}
