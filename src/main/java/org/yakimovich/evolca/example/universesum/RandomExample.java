package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;
import org.yakimovich.evolca.utils.InitialStateGenerator;

import java.util.ArrayList;
import java.util.List;

public class RandomExample {
    public static void main(String[] args){
        int size = 100;
        char numberOfStates = 8;
        int numberOfNeighbors = 4;
        boolean isCircular = true;
        String initialState = "";
        double resultStateToZeroProbability = 0.8;

        if(args != null && args.length >= 1){
            numberOfStates = (char) Integer.parseInt(args[0]);

            if(args.length >= 2){
                numberOfNeighbors = (char) Integer.parseInt(args[1]);
            }

            if(args.length >= 3){
                initialState = args[2];
            }

            if(args.length >= 4){
                resultStateToZeroProbability = Double.parseDouble(args[3]);
            }
        }

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = null;
            if(initialState.equalsIgnoreCase("point")){
                initialCells = InitialStateGenerator.create1Point(size, size, (char)1);
            } else if(initialState.equalsIgnoreCase("rectangular")){
                initialCells = InitialStateGenerator.create3ColorsRectangular(size, size);
            } else {
                initialCells = InitialStateGenerator.createRandom(size, size, numberOfStates);
            }
            int[][] thresholds = ArrayUtils.createRandom2DIntArray(numberOfStates, numberOfStates - 1,
                    (numberOfStates - 1) * numberOfNeighbors);
            char[][] resultStates = ArrayUtils.createRandom2DCharArray(numberOfStates,numberOfStates, numberOfStates);
            resultStates = ArrayUtils.replaceWithZeros(resultStates, resultStateToZeroProbability);
            for(int j = 0; j < resultStates.length; j++){
                resultStates[j][0] = 0;
            }
            Universe u = new UniverseSum(initialCells, numberOfNeighbors, thresholds, resultStates, numberOfStates, isCircular);
            universes.add(u);
        }

        MainWindow mainWindow = new MainWindow("Random example");
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.setSleepTimeInMilliseconds(100);
        mainWindow.setUniverses(universes);
    }
}
