package pl.maxmati.po.automaton.neighborhood;

import com.google.common.base.Objects;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.exceptions.NotSupportedCellCoordinates;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by maxmati on 11/20/15
 */
public class MooreNeighborhood implements CellNeighborhood {
    private final int radius;

    public MooreNeighborhood(int radius) {
        this.radius = radius;
    }

    @Override
    public Set<CellCoordinates> cellNeighbors(CellCoordinates cell) {
        if(cell instanceof Cords2D){
            Set<CellCoordinates> cords = new HashSet<>(radius*radius - 1);
            Cords2D cord = (Cords2D) cell;

            for(int i = -radius; i <= radius; ++i)
                for(int j = -radius; j <= radius; ++j)
                    if(j != 0 || i != 0)
                        cords.add(new Cords2D(cord.x + i, cord.y + j));

            return cords;
        }

        throw new NotSupportedCellCoordinates();
    }

    @Override
    public String toString() {
        return "MooreNeighborhood{" +
                "radius=" + radius +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MooreNeighborhood)) return false;
        MooreNeighborhood that = (MooreNeighborhood) o;
        return Objects.equal(radius, that.radius);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(radius);
    }
}
