package pl.maxmati.po.automaton.gui.controller;

import pl.maxmati.po.automaton.Cell;
import pl.maxmati.po.automaton.automaton.Automaton;
import pl.maxmati.po.automaton.automaton.Automaton1Dim;
import pl.maxmati.po.automaton.automaton.factories.AutomatonFactory;
import pl.maxmati.po.automaton.coordinates.CellCoordinates;
import pl.maxmati.po.automaton.coordinates.Cords1D;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.commands.BoardAdapterCommand;
import pl.maxmati.po.automaton.gui.view.Board;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;
import pl.maxmati.po.automaton.gui.view.cell.factiories.CellRendererFactory;
import pl.maxmati.po.automaton.state.CellState;
import pl.maxmati.po.automaton.structures.AutomatonStructure;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.stream.Collectors;

/**
 * Created by maxmati on 12/17/15.
 */
public class BoardAdapter extends Observable implements Iterable<BoardAdapter.RenderableCell> {
    private int height;
    private int width;
    private AutomatonsHistory automatons;
    private CommandQueue queue;
    private String automatonName = "Game of Life";
    private Board board = null;
    private AutomatonStructure insertingStructure = null;

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
    public synchronized Iterator<RenderableCell> iterator() {
        return new BoardIterator(automatons);
    }

    public void dispatchCommand(BoardAdapterCommand command){
        command.setAdapter(this);
        queue.dispatchCommand(command);
        queue.processCommands();//TODO: REMOVE ASYNC
    }

    public synchronized void switchCell(Cords2D cords) {
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

    public synchronized void tickAutomaton() {
        automatons.add(automatons.getLast().nextState());
        setChanged();
        notifyObservers();
    }

    public synchronized void createAutomaton(String name, Map<String, Object> params) {
        automatonName = name;
        Automaton automaton = AutomatonFactory.createAutomaton(name, params);
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

        automatons = new AutomatonsHistory(historySize);

        for (int i = 1; i < historySize; i++) {
            automatons.add(automaton.createNewEmpty());
        }
        automatons.add(automaton);
    }

    public String getAutomatonName() {
        return automatonName;
    }

    public void startInsertingStructure(AutomatonStructure structure) {
        this.insertingStructure = structure;
        final CellRendererFactory cellRendererFactory = CellRendererFactory.createFactory(automatons.getLast());

        List<RenderableCell> renderableStructure = structure.getData().entrySet().stream()
                .map(entry -> new RenderableCell(
                        cellRendererFactory.create(entry.getValue()),
                        (Cords2D) entry.getKey())
                )
                .collect(Collectors.toList());

        board.startInsertingStructure(renderableStructure);
    }

    public synchronized void finishInsertingStructure(Cords2D position) {
        Map<CellCoordinates, CellState> data = insertingStructure.getData().entrySet().stream().collect(Collectors.toMap(
            (e) ->{
                Cords2D old = (Cords2D) e.getKey();
                return new Cords2D(old.x + position.x, old.y + position.y);
            },
            Map.Entry::getValue
        ));
        automatons.getLast().insertStructure(data);
        setChanged();
        notifyObservers();
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void cancelInsertingStructure() {
        board.stopInsertingStructure();
        insertingStructure = null;
    }

    public static class BoardIterator implements Iterator<RenderableCell> {

        private int nextAutomatonIndex = 1;
        private Iterator<pl.maxmati.po.automaton.Cell> iterator;
        private final AutomatonsHistory automatons;
        private final CellRendererFactory cellRendererFactory;
        private RenderableCell renderableCell;

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
        public RenderableCell next() {
            Cell cell;
            if (iterator.hasNext())
                cell = iterator.next();
            else if (hasNext()) {
                iterator = automatons.get(nextAutomatonIndex++).iterator();
                cell = iterator.next();
            } else
                return null;

            Cords2D position = getCords(cell.cords);

            if (this.renderableCell == null)
                this.renderableCell = new RenderableCell(cellRendererFactory.create(cell.state), position);
            else {
                this.renderableCell.setRenderer(cellRendererFactory.create(cell.state));
                this.renderableCell.setPosition(position);
            }

            return this.renderableCell;
        }

        private Cords2D getCords(CellCoordinates cords) {
            Cords2D position;
            if(cords instanceof Cords1D)
                position = new Cords2D( ((Cords1D)cords).x, nextAutomatonIndex - 1);
            else
                position = (Cords2D) cords;
            return position;
        }
    }

    public static class RenderableCell {
        private CellRenderer renderer;
        private Cords2D position;

        public RenderableCell(CellRenderer renderer, Cords2D position) {
            this.renderer = renderer;
            this.position = position;
        }

        public CellRenderer getRenderer() {
            return renderer;
        }

        public Cords2D getPosition() {
            return position;
        }

        public void setRenderer(CellRenderer renderer) {
            this.renderer = renderer;
        }

        public void setPosition(Cords2D position) {
            this.position = position;
        }
    }

}
