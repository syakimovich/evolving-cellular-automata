package org.yakimovich.evolca;

/**
 * Measure to be able to numerically compare universes.
 */
public interface Measure {
    public String getName();
    public double getValue(Universe u);
}
