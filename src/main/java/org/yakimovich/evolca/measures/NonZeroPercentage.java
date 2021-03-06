package org.yakimovich.evolca.measures;

import org.yakimovich.evolca.Measure;
import org.yakimovich.evolca.Universe;
import org.yakimovich.evolca.utils.ArrayUtils;

/**
 * Percentage of cells that are not in a state 0.
 */
public class NonZeroPercentage implements Measure {

    @Override
    public String getName() {
        return "NZP";
    }

    @Override
    public double getValue(Universe u) {
        int nonZero = 0;
        for(int i = 0; i < u.getHeight(); i++) {
            for (int j = 0; j < u.getWidth(); j++) {
                if (u.getValue(i, j) != 0) {
                    nonZero++;
                }
            }
        }
        return (100.0 * nonZero) / (u.getWidth() * u.getHeight());
    }
}
