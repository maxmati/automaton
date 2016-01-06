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
 * Calculate neighbours in terms of Von Neuman Neighborhood with specified radius and possibly with wrapping.
 *
 */
public class VonNeumanNeighborhood implements CellNeighborhood {
    int radius;
    private boolean wrap = false;
    private Integer width = null;
    private Integer height = null;

    /**
     * Creates instance which calculate neighbors with specified radius and with specified wrapping
     * @param radius Radius of neighborhood
     * @param wrap Defines if should wrap board.
     * @param width Width of the board. Required for wrapping.
     * @param height Height of the board. Required for wrapping.
     */
    public VonNeumanNeighborhood(int radius, boolean wrap, Integer width, Integer height) {
        this(radius);
        this.wrap = wrap;
        this.width = width;
        this.height = height;
    }

    /**
     * Creates instance which calculate neighbors with specified radius and without wrapping
     * @param radius Radius of neighborhood
     */
    public VonNeumanNeighborhood(int radius) {
        this.radius = radius;
    }

    /**
     * Supports only {@link Cords2D}
     *
     * @see CellNeighborhood#cellNeighbors(CellCoordinates)
     */
    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if(!(cell instanceof Cords2D))
            throw new NotSupportedCellCoordinates();
        Cords2D cords = (Cords2D) cell;

        Set<CellCoordinates> neighbors = new HashSet<>();
        for (int y = -radius; y <= radius; ++y) {
            int xRadius = radius - Math.abs(y);
            for(int x = -xRadius; x <= xRadius; ++x){
                if(x == 0 && y == 0)
                    continue;

                int nx = wrap ? Utils.mod(cords.x + x, width) : cords.x + x;
                int ny = wrap ? Utils.mod(cords.y + y, height) : cords.y + y;

                neighbors.add(new Cords2D(nx, ny));
//                neighbors.add(new Cords2D(cords.x + x, cords.y + y));
            }

        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "VonNeumanNeighborhood{" + radius + '}';
    }
}
