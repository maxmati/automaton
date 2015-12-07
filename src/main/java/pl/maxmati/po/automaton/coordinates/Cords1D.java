package pl.maxmati.po.automaton.coordinates;

/**
 * Created by maxmati on 12/7/15.
 */
public class Cords1D implements CellCoordinates{
    public final int x;

    public Cords1D(int x){
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cords1D cords1D = (Cords1D) o;

        return x == cords1D.x;

    }

    @Override
    public int hashCode() {
        return x;
    }

    @Override
    public String toString() {
        return "[" + x + ']';
    }
}
