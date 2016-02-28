package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class UniversePanel extends JPanel {
    private JLabel label;
    private BareUniversePanel universePanel;
    private List<Measure> measures = new ArrayList<Measure>();
    private NumberFormat doubleFormatter = new DecimalFormat("######.00");
    private boolean tick = false;
    private long sleepTimeInMilliseconds = 100;

    public UniversePanel(Universe universe, int zoom){
        universePanel = new BareUniversePanel(universe, zoom);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(this.universePanel);
        label = new JLabel("");
        this.add(label);
        updateSize();
        startLoop();
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
        int minLabel = 0;
        if((label.getText() != null) && (label.getText().trim().length() > 0)){
            minLabel = 100;
        }
        Dimension size = new Dimension(universePanel.getWidth(),
                universePanel.getHeight() + minLabel);
        setSize(size);
        setMinimumSize(size);
    }

    public void setUniverse(Universe universe){
        universePanel.setUniverse(universe);
        updateSize();
    }

    public void setZoom(int zoom){
        universePanel.setZoom(zoom);
        updateSize();
    }

    public void setLabelText(String text){
        label.setText(text);
    }

    public void paint(Graphics g) {
        label.setText(getInfoHTML());
        super.paint(g);
    }

    public Universe getUniverse(){
        return universePanel.getUniverse();
    }

    public int getZoom(){
        return universePanel.getZoom();
    }

    public void addMeasure(Measure m){
        measures.add(m);
    }

    public void setMeasures(List<Measure> newMeasures){
        measures = newMeasures;
    }

    private String getInfoBody() {
        StringBuffer result = new StringBuffer();
        result.append("age: ");
        result.append(universePanel.getUniverse().getAge());
        for(Measure m: measures){
            result.append("<br/>");
            result.append(m.getName());
            result.append(": ");
            result.append(doubleFormatter.format(m.getValue(universePanel.getUniverse())));
        }
        return result.toString();
    }

    public String getInfoHTML() {return "<html><body>" + getInfoBody() + "<body><html>"; }

    private void startLoop(){
        Runnable loop = new Runnable(){
            @Override
            public void run() {
                try {
                    while(true){
                        if(tick){
                            universePanel.getUniverse().tick();
                            repaint();
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

    public void start(){
        tick = true;
    }

    public void stop(){
        tick = false;
    }

    public long getSleepTimeInMilliseconds() {
        return sleepTimeInMilliseconds;
    }

    public void setSleepTimeInMilliseconds(long sleepTimeInMilliseconds) {
        this.sleepTimeInMilliseconds = sleepTimeInMilliseconds;
    }
}
