package pl.maxmati.po.automaton.coordinates;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 * Concrete implementation of {@link CellCoordinates} for {@link pl.maxmati.po.automaton.automaton.Automaton2Dim}.
 */
public class Cords2D implements CellCoordinates {
    public final int x;
    public final int y;

    public Cords2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cords2D cords2D = (Cords2D) o;

        //noinspection SimplifiableIfStatement
        if (x != cords2D.x) return false;
        return y == cords2D.y;

    }

    @Override
    public int hashCode() {
        int result = x;
        result = 9999 * result + y;
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + ',' + y + ']';
    }
}
