package org.yakimovich.evolca.example.universe5;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.Universe5;
import org.yakimovich.evolca.measures.AvgNeighborColourIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.CellsArrayGenerator;

import java.util.ArrayList;
import java.util.List;

public class Random8StatesFromPoint {

	public static void main(String[] args) throws InterruptedException {
		final char size = 100;
        final char numberOfStates = 8;
        final double[] thresholds = {0.825, 0.850, 0.875, 0.9, 0.925, 0.95, 0.975};
        final double[] thresholds2 = {0.766, 0.8, 0.833, 0.866, 0.9, 0.933, 0.966};
        final double[] thresholds3 = {0.65, 0.7, 0.75, 0.8, 0.85, 0.9, 0.95};

        final boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = CellsArrayGenerator.create1Point(size);
            char[][][][][] matrix = ArrayUtils.createRandomMatrix5D(thresholds2);
            matrix[0][0][0][0][0] = 0;
            Universe u = new Universe5(initialCells, matrix, numberOfStates, isCircular);
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Simple random example");
        mainWindow.addMeasure(new NonZeroPercentage());
        mainWindow.addMeasure(new AvgNeighborColourIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

	}

}
