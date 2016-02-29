package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Window to display universes, usually 10 in 2 rows.
 */
public class UniversesWindow extends JFrame {
    private UniversePanel[] universePanels = null;
    private List<Measure> measures = new ArrayList<Measure>();
    private JPanel universesPanel = new JPanel();
    private JPanel controlsPanel = new JPanel();
    private JButton startAllButton = new JButton();
    private JButton stopAllButton = new JButton();
    private int defaultWidth;
    private int defaultHeight;
    private int defaultZoom;
    private int numberOfColumns;
    private int numberOfRows;
    private long sleepTimeInMilliseconds;

    public class Params{
        public Universe[] universes;
        public String title;
        public int defaultWidth = 1300;
        public int defaultHeight = 700;
        public int defaultZoom = 2;
        public int numberOfColumns = 5;
        public int numberOfRows = 2;
        public long sleepTimeInMilliseconds = 100;
    }

    public UniversesWindow(Universe[] universes, String title){
        super(title);
        Params params = new Params();
        params.universes = universes;
        init(params);
    }

    public UniversesWindow(Params params){
        super(params.title);
        init(params);
    }

    public void init(Params params){
        this.defaultWidth = params.defaultWidth;
        this.defaultHeight = params.defaultHeight;
        this.defaultZoom = params.defaultZoom;
        this.numberOfColumns = params.numberOfColumns;
        this.numberOfRows = params.numberOfRows;
        this.sleepTimeInMilliseconds = params.sleepTimeInMilliseconds;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(defaultWidth, defaultHeight);
        setUniverses(params.universes);
        setVisible(true);
    }

    public void addMeasure(Measure m){
        measures.add(m);
    }

    public void setUniverses(Universe[] universes){
        Container contentPane = getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        GridLayout gl = new GridLayout(0, numberOfColumns);
        universesPanel.setLayout(gl);

        int n = 0;
        universePanels = new UniversePanel[universes.length];
        for(int i = 0; i < numberOfColumns; i++){
            for(int j = 0; j < numberOfRows; j++){
                UniversePanel up = new UniversePanel(universes[n], defaultZoom);
                up.setSleepTimeInMilliseconds(sleepTimeInMilliseconds);
                up.setMeasures(measures);
                universePanels[n] = up;
                universesPanel.add(up);
                n++;
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
        int universePanelWidth = universePanels[0].getWidth();
        int universePanelHeight = universePanels[0].getHeight();

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
        if(universePanels != null){
            for(UniversePanel up : universePanels){
                up.start();
            }
        }
    }

    public void stopAll(){
        if(universePanels != null){
            for(UniversePanel up : universePanels){
                up.stop();
            }
        }
    }
}
