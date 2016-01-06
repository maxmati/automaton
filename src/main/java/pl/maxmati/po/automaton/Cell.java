package pl.maxmati.po.automaton;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.state.CellState;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Merge {@link CellState} and {@link CellCoordinates} into complete cell.
 *
 */
public class Cell {
    public final CellState state;
    public final CellCoordinates cords;

    /**
     * Creates new immutable cell.
     *
     * @param coordinates Coordinates of cell.
     * @param state State of cell.
     */
    public Cell(CellCoordinates coordinates, CellState state) {
        this.state = state;
        this.cords = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        //noinspection SimplifiableIfStatement
        if (state != null ? !state.equals(cell.state) : cell.state != null) return false;
        return !(cords != null ? !cords.equals(cell.cords) : cell.cords != null);

    }

    @Override
    public int hashCode() {
        int result = state != null ? state.hashCode() : 0;
        result = 31 * result + (cords != null ? cords.hashCode() : 0);
        return result;
    }
}
