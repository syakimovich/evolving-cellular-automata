package org.yakimovich.evolca.example.universe5;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.Universe5;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class Random4States {

	public static void main(String[] args) throws InterruptedException {
		final char size = 100;
        final char numberOfStates = 4;
        final double[] thresholds = {0.8, 0.9, 0.95};
        final boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = ArrayUtils.createRandom2DCharArray(size, size, numberOfStates);
            char[][][][][] matrix = ArrayUtils.createRandomMatrix5D(thresholds);
            Universe u = new Universe5(initialCells, matrix, numberOfStates, isCircular);
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Simple random example");
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

	}

}
