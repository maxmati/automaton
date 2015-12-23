package pl.maxmati.po.automaton.gui;

import pl.maxmati.po.automaton.gui.commands.Command;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by maxmati on 12/17/15.
 */
public class CommandQueue {

    final Queue<Command> queue = new LinkedList<>();

    public void dispatchCommand(Command c){
        queue.add(c);
    }

    public void processCommands(){
        while (!queue.isEmpty()) {
            queue.poll().execute();
        }
    }
}
