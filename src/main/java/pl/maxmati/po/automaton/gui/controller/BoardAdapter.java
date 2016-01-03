package pl.maxmati.po.automaton.gui.controller;

import javafx.geometry.Point2D;
import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.Automaton1Dim;
import pl.maxmati.po.automaton.automaton.factories.AutomatonFactory;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.commands.BoardAdapterCommand;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.factiories.CellRendererFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

/**
 * Created by maxmati on 12/17/15.
 */
public class BoardAdapter extends Observable implements Iterable<BoardAdapter.Cell> {
    private int height;
    private int width;
    private AutomatonsHistory automatons;
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

        initializeHistory(automaton);
    }

    @Override
    public Iterator<Cell> iterator() {
        return new BoardIterator(automatons);
    }

    public void dispatchCommand(BoardAdapterCommand command){
        command.setAdapter(this);
        queue.dispatchCommand(command);
        queue.processCommands();//TODO: REMOVE ASYNC
    }

    public void switchCell(Cords2D cords) {
        Automaton automaton = automatons.getLast();
        if (automaton instanceof Automaton1Dim) {
            if (cords.y != automatons.size() - 1)
                return;
            automaton.switchCell(new Cords1D(cords.x));
        } else {
            automaton.switchCell(cords);
        }
        setChanged();
        notifyObservers();
    }

    public void tickAutomaton() {
        automatons.add(automatons.getLast().nextState());
        setChanged();
        notifyObservers();
    }

    public void createAutomaton(String type, Map<String, Object> params) {
        Automaton automaton = AutomatonFactory.createAutomaton(type, params);
        width = (Integer) params.get("Width");
        height = (Integer) params.get("Height");

        initializeHistory(automaton);

        setChanged();
        notifyObservers();
    }

    private void initializeHistory(Automaton automaton) {
        int historySize;
        if (automaton instanceof Automaton1Dim)
            historySize = height;
        else
            historySize = 1;

        automatons = new AutomatonsHistory(height);

        for (int i = 1; i < historySize; i++) {
            automatons.add(automaton.createNewEmpty());
        }
        automatons.add(automaton);
    }

    public class BoardIterator implements Iterator<Cell> {

        private int nextAutomatonIndex = 1;
        private Iterator<pl.maxmati.po.automaton.Cell> iterator;
        private final AutomatonsHistory automatons;
        private final CellRendererFactory cellRendererFactory;

        public BoardIterator(AutomatonsHistory automatons) {
            this.automatons = automatons;
            this.iterator = automatons.get(0).iterator();
            this.cellRendererFactory = CellRendererFactory.createFactory(automatons.get(0));
        }

        @Override
        public boolean hasNext() {
            return iterator.hasNext() || nextAutomatonIndex < automatons.size();
        }

        @Override
        public Cell next() {
            pl.maxmati.po.automaton.Cell cell;
            if(iterator.hasNext())
                cell = iterator.next();
            else if(hasNext()){
                iterator = automatons.get(nextAutomatonIndex++).iterator();
                cell = iterator.next();
            } else
                return null;

            Point2D position = getCords(cell.cords);

            return new Cell(cellRendererFactory.create(cell.state), position) ;
        }

        private Point2D getCords(CellCoordinates cords) {
            Point2D position;
            if(cords instanceof Cords1D)
                position = new Point2D( ((Cords1D)cords).x, nextAutomatonIndex - 1);
            else
                position = new Point2D( ((Cords2D)cords).x, ((Cords2D)cords).y);
            return position;
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
