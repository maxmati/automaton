package pl.maxmati.po.automaton.gui.controller;

import pl.maxmati.po.automaton.gui.commands.TickCommand;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by maxmati on 12/24/15.
 */
public class Ticker{
    private final BoardAdapter adapter;
    private final Timer timer = new Timer("Ticker-timer");
    private TimerTask task = null;
    private boolean active;
    private int rate = 40;

    public Ticker(BoardAdapter adapter) {
        this.adapter = adapter;
    }

    private TimerTask createTickTask() {
        return new TimerTask() {
            @Override
            public void run() {
                adapter.dispatchCommand(new TickCommand());
            }
        };
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void start() {
        if(task != null)
            task.cancel();
        task = createTickTask();
        timer.scheduleAtFixedRate(task, rate, rate);
        active = true;
    }

    public synchronized void stop() {
        active = false;
        task.cancel();
        task = null;
    }

    public synchronized int getRate() {
        return rate;
    }

    public synchronized void setRate(int rate) {
        this.rate = rate;
        if(active) start();
    }
}
