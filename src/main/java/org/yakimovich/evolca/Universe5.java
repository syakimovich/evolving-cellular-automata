package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

import java.io.*;

/**
 * Cell next state depends only from it previous state and 4 neighbors.
 */
public class Universe5 extends Universe{
    private char[][] initialCells;
    private char[][] currentCells;
    private char[][] previousCells;
    private char[][][][][] matrix;
    private char numberOfStates;
    private boolean isCircular = false;
    final public static char OUT_VALUE = 0;

    public Universe5(char[][] initialCells, char[][][][][] matrix, char numberOfStates,  boolean isCircular){
        this.currentCells = ArrayUtils.copy2DArray(initialCells);
        this.previousCells = ArrayUtils.copy2DArray(initialCells);
        this.initialCells = ArrayUtils.copy2DArray(initialCells);
        this.matrix = ArrayUtils.copy5DArray(matrix);
        this.numberOfStates = numberOfStates;
        this.isCircular = isCircular;
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

    @Override
    public char getValue(int i, int j) {
        return currentCells[i][j];
    }

    @Override
    public int getWidth() {
        return currentCells.length;
    }

    @Override
    public int getHeight() {
        if(currentCells.length < 1){
            return 0;
        }
        return currentCells[0].length;
    }

    @Override
    public boolean isCircular() {
        return isCircular;
    }

    @Override
    public char getNumberOfStates() {
        return numberOfStates;
    }

    @Override
    public void resetToInitialState() {
        this.currentCells = ArrayUtils.copy2DArray(initialCells);
        this.previousCells = ArrayUtils.copy2DArray(initialCells);
        this.setAge(0);
    }

    @Override
    public void loadFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        Universe5 loadedUniverse = (Universe5) ois.readObject();
        ois.close();
        this.setAge(loadedUniverse.getAge());
        this.currentCells = loadedUniverse.currentCells;
        this.previousCells = loadedUniverse.previousCells;
        this.initialCells = loadedUniverse.initialCells;
        this.matrix = loadedUniverse.matrix;
        this.numberOfStates = loadedUniverse.numberOfStates;
        this.isCircular = loadedUniverse.isCircular;
    }

    @Override
    public void saveToFile(File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(this);
        oos.close();
    }
}
