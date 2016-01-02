package pl.maxmati.po.automaton.automaton.factories;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.QuadLife;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maxmati on 1/2/16.
 */
public class QuadLifeFactory extends AutomatonFactory {
    final Map<String, Object> defaultParams = new LinkedHashMap<>();

    public QuadLifeFactory() {
        defaultParams.put("Width", 5);
        defaultParams.put("Height", 10);
        defaultParams.put("Wrap", false);
    }

    @Override
    protected Map<String, Object> getParams() {
        return defaultParams;
    }

    @Override
    protected Automaton newAutomaton(Map<String, Object> params) {
        return new QuadLife( (Integer) params.get("Width"), (Integer) params.get("Height"), (Boolean) params.get("Wrap"));
    }
}
