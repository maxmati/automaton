package pl.maxmati.po.automaton.gui.view;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.commands.FinishInsertingStructureCommand;
import pl.maxmati.po.automaton.gui.commands.SwitchCellCommand;
import pl.maxmati.po.automaton.gui.controller.BoardAdapter;
import pl.maxmati.po.automaton.gui.view.cell.CellRenderer;

import java.util.*;

/**
 * Created by maxmati on 12/17/15.
 */
public class Board extends StackPane implements Observer{

    private static final int REDRAWING_RATE_MS = 16;//~60FPS

    private final BoardAdapter adapter;

    private Timer resizeTimer = new Timer("Board redraw timer");
    private TimerTask resizeTask = null;

    private double cellHeight;
    private double cellWidth;

    private boolean dirty = true;

    private Point2D mousePosition = null;
    private List<BoardAdapter.RenderableCell> insertingStructure = null;

    private CellRenderer[][] cache;

    private Canvas mainLayer = new Canvas();
    private Canvas insertingLayer = new Canvas();
    private Canvas borderCanvas = new Canvas();

    public Board(BoardAdapter adapter){
        this.adapter = adapter;
        adapter.addObserver(this);

        setMinSize(0, 0);

        initCanvas(mainLayer);
        initCanvas(insertingLayer);
        initCanvas(borderCanvas);

        initCache(adapter);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                final double cellHeight = Board.this.getHeight()/adapter.getHeight();
                final double cellWidth = Board.this.getWidth()/adapter.getWidth();

                final int x = (int) (mouseEvent.getX() / cellWidth);
                final int y = (int) (mouseEvent.getY() / cellHeight);

                if(insertingStructure == null)
                    adapter.dispatchCommand(new SwitchCellCommand(new Cords2D(x,y)));
                else {
                    adapter.dispatchCommand(new FinishInsertingStructureCommand(new Cords2D(x, y)));
                }
            }
        });

        this.addEventHandler(MouseEvent.MOUSE_ENTERED, this::updateMousePosition);
        this.addEventHandler(MouseEvent.MOUSE_MOVED, this::updateMousePosition);
        this.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> mousePosition = null);


        startRedrawingTask();
    }

    private Point2D updateMousePosition(MouseEvent mouseEvent) {
        return mousePosition = new Point2D(mouseEvent.getX(), mouseEvent.getY());
    }

    private void startRedrawingTask() {
        TimerTask redrawTask = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> draw());
            }
        };
        resizeTimer.scheduleAtFixedRate(redrawTask, REDRAWING_RATE_MS, REDRAWING_RATE_MS);
    }

    private void initCache(BoardAdapter adapter) {
        cache = new CellRenderer[adapter.getWidth()][adapter.getHeight()];
    }

    private void initCanvas(Canvas canvas) {
        this.getChildren().add(canvas);
        canvas.heightProperty().bind(this.heightProperty());
        canvas.widthProperty().bind(this.widthProperty());
    }

    public void draw(){
        draw(false);
    }

    public void draw(boolean discardCache){
        drawInsertingLayer();

        discardCache = drawMainLayer(discardCache);

        if(discardCache)
            drawBorders();

    }

    private boolean drawMainLayer(boolean discardCache) {
        if(!dirty) return discardCache;

        GraphicsContext context = mainLayer.getGraphicsContext2D();

        if(cache.length != adapter.getWidth() || cache[0].length != adapter.getHeight() ) {
            initCache(adapter);
            discardCache = true;
        }

        dirty = false;

        cellHeight = this.getHeight()/adapter.getHeight();
        cellWidth = this.getWidth()/adapter.getWidth();

        for(BoardAdapter.RenderableCell renderableCell : adapter){
            if(discardCache)
                drawCell(renderableCell, context, true);
            else
                drawCellIfNeeded(renderableCell, context);
        }
        return discardCache;
    }

    private void drawBorders() {
        GraphicsContext context = borderCanvas.getGraphicsContext2D();

        context.clearRect(0,0,insertingLayer.getWidth(), insertingLayer.getHeight());

        context.setStroke(Color.GRAY);

        for (int i = 0; i < adapter.getHeight(); i++) {
            context.strokeLine(0,cellHeight*i, this.getWidth(), cellHeight*i);
        }

        for (int i = 0; i < adapter.getWidth(); i++) {
            context.strokeLine(cellWidth*i, 0, cellWidth*i, this.getHeight());
        }
    }

    private void drawInsertingLayer() {
        GraphicsContext context = insertingLayer.getGraphicsContext2D();

        context.clearRect(0,0,insertingLayer.getWidth(), insertingLayer.getHeight());

        if(insertingStructure != null && mousePosition != null)
            for(BoardAdapter.RenderableCell cell: insertingStructure)
                drawCell(moveByMouse(cell), context, false);
    }

    private BoardAdapter.RenderableCell moveByMouse(BoardAdapter.RenderableCell cell) {
        Cords2D oldCords = cell.getPosition();
        Cords2D newCords = new Cords2D(
                oldCords.x + (int) (mousePosition.getX()/cellWidth),
                oldCords.y + (int) (mousePosition.getY()/cellHeight)
        );

        return new BoardAdapter.RenderableCell(cell.getRenderer(), newCords);
    }

    private void drawCellIfNeeded(BoardAdapter.RenderableCell renderableCell, GraphicsContext context) {
        CellRenderer newRenderer = renderableCell.getRenderer();
        Cords2D position = renderableCell.getPosition();

        final CellRenderer oldRenderer = cache[position.x][position.y];
        if(newRenderer != oldRenderer) {
            drawCell(renderableCell, context, true);
        }
    }

    private void drawCell(BoardAdapter.RenderableCell renderableCell, GraphicsContext context, boolean storeCache) {
        CellRenderer newRenderer = renderableCell.getRenderer();
        Cords2D position = renderableCell.getPosition();
        newRenderer.render(context, position, cellWidth, cellHeight);
        if(storeCache)
            cache[position.x][position.y] = newRenderer;
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
                Platform.runLater(() -> draw(true));
            }
        };
        resizeTimer.schedule( resizeTask, 200);
    }

    @Override
    public void update(Observable observable, Object o) {
        dirty = true;
    }

    public void startInsertingStructure(List<BoardAdapter.RenderableCell> structure) {
        insertingStructure = structure;
    }

    public StackPane getRoot() {
        return this;
    }

    public void stopInsertingStructure() {
        insertingStructure = null;
    }
}
