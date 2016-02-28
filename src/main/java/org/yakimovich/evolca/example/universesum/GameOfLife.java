package org.yakimovich.evolca.example.universesum;

import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.UniverseSum;
import org.yakimovich.evolca.measures.AvgNeighborColorIndex5;
import org.yakimovich.evolca.measures.Gini;
import org.yakimovich.evolca.measures.NonZeroPercentage;
import org.yakimovich.evolca.ui.UniversesWindow;
import org.yakimovich.evolca.utils.InitialStateGenerator;

import java.util.ArrayList;
import java.util.List;

public class GameOfLife {
    public static void main(String[] args){
        char size = 100;
        char numberOfStates = 2;
        int numberOfNeighbors = 8;
        boolean isCircular = true;

        Universe[] universes = new Universe[10];
        for(int i = 0; i < 10; i++){
            char[][] initialCells = InitialStateGenerator.createRandom(size, size, numberOfStates);
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

            universes[i] = new UniverseSum(initialCells, numberOfNeighbors, thresholds, resultStates, numberOfStates, isCircular);
        }

        UniversesWindow mainWindow = new UniversesWindow(universes, "Game Of Life example");
        mainWindow.addMeasure(new AvgNeighborColorIndex5());
        mainWindow.addMeasure(new Gini());
        mainWindow.addMeasure(new NonZeroPercentage());
    }
}
