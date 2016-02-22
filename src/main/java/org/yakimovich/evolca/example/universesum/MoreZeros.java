package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColourIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MoreZeros {

	public static void main(String[] args) throws InterruptedException {
        char size = 100;
        char numberOfStates = 64;
        int numberOfNeighbors = 8;
        double initialStateToZeroProbability = 0;
        double resultStateToZeroProbability = 0.8;
        Random r = new Random();

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

            for(int n = 0; n < initialCells.length; n++){
                for(int m = 0; m < initialCells[0].length; m++){
                    if(r.nextDouble() < initialStateToZeroProbability){
                        initialCells[n][m] = 0;
                    }

                }
            }

            int[][] thresholds = ArrayUtils.createRandom2DIntArray(numberOfStates, numberOfStates - 1,
                    (numberOfStates - 1) * numberOfNeighbors);
            char[][] resultStates = ArrayUtils.createRandom2DCharArray(numberOfStates,numberOfStates, numberOfStates);
            for(int n = 0; n < resultStates.length; n++){
                for(int m = 0; m < resultStates[0].length; m++){
                    if(r.nextDouble() < resultStateToZeroProbability){
                        resultStates[n][m] = 0;
                    }

                }
            }

            resultStates[0][0] = 0;

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
