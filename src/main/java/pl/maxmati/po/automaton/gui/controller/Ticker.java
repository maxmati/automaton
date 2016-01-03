package pl.maxmati.po.automaton.gui.controller;

import pl.maxmati.po.automaton.gui.commands.TickCommand;

/**
 * Created by maxmati on 12/24/15.
 */
public class Ticker implements Runnable{
    private final BoardAdapter adapter;
    private boolean active;
    private int rate = 10;

    public Ticker(BoardAdapter adapter) {
        this.adapter = adapter;
        Thread t = new Thread(this);
        t.start();
    }

    public synchronized boolean isActive() {
        return active;
    }

    public synchronized void start() {
        active = true;
        this.notify();
    }

    public synchronized void stop() {
        active = false;
    }

    public synchronized int getRate() {
        return rate;
    }

    public synchronized void setRate(int rate) {
        System.out.println(rate);
        this.rate = rate;
    }

    @Override
    public void run() {
        //noinspection InfiniteLoopStatement
        while (true) {
            synchronized (this) {
                try {
                    this.wait(rate);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if(active)
                adapter.dispatchCommand(new TickCommand());
        }
    }
}
