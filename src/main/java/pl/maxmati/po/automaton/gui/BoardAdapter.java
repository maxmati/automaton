package pl.maxmati.po.automaton.gui;

import javafx.geometry.Point2D;
import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.ColorTranslator.ColorTranslator;
import pl.maxmati.po.automaton.gui.ColorTranslator.ColorTranslatorFactory;

import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.gui.CordsTranslator.PositionTranslator;
import pl.maxmati.po.automaton.gui.CordsTranslator.PositionTranslatorFactory;
import pl.maxmati.po.automaton.gui.commands.BoardAdapterCommand;
import pl.maxmati.po.automaton.gui.commands.Command;

import java.util.Iterator;
import java.util.Observable;

/**
 * Created by maxmati on 12/17/15.
 */
public class BoardAdapter extends Observable implements Iterable<BoardAdapter.ColorCell> {
    private int height;
    private int width;
    private Automaton automaton;
    private CommandQueue queue;

    public int getHeight(){
        return height;
    }

    public int getWidth(){
        return width;
    }

    public BoardAdapter(CommandQueue queue, int height, int width, Automaton automaton) {
        this.queue = queue;
        this.height = height;
        this.width = width;
        this.automaton = automaton;
    }

    @Override
    public Iterator<ColorCell> iterator() {
        return new BoardIterator(automaton.iterator(), ColorTranslatorFactory.create(automaton), PositionTranslatorFactory.create(automaton));
    }

    public void dispatchCommand(BoardAdapterCommand command){
        command.setAdapter(this);
        queue.dispatchCommand(command);
        queue.processCommands();//TODO: REMOVE ASYNC
    }

    public void switchCell(CellCoordinates cords) {
        automaton.switchCell(cords);
        setChanged();
        notifyObservers();
    }

    public void tickAutomaton() {
        automaton = automaton.nextState();
        setChanged();
        notifyObservers();
    }

    public class BoardIterator implements Iterator<ColorCell> {

        private final Iterator<Cell> iterator;
        private final ColorTranslator colorTranslator;
        private final PositionTranslator positionTranslator;

        public BoardIterator(Iterator<Cell> iterator, ColorTranslator colorTranslator, PositionTranslator positionTranslator) {
            this.iterator = iterator;
            this.colorTranslator = colorTranslator;
            this.positionTranslator = positionTranslator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public ColorCell next() {
            final Cell cell = iterator.next();
            return new ColorCell(colorTranslator.translate(cell.state), positionTranslator.translate(cell.cords)) ;
        }
    }

    public static class ColorCell {
        private final Color color;
        private final Point2D position;

        public ColorCell(Color color, Point2D position) {
            this.color = color;
            this.position = position;
        }

        public Color getColor() {
            return color;
        }

        public Point2D getPosition() {
            return position;
        }
    }

}
