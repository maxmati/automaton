package pl.maxmati.po.automaton.automaton.factories;

import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.GameOfLife;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by maxmati on 12/29/15.
 */
public class GameOfLifeFactory extends AutomatonFactory {

    final Map<String, Object> defaultParams = new LinkedHashMap<>();

    public GameOfLifeFactory() {
        defaultParams.put("Width", 5);
        defaultParams.put("Height", 10);
        defaultParams.put("Wrap", false);
        defaultParams.put("Rule", new GameOfLife.Rule("3/23"));
    }

    @Override
    protected Map<String, Object> getParams() {
        return defaultParams;
    }

    @Override
    protected Automaton newAutomaton(Map<String, Object> params) {
        return new GameOfLife(
                (Integer) params.get("Width"),
                (Integer) params.get("Height"),
                (Boolean) params.get("Wrap"),
                new GameOfLife.Rule((String) params.get("Rule"))
        );
    }
}
