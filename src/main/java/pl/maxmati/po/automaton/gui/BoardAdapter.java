package pl.maxmati.po.automaton.gui;

import javafx.geometry.Point2D;
import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.factories.AutomatonFactory;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.gui.commands.BoardAdapterCommand;
import pl.maxmati.po.automaton.gui.translators.color.CellRendererFactory;
import pl.maxmati.po.automaton.gui.translators.cords.PositionTranslator;
import pl.maxmati.po.automaton.gui.translators.cords.PositionTranslatorFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/**
 * Created by maxmati on 12/17/15.
 */
public class BoardAdapter extends Observable implements Iterable<BoardAdapter.Cell> {
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
    public Iterator<Cell> iterator() {
        return new BoardIterator(automaton.iterator(), CellRendererFactory.createFactory(automaton), PositionTranslatorFactory.create(automaton));
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

    public void createAutomaton(String type, Map<String, Object> params) {
        automaton = AutomatonFactory.createAutomaton(type, params);
        width = (Integer) params.get("Width");
        height = (Integer) params.get("Height");
        setChanged();
        notifyObservers();
    }

    public class BoardIterator implements Iterator<Cell> {

        private final Iterator<pl.maxmati.po.automaton.Cell> iterator;
        private final CellRendererFactory cellRendererFactory;
        private final PositionTranslator positionTranslator;

        public BoardIterator(Iterator<pl.maxmati.po.automaton.Cell> iterator, CellRendererFactory cellRendererFactory, PositionTranslator positionTranslator) {
            this.iterator = iterator;
            this.cellRendererFactory = cellRendererFactory;
            this.positionTranslator = positionTranslator;
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext();
        }

        @Override
        public Cell next() {
            final pl.maxmati.po.automaton.Cell cell = iterator.next();
            return new Cell(cellRendererFactory.create(cell.state), positionTranslator.translate(cell.cords)) ;
        }
    }

    public static class Cell {
        private final CellRenderer renderer;
        private final Point2D position;

        public Cell(CellRenderer renderer, Point2D position) {
            this.renderer = renderer;
            this.position = position;
        }

        public CellRenderer getRenderer() {
            return renderer;
        }

        public Point2D getPosition() {
            return position;
        }
    }

}
