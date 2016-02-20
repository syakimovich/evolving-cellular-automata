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
    private List<Measure> measures = new ArrayList<Measure>();
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
    }

    public void addMeasure(Measure m){
        measures.add(m);
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
                    final UniversePanelWithInfo upi = new UniversePanelWithInfo(universesIterator.next(), 2, false);
                    upi.setMeasures(measures);
                    universePanels.add(upi);
                    universesPanel.add(upi);
                }
            }
        }
        startAllButton.setText("Start all");
        startAllButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                startAll();
            }
        });

        stopAllButton.setText("Stop all");
        stopAllButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                stopAll();
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

    public void startAll(){
        tickAll = true;
    }

    public void stopAll(){
        tickAll = false;
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

    public long getSleepTimeInMilliseconds() {
        return sleepTimeInMilliseconds;
    }

    public void setSleepTimeInMilliseconds(long sleepTimeInMilliseconds) {
        this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
    }
}
