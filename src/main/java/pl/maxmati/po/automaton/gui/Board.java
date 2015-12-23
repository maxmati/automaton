package pl.maxmati.po.automaton.gui;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.CacheHint;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pl.maxmati.po.automaton.coordinates.Cords2D;
import pl.maxmati.po.automaton.gui.commands.SwitchCellCommand;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maxmati on 12/17/15.
 */
public class Board extends Canvas implements Observer{

    private final BoardAdapter adapter;

    private Timer resizeTimer = new Timer();
    private TimerTask resizeTask = null;
    private double cellHeight;
    private double cellWidth;
    private GraphicsContext context;

    public Board(BoardAdapter adapter){

        setCache(true);
        setCacheHint(CacheHint.SPEED);

        this.adapter = adapter;
        adapter.addObserver(this);

        context = this.getGraphicsContext2D();
//        context.setGlobalBlendMode(BlendMode.);

        this.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if(mouseEvent.getButton() == MouseButton.PRIMARY){
                final double cellHeight = Board.this.getHeight()/adapter.getHeight();
                final double cellWidth = Board.this.getWidth()/adapter.getWidth();

                final int x = (int) (mouseEvent.getX() / cellWidth);
                final int y = (int) (mouseEvent.getY() / cellHeight);

                adapter.dispatchCommand(new SwitchCellCommand(new Cords2D(x,y)));
            }
        });
    }



    public void draw(){
        cellHeight = this.getHeight()/adapter.getHeight();
        cellWidth = this.getWidth()/adapter.getWidth();

        context.setStroke(Color.GRAY);

        for(BoardAdapter.ColorCell cell: adapter){
            context.setFill(cell.getColor());
            final double x = cell.getPosition().getX() * cellWidth;
            final double y = cell.getPosition().getY() * cellHeight;
            context.fillRect(x, y, cellWidth, cellHeight);
//            context.strokeRect(x, y, cellWidth, cellHeight);
        }

        for (int i = 0; i < adapter.getHeight(); i++) {
            context.strokeLine(0,cellHeight*i, getWidth(), cellHeight*i);
        }

        for (int i = 0; i < adapter.getHeight(); i++) {
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
        startRedrawTask();
    }

    private void startRedrawTask() {
        if(resizeTask != null){
            resizeTask.cancel();
        }

        resizeTask = new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };
        resizeTimer.schedule( resizeTask, 200);
    }


    @Override
    public void update(Observable observable, Object o) {
        draw();
    }
}
