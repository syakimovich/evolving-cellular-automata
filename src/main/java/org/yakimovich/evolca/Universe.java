package org.yakimovich.evolca;

import java.io.Serializable;

/**
 * Matrix of cells in different states.
 */
public abstract class Universe implements Serializable {

    public abstract void tick();

    public void tick(int n){
        for(int i = 0; i < n; i++){
            tick();
        }
    }

    public abstract char[][] getCells();
}