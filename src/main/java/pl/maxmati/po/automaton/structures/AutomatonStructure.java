package pl.maxmati.po.automaton.structures;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.state.CellState;

import java.util.Map;

/**
 * @author maxmati
 * @version 1.0
 * <br>
 *
 * Represents structure which is set of cells.
 *
 */
public class AutomatonStructure {
    private final String path;
    private final String name;
    private Map<CellCoordinates, CellState> data = null;
    private boolean loaded = false;

    /**
     * @see StructureLoader
     */

    AutomatonStructure(String name, String path) {
        this.name = name;
        this.path = path;
    }

    /**
     * @return Name of structure.
     */
    public String getName() {
        return name;
    }

    /**
     * Load structure from file if required and returns it's data.
     *
     * @return Map of {@link CellCoordinates} and {@link CellState} of which structure comprises.
     */
    public Map<CellCoordinates, CellState> getData() {
        if (!loaded) load();
        return data;
    }

    private void load() {
        data = StructureLoader.loadStructure(path);
        loaded = true;
    }

    @Override
    public String toString() {
        return name;
    }
}
