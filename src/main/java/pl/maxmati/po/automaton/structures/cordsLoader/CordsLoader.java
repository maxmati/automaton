package pl.maxmati.po.automaton.structures.cordsLoader;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;

import java.util.Scanner;

/**
 * Created by maxmati on 1/4/16.
 */
public interface CordsLoader {
    void initSize(Scanner scanner);

    boolean hasNext();

    CellCoordinates next();
}
