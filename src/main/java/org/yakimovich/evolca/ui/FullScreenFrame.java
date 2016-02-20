package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.ArrayUtils;

import javax.swing.*;
import java.awt.*;

public class FullScreenFrame extends JFrame {
    private boolean tick = false;
    private long sleepTimeInMilliseconds = 100;
    private Universe universe;

    public FullScreenFrame(Universe u){
        universe = u.copy();
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        char[][] newCells = ArrayUtils.createRandom2DCharArray((int) d.getHeight(),
                (int) d.getWidth(), u.getNumberOfStates());
        universe.setCells(newCells);
        UniversePanel up = new UniversePanel(universe, 1, true);
        add(up);
        startLoop();
    }

    private void startLoop(){
        Runnable loop = new Runnable(){
            @Override
            public void run() {
                try {
                    while(true){
                        if(tick){
                            universe.tick();
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
