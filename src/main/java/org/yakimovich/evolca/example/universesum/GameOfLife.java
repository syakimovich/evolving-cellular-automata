package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColourIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.ui.MainWindow;
import org.yakimovich.evolca.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    public static void main(String[] args){
        char size = 100;
        char numberOfStates = 2;
        int numberOfNeighbors = 8;
        boolean isCircular = true;

        List<Universe> universes = new ArrayList<Universe>();
        for(int i = 0; i < 10; i++){
            char[][] initialCells = ArrayUtils.createRandom2DCharArray(size, size, numberOfStates);
            int[][] thresholds = new int[2][2];
            thresholds[0][0] = 2;
            thresholds[0][1] = 3;

            thresholds[1][0] = 1;
            thresholds[1][1] = 3;

            char[][] resultStates = new char[2][3];
            resultStates[0][0] = 0;
            resultStates[0][1] = 1;
            resultStates[0][2] = 0;

            resultStates[1][0] = 0;
            resultStates[1][1] = 1;
            resultStates[1][2] = 0;

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