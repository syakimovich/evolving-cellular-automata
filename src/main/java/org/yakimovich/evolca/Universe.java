package org.yakimovich.evolca;

import org.yakimovich.evolca.utils.ArrayUtils;

import java.io.*;

/**
 * Matrix of cells in different states.
 */
public abstract class Universe implements Serializable {
    private int age = 0;

    protected char[][] initialCells;
    protected char[][] currentCells;
    protected char[][] previousCells;
    protected char numberOfStates;
    protected boolean isCircular = false;

    final public static char OUT_VALUE = 0;

    public Universe(char[][] initialCells, char numberOfStates, boolean isCircular){
        this.currentCells = ArrayUtils.copy2DArray(initialCells);
        this.previousCells = ArrayUtils.copy2DArray(initialCells);
        this.initialCells = ArrayUtils.copy2DArray(initialCells);
        this.numberOfStates = numberOfStates;
        this.isCircular = isCircular;
    }

    public abstract Universe copy();

    public abstract void doTick();

    public void tick(){
        doTick();
        age++;
    }

    public void tick(int n){
        for(int i = 0; i < n; i++){
            tick();
        }
    }

    public int getAge(){
        return age;
    }

    public void setAge(int age){
        this.age = age;
    }

    public char[][] getCells() {
        return ArrayUtils.copy2DArray(currentCells);
    }

    public void setCells(char[][] newCells){
        this.currentCells = ArrayUtils.copy2DArray(newCells);
        this.previousCells = ArrayUtils.copy2DArray(newCells);
        this.initialCells = ArrayUtils.copy2DArray(newCells);
    }

    public char getValue(int i, int j) {
        return currentCells[i][j];
    }

    public int getWidth() {
        if(currentCells.length < 1){
            return 0;
        }
        return currentCells[0].length;
    }

    public int getHeight() {
        return currentCells.length;
    }

    public boolean isCircular() {
        return isCircular;
    }

    public char getNumberOfStates() {
        return numberOfStates;
    }

    public void resetToInitialState() {
        this.currentCells = ArrayUtils.copy2DArray(initialCells);
        this.previousCells = ArrayUtils.copy2DArray(initialCells);
        this.setAge(0);
    }

    public static Universe loadFromFile(File file) throws IOException, ClassNotFoundException {
        FileInputStream fin = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fin);
        Universe loadedUniverse = (Universe) ois.readObject();
        ois.close();
        return loadedUniverse;
    }

    public static void saveToFile(Universe universe, File file) throws IOException {
        FileOutputStream fout = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fout);
        oos.writeObject(universe);
        oos.close();
    }
}