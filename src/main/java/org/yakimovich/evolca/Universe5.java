package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

public class Universe5 extends Universe{
    private char[][] currentCells;
    private char[][] previousCells;
    private char[][][][][] matrix;
    private boolean isCircular = false;
    final public static char OUT_VALUE = 0;

    public Universe5(char[][] initialCells, boolean isCircular){
        currentCells = ArrayUtils.copy2DArray(initialCells);
        previousCells = ArrayUtils.copy2DArray(initialCells);
        this.isCircular = isCircular;
    }

    @Override
    public void tick(){
        char[][] prevCellsRef = previousCells;
        previousCells = currentCells;
        currentCells = prevCellsRef;

        for(int i = 0; i < previousCells.length; i++){
            for(int j = 0; j < previousCells[0].length; j++){

                int p = i;
                int q = j;
                int a0 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i - 1;
                q = j - 1;
                int a1 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i + 1;
                q = j - 1;
                int a2 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i - 1;
                q = j + 1;
                int a3 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                p = i + 1;
                q = j + 1;
                int a4 = ArrayUtils.getSafeValue2D(p, q, previousCells, isCircular, OUT_VALUE);

                currentCells[i][j] = matrix[a0][a1][a2][a3][a4];
            }
        }
    }

    @Override
    public char[][] getCells() {
        return ArrayUtils.copy2DArray(currentCells);
    }
}
