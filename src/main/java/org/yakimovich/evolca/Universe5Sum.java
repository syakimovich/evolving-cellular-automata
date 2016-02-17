package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

/**
 * Cell next state depends only from its previous state and sum of 4 neighbors.
 */
public class Universe5Sum extends Universe{
    private int[][] thresholds;

    public Universe5Sum(char[][] initialCells, int[][] thresholds, char numberOfStates, boolean isCircular){
        super(initialCells, numberOfStates, isCircular);
        this.thresholds = ArrayUtils.copy2DArray(thresholds);
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

                char value = 0;
                int sum = a1 + a2 + a3 + a4;

                for(int threshold : thresholds[a0]){
                    if(sum > threshold){
                        value++;
                    }
                }

                currentCells[i][j] = value;
            }
        }
    }
}
