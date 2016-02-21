package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColourIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class ExampleWithCmdParams {

	public static void main(String[] args) throws InterruptedException {
        char size = 100;
        char numberOfStates = 16;
        int numberOfNeighbors = 4;

        if(args != null && args.length >= 1){
            numberOfStates = (char) Integer.parseInt(args[0]);

            if(args.length >= 2){
                numberOfNeighbors = (char) Integer.parseInt(args[1]);
            }
        }

        final boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = ArrayUtils.createRandom2DCharArray(size, size, numberOfStates);
            int[][] thresholds = ArrayUtils.createRandom2DIntArray(numberOfStates, numberOfStates - 1,
                    (numberOfStates - 1) * numberOfNeighbors);
            char[][] resultStates = ArrayUtils.createRandom2DCharArray(numberOfStates,numberOfStates, numberOfStates);
            Universe u = new UniverseSum(initialCells, numberOfNeighbors, thresholds, resultStates, numberOfStates, isCircular);
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Simple random example");
        mainWindow.addMeasure(new AvgNeighborColourIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);

	}

}
