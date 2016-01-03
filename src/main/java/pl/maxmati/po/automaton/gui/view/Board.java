package pl.maxmati.po.automaton.gui.view;

import javafx.application.Platform;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.commands.SwitchCellCommand;
import pl.maxmati.po.automaton.gui.controller.BoardAdapter;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maxmati on 12/17/15.
 */
public class Board extends Canvas implements Observer{

    private final BoardAdapter adapter;

    private Timer resizeTimer = new Timer("Board redraw timer");
    private TimerTask resizeTask = null;
    private double cellHeight;
    private double cellWidth;
    private GraphicsContext context;
    private boolean dirty = true;
    private CellRenderer[][] old;

    public Board(BoardAdapter adapter){

        setCache(true);
        setCacheHint(CacheHint.SPEED);

        this.adapter = adapter;
        adapter.addObserver(this);

        old = new CellRenderer[adapter.getWidth()][adapter.getHeight()];

        context = this.getGraphicsContext2D();

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                final double cellHeight = Board.this.getHeight()/adapter.getHeight();
                final double cellWidth = Board.this.getWidth()/adapter.getWidth();

                final int x = (int) (mouseEvent.getX() / cellWidth);
                final int y = (int) (mouseEvent.getY() / cellHeight);

                adapter.dispatchCommand(new SwitchCellCommand(new Cords2D(x,y)));
            }
        });

        TimerTask redrawTask = new TimerTask() {
            @Override
            public void run() {
                if (dirty) Platform.runLater(() -> draw());
            }
        };
        resizeTimer.scheduleAtFixedRate(redrawTask,35,35);
    }

    public void draw(){
        if(!dirty) return;

        if(old.length != adapter.getWidth() || old[0].length != adapter.getHeight() ) {
            old = new CellRenderer[adapter.getWidth()][adapter.getHeight()];
            drawAll();
            return;
        }

        dirty = false;

        cellHeight = this.getHeight()/adapter.getHeight();
        cellWidth = this.getWidth()/adapter.getWidth();

        context.setStroke(Color.GRAY);

        for(BoardAdapter.RenderableCell renderableCell : adapter){
            drawCellIfNeeded(renderableCell);
        }

        for (int i = 0; i < adapter.getHeight(); i++) {
            context.strokeLine(0,cellHeight*i, getWidth(), cellHeight*i);
        }

        for (int i = 0; i < adapter.getWidth(); i++) {
            context.strokeLine(cellWidth*i, 0, cellWidth*i, getHeight());
        }

    }

    private void drawCellIfNeeded(BoardAdapter.RenderableCell renderableCell) {
        CellRenderer newRenderer = renderableCell.getRenderer();
        final int x = renderableCell.getPosition().x;
        final int y = renderableCell.getPosition().y;
        final CellRenderer oldRenderer = old[x][y];
        if(newRenderer != oldRenderer) {
            renderableCell.getRenderer().render(context, renderableCell.getPosition(), cellWidth, cellHeight);
            old[x][y] = newRenderer;
        }
    }

    public void drawAll(){
        dirty = false;

        cellHeight = this.getHeight()/adapter.getHeight();
        cellWidth = this.getWidth()/adapter.getWidth();

        context.setStroke(Color.GRAY);

        for(BoardAdapter.RenderableCell renderableCell : adapter){
            renderableCell.getRenderer().render(context, renderableCell.getPosition(), cellWidth, cellHeight);
        }

        for (int i = 0; i < adapter.getHeight(); i++) {
            context.strokeLine(0,cellHeight*i, getWidth(), cellHeight*i);
        }

        for (int i = 0; i < adapter.getWidth(); i++) {
            context.strokeLine(cellWidth*i, 0, cellWidth*i, getHeight());
        }

    }

    @Override
    public double minHeight(double width)
    {
        return 64;
    }

    @Override
    public double maxHeight(double width)
    {
        return 10000;
    }

    @Override
    public double prefHeight(double width)
    {
        return minHeight(width);
    }

    @Override
    public double minWidth(double height)
    {
        return 0;
    }

    @Override
    public double maxWidth(double height)
    {
        return 10000;
    }

    @Override
    public boolean isResizable()
    {
        return true;
    }

    @Override
    public void resize(double width, double height)
    {
        super.setWidth(width);
        super.setHeight(height);
        dirty = true;
        startRedrawTask();
    }

    private void startRedrawTask() {
        if(resizeTask != null){
            resizeTask.cancel();
        }

        resizeTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> drawAll());
            }
        };
        resizeTimer.schedule( resizeTask, 200);
    }


    @Override
    public void update(Observable observable, Object o) {
        dirty = true;
    }
}
