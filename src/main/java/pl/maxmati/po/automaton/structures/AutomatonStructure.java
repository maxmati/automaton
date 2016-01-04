package pl.maxmati.po.automaton.structures;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.state.CellState;

import java.util.Map;

/**
 * Created by maxmati on 1/4/16.
 */
public class AutomatonStructure {
    private final String path;
    private final String name;
    private Map<? extends  CellCoordinates, ? extends CellState> data = null;
    private boolean loaded = false;

    public AutomatonStructure(String name, String path) {
        this.name = name;
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public Map<? extends  CellCoordinates, ? extends CellState> getData() {
        if (!loaded) load();
        return data;
    }

    private void load() {
        data = StructureLoader.loadStructure(path);
        loaded = true;
    }
}
