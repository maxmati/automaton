package pl.maxmati.po.automaton.neighborhood;

import pl.maxmati.po.automaton.Utils;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Calculate neighbours in terms of Moore Neighborhood with specified radius and possibly with wrapping.
 *
 */
public class MooreNeighborhood implements CellNeighborhood {
    private final int radius;
    private final int height;
    private final int width;
    private final boolean wrap;

    /**
     * Creates instance which calculate neighbors with specified radius and without wrapping
     * @param radius Radius of neighborhood
     */
    public MooreNeighborhood(int radius) {
        this(radius, false, 0, 0);
    }

    /**
     * Creates instance which calculate neighbors with specified radius and with specified wrapping
     * @param radius Radius of neighborhood
     * @param wrap Defines if should wrap board.
     * @param width Width of the board. Required for wrapping.
     * @param height Height of the board. Required for wrapping.
     */
    public MooreNeighborhood(int radius, boolean wrap, int width, int height) {
        this.radius = radius;
        this.wrap = wrap;
        this.width = width;
        this.height = height;
    }

    /**
     * Supports only {@link Cords2D}
     *
     * @see CellNeighborhood#cellNeighbors(CellCoordinates)
     */
    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if(cell instanceof Cords2D){
            Set<CellCoordinates> cords = new HashSet<>(radius*radius - 1);
            Cords2D cord = (Cords2D) cell;

            for(int i = -radius; i <= radius; ++i)
                for(int j = -radius; j <= radius; ++j)
                    if(j != 0 || i != 0) {
                        int x = wrap ? Utils.mod(cord.x + j, width) : cord.x + j;
                        int y = wrap ? Utils.mod(cord.y + i, height) : cord.y + i;

                        cords.add(new Cords2D(x, y));
                    }

            return cords;
        }

        throw new NotSupportedCellCoordinates();
    }

    @Override
    public String toString() {
        return "MooreNeighborhood{" + radius + '}';
    }
}
