package pl.maxmati.po.automaton.neighborhood;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;
import pl.maxmati.po.automaton.neighborhood.CellNeighborhood;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maxmati on 11/23/15
 */
public class VonNeumanNeighborhood implements CellNeighborhood {
    int radius;

    public VonNeumanNeighborhood(int radius) {
        this.radius = radius;
    }

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

                neighbors.add(new Cords2D(cords.x + x, cords.y + y));
            }

        }

        return neighbors;
    }

    @Override
    public String toString() {
        return "VonNeumanNeighborhood{" + radius + '}';
    }
}
