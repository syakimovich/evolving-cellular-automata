package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

/**
 * Cell next state depends only from the sum of neighboring cells states.
 */
public class UniverseSum extends Universe{
    private int[][] thresholds;
    private char[][] resultStates;
    private int numberOfNeighbors;

    public UniverseSum(char[][] initialCells, int numberOfNeighbors,
                       int[][] thresholds, char[][] resultStates, char numberOfStates, boolean isCircular){
        super(initialCells, numberOfStates, isCircular);
        this.numberOfNeighbors = numberOfNeighbors;
        this.thresholds = ArrayUtils.copy2DArray(thresholds);
        this.resultStates = ArrayUtils.copy2DArray(resultStates);
    }

    public UniverseSum(char[][] initialCells, int numberOfNeighbors,
                       int[][] thresholds, char numberOfStates, boolean isCircular){
        this(initialCells, numberOfNeighbors, thresholds,
                getDefaultResultStates(thresholds), numberOfStates, isCircular);
    }

    private static char[][] getDefaultResultStates(int[][] thresholds){
        char[][] defaultResultStates = new char[thresholds.length][thresholds[0].length + 1];
        for(int i = 0; i < defaultResultStates.length; i++){
            for(int j = 0; j < defaultResultStates[0].length; j++){
                defaultResultStates[i][j] = (char) j;
            }
        }
        return defaultResultStates;
    }

    @Override
    public Universe copy() {
        return new UniverseSum(initialCells, numberOfNeighbors, thresholds, resultStates, numberOfStates, isCircular);
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

                int sum = 0;

                if(numberOfNeighbors >= 1){
                    p = i - 1;
                    q = j;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 2){
                    p = i;
                    q = j - 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 3){
                    p = i;
                    q = j + 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 4){
                    p = i + 1;
                    q = j;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 5){
                    p = i - 1;
                    q = j - 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 6){
                    p = i - 1;
                    q = j + 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 7){
                    p = i + 1;
                    q = j - 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 8){
                    p = i + 1;
                    q = j + 1;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 9){
                    p = i - 2;
                    q = j;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 10){
                    p = i;
                    q = j - 2;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 11){
                    p = i;
                    q = j + 2;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                if(numberOfNeighbors >= 12){
                    p = i + 2;
                    q = j;
                    sum += ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);
                }

                char value = 0;

                for(int threshold : thresholds[a0]){
                    if(sum > threshold){
                        value++;
                    }
                }

                currentCells[i][j] = resultStates[a0][value];
            }
        }
    }
}
