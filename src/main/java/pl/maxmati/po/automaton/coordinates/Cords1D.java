package pl.maxmati.po.automaton.coordinates;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellCoordinates} for {@link pl.maxmati.po.automaton.automaton.Automaton1Dim}.
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
