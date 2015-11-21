package pl.maxmati.po.automaton.state.factory;

import com.google.common.base.Objects;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.state.CellState;

/**
 * Created by maxmati on 11/20/15
 */
public class UniformStateFactory implements CellStateFactory {
    final private CellState state;

    public UniformStateFactory(CellState state) {
        this.state = state;
    }

    @Override
    public CellState initialState(CellCoordinates cords) {
        return state;
    }

    @Override
    public String toString() {
        return "UniformStateFactory{" + state + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UniformStateFactory)) return false;
        UniformStateFactory that = (UniformStateFactory) o;
        return Objects.equal(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(state);
    }
}
