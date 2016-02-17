package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

import java.io.*;

/**
 * Cell next state depends only from its previous state and 4 neighbors.
 */
public class Universe5 extends Universe{
    private char[][][][][] matrix;

    public Universe5(char[][] initialCells, char[][][][][] matrix, char numberOfStates, boolean isCircular){
        super(initialCells, numberOfStates, isCircular);
        this.matrix = ArrayUtils.copy5DArray(matrix);
    }

    @Override
    public void doTick(){
        char[][] prevCellsRef = previousCells;
        previousCells = currentCells;
        currentCells = prevCellsRef;

        for(int i = 0; i < previousCells.length; i++){
            for(int j = 0; j < previousCells[0].length; j++){

                int p = i;
                int q = j;
                int a0 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i - 1;
                q = j;
                int a1 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i + 1;
                q = j;
                int a2 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i;
                q = j + 1;
                int a3 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i;
                q = j - 1;
                int a4 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                currentCells[i][j] = matrix[a0][a1][a2][a3][a4];
            }
        }
    }
}
