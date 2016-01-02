package pl.maxmati.po.automaton.automaton.factories;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.WireWorld;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maxmati on 12/30/15.
 */
public class WireWorldFactory extends AutomatonFactory{

    final Map<String, Object> defaultParams = new LinkedHashMap<>();

    public WireWorldFactory() {
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
        return new WireWorld( (Integer) params.get("Width"), (Integer) params.get("Height"), (Boolean) params.get("Wrap"));
    }
}
