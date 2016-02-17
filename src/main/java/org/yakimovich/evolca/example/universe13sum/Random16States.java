package org.yakimovich.evolca.example.universe13sum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.Universe13Sum;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class Random16States {

	public static void main(String[] args) throws InterruptedException {
		final char size = 100;
        final char numberOfStates = 16;
        final boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = ArrayUtils.createRandom2DCharArray(size, size, numberOfStates);
            int[][] thresholds = ArrayUtils.createRandom2DIntArray(numberOfStates, numberOfStates - 1, numberOfStates * 12);
            Universe u = new Universe13Sum(initialCells, thresholds, numberOfStates, isCircular);
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Simple random example");
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

	}

}
