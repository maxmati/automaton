package pl.maxmati.po.automaton.gui.commands;

import java.util.Map;

/**
 * Created by maxmati on 12/28/15.
 */
public class CreateAutomatonCommand extends BoardAdapterCommand {
    private String type;
    private Map<String, Object> params;

    public CreateAutomatonCommand(String type, Map<String, Object> params) {
        this.type = type;
        this.params = params;
    }

    @Override
    public void execute() {
        adapter.createAutomaton(type, params);
    }
}
