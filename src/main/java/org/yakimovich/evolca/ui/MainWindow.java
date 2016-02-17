package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.measures.AvgNeighborColourIndex5;
import org.yakimovich.evolca.measures.NonZeroPercentage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainWindow extends JFrame {

    public static int DEFAULT_WIDTH = 1300;
    public static int DEFAULT_HEIGHT = 700;

    private int numberOfColumns = 5;
    private int numberOfRows = 2;
    private long sleepTimeInMilliseconds = 500;
    private List<UniversePanelWithInfo> universePanels;
    private List<Measure> measures;
    private boolean tickAll = false;
    private JPanel universesPanel = new JPanel();
    private JPanel controlsPanel = new JPanel();
    private JButton startAllButton = new JButton();
    private JButton stopAllButton = new JButton();

    public MainWindow(String title){
        super(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setVisible(true);
        startLoop();

        measures = new ArrayList<Measure>();
        measures.add(new AvgNeighborColourIndex5());
        measures.add(new NonZeroPercentage());
    }

    public void setUniverses(List<Universe> universes){
        Iterator<Universe> universesIterator = universes.iterator();

        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        GridLayout gl = new GridLayout(0, 5);
        universesPanel.setLayout(gl);

        universePanels = new ArrayList<UniversePanelWithInfo>();
        for(int i = 0; i < numberOfColumns; i++){
            for(int j = 0; j < numberOfRows; j++){

                if(universesIterator.hasNext()){
                    final UniversePanelWithInfo upi = new UniversePanelWithInfo(universesIterator.next(), 2);
                    upi.setMeasures(measures);
                    upi.addMouseListener(new MouseAdapter(){
                        public void mousePressed(MouseEvent e){
                            if (e.isPopupTrigger())
                                doPop(e);
                        }

                        public void mouseReleased(MouseEvent e){
                            if (e.isPopupTrigger())
                                doPop(e);
                        }

                        private void doPop(MouseEvent e){
                            UniversePopupMenu menu = new UniversePopupMenu(upi);
                            menu.show(e.getComponent(), e.getX(), e.getY());
                        }
                    });
                    universePanels.add(upi);
                    universesPanel.add(upi);
                }
            }
        }
        startAllButton.setText("Start all");
        startAllButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                tickAll = true;
            }
        });

        stopAllButton.setText("Stop all");
        stopAllButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                tickAll = false;
            }
        });
        int universePanelWidth = universePanels.get(0).getWidth();
        int universePanelHeight = universePanels.get(0).getHeight();

        universesPanel.setSize(universePanelWidth * 5, universePanelHeight * 2);

        controlsPanel.add(startAllButton);
        controlsPanel.add(stopAllButton);
        controlsPanel.setMaximumSize(new Dimension(500, 200));
        controlsPanel.setSize(new Dimension(500, 200));

        contentPane.add(universesPanel);
        contentPane.add(controlsPanel);
        repaint();
    }

    private void startLoop(){
        Runnable loop = new Runnable(){
            @Override
            public void run() {
                try {
                    while(true){
                        if(tickAll){
                            tickAll();
                        }
                        Thread.sleep(sleepTimeInMilliseconds);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread loopThread = new Thread(loop);
        loopThread.start();
    }

    private void tickAll(){
        for(UniversePanelWithInfo up : universePanels){
            up.getUniverse().tick();
        }
        repaint();
    }

    public int getNumberOfColumns() {
        return numberOfColumns;
    }

    public void setNumberOfColumns(int numberOfColumns) {
        this.numberOfColumns = numberOfColumns;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public void setNumberOfRows(int numberOfRows) {
        this.numberOfRows = numberOfRows;
    }

    public boolean isTickAll() {
        return tickAll;
    }

    public void setTickAll(boolean tickAll) {
        this.tickAll = tickAll;
    }

    private class UniversePopupMenu extends JPopupMenu{
        private JMenuItem oneStep;
        private JMenuItem saveUniverseInitialState;
        private JMenuItem loadUniverseInitialState;
        private JMenuItem saveUniverseCurrentState;

        public UniversePopupMenu(final UniversePanelWithInfo upi){
            oneStep = new JMenuItem("Do one step");
            oneStep.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    upi.getUniverse().tick();
                    upi.repaint();
                }
            });
            add(oneStep);

            saveUniverseInitialState = new JMenuItem("Reset to initial state");
            saveUniverseInitialState.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    upi.getUniverse().resetToInitialState();
                    upi.repaint();
                }
            });
            add(saveUniverseInitialState);

            saveUniverseCurrentState = new JMenuItem("Save the universe");
            saveUniverseCurrentState.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser c = new JFileChooser();
                    int rVal = c.showSaveDialog(MainWindow.this);
                    if (rVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            Universe.saveToFile(upi.getUniverse(), c.getSelectedFile());
                            JOptionPane.showMessageDialog(null, "The universe is successfully saved to file.");
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "The universe is not saved. Error: " + e1);
                        }
                    }
                    upi.repaint();
                }
            });
            add(saveUniverseCurrentState);

            loadUniverseInitialState = new JMenuItem("Load universe");
            loadUniverseInitialState.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser c = new JFileChooser();
                    int rVal = c.showSaveDialog(MainWindow.this);
                    if (rVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            upi.setUniverse(Universe.loadFromFile(c.getSelectedFile()));
                            JOptionPane.showMessageDialog(null, "The universe is successfully loaded from file.");
                        } catch (IOException e1) {
                            JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
                        } catch (ClassNotFoundException e1) {
                            JOptionPane.showMessageDialog(null, "The universe is not loaded. Error: " + e1);
                        }

                    }
                    upi.repaint();
                }
            });
            add(loadUniverseInitialState);
        }
    }

    public long getSleepTimeInMilliseconds() {
        return sleepTimeInMilliseconds;
    }

    public void setSleepTimeInMilliseconds(long sleepTimeInMilliseconds) {
        this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
    }
}
