package pl.maxmati.po.automaton.automaton.factories;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.Automaton1Dim;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maxmati on 1/2/16.
 */
public class Automaton1DimFactory extends AutomatonFactory {
    final Map<String, Object> defaultParams = new LinkedHashMap<>();

    public Automaton1DimFactory() {
        defaultParams.put("Width", 5);
        defaultParams.put("Height", 10);
        defaultParams.put("Wrap", false);
        defaultParams.put("Rule", new Automaton1Dim.Rule(30));
    }
    @Override
    protected Map<String, Object> getParams() {
        return defaultParams;
    }

    @Override
    protected Automaton newAutomaton(Map<String, Object> params) {
        return new Automaton1Dim( (Integer) params.get("Width"), new Automaton1Dim.Rule((Integer) params.get("Rule")));
    }
}
