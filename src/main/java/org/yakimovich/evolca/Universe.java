package org.yakimovich.evolca;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

/**
 * Matrix of cells in different states.
 */
public abstract class Universe implements Serializable {

    private int age = 0;

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

    public abstract char[][] getCells();

    public abstract char getValue(int i, int j);

    public abstract int getWidth();

    public abstract int getHeight();

    public abstract boolean isCircular();

    public abstract char getNumberOfStates();

    public abstract void resetToInitialState();

    public abstract void saveToFile(File file) throws IOException;

    public abstract void loadFromFile(File file) throws IOException, ClassNotFoundException;
}