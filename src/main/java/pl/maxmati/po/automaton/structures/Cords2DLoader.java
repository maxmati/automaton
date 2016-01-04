package pl.maxmati.po.automaton.structures;

import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;

import java.util.Scanner;

/**
 * Created by maxmati on 1/4/16.
 */
public class Cords2DLoader implements CordsLoader {
    int x = 0;
    int y = 0;
    int maxX;
    int maxY;

    @Override
    public void initSize(Scanner scanner) {
        maxX = scanner.nextInt();
        maxY = scanner.nextInt();
    }

    @Override
    public boolean hasNext() {
        return x < maxX && y < maxY;
    }

    @Override
    public CellCoordinates next() {
        Cords2D cord = new Cords2D(x, y);

        moveToNextCord();

        return cord;
    }

    private void moveToNextCord() {
        ++x;
        if(x >= maxX){
            ++y;
            x = 0;
        }
    }
}
