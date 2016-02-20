package org.yakimovich.evolca.ui;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.ArrayUtils;

import javax.swing.*;
import java.awt.*;

public class FullScreenFrame extends JFrame {
    public FullScreenFrame(Universe u){
        Universe fullScreenUniverse = u.copy();
        setExtendedState(Frame.MAXIMIZED_BOTH);
        setUndecorated(true);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        char[][] newCells = ArrayUtils.createRandom2DCharArray((int) d.getHeight(),
                (int) d.getWidth(), u.getNumberOfStates());
        fullScreenUniverse.setCells(newCells);
        UniversePanel up = new UniversePanel(fullScreenUniverse, 1, true);
        add(up);
    }
}
