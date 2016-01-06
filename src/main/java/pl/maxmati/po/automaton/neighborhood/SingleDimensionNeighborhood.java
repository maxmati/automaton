package pl.maxmati.po.automaton.neighborhood;

import pl.maxmati.po.automaton.Utils;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Calculate neighbours in single dimension with specified radius and possibly with wrapping.
 *
 */
public class SingleDimensionNeighborhood implements CellNeighborhood{
    private final int radius;
    private boolean wrap = false;
    private Integer width = null;

    /**
     * Creates instance which calculate neighbors with specified radius and without wrapping
     * @param radius Radius of neighborhood
     */
    public SingleDimensionNeighborhood(int radius) {
        this.radius = radius;
    }

    /**
     * Creates instance which calculate neighbors with specified radius and with specified wrapping
     * @param radius Radius of neighborhood
     * @param wrap Defines if should wrap board.
     * @param width Width of the board. Required for wrapping.
     */
    public SingleDimensionNeighborhood(int radius, boolean wrap, Integer width) {
        this(radius);
        this.wrap = wrap;
        this.width = width;
    }

    /**
     * Supports only {@link Cords1D}
     *
     * @see CellNeighborhood#cellNeighbors(CellCoordinates)
     */
    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if(!(cell instanceof Cords1D))
            throw new NotSupportedCellCoordinates();

        Set<CellCoordinates> neighbors = new HashSet<>(2*radius);

        Cords1D cords = (Cords1D) cell;
        for (int i = -radius; i <= radius; i++) {
            if(i == 0) continue;

            neighbors.add(new Cords1D(wrap ? Utils.mod(cords.x + i, width) : cords.x + i));
        }
        return neighbors;
    }
}
