package pl.maxmati.po.automaton.automaton.factories;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.exceptions.AutomatonNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by maxmati on 12/28/15.
 */
public abstract class AutomatonFactory {
    static private Map<String, AutomatonFactory> availableAutomatons = new HashMap<>();

    static {
        availableAutomatons.put("Game of Life", new GameOfLifeFactory());
    }

    static public Automaton createAutomaton(String name, Map<String, Object> params){
        AutomatonFactory automatonClass = availableAutomatons.get(name);
        if(automatonClass == null) throw new AutomatonNotFoundException(name);

        return automatonClass.newAutomaton(params);
    }

    public static Map<String, Object> getParams(String name) {
        AutomatonFactory automatonClass = availableAutomatons.get(name);
        if(automatonClass == null) throw new AutomatonNotFoundException(name);

        return automatonClass.getParams();
    }

    public static Set<String> getAvailableAutomatons() {
        return availableAutomatons.keySet();
    }

    protected abstract Map<String,Object> getParams();
    protected abstract Automaton newAutomaton(Map<String, Object> params);

}
