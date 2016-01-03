package pl.maxmati.po.automaton.gui.controller;

import pl.maxmati.po.automaton.automaton.Automaton;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxmati on 1/2/16.
 */
public class AutomatonsHistory {
    private List<Automaton> automatons;
    private int size;

    public AutomatonsHistory(int size) {
        automatons = new ArrayList<>(size + 1);
        this.size = size;
    }

    public void add(Automaton a){
        automatons.add(a);
        if(automatons.size() > size)
            automatons.remove(0);
    }

    public Automaton get(int i){
        return automatons.get(i);
    }

    public Automaton getLast() {
        return get(automatons.size() - 1);
    }

    public int size() {
        return automatons.size();
    }
}
