package org.uob.a2.parser;

import java.util.List;
import org.uob.a2.engine.Context;

public class QuitCommand extends Command {

    public QuitCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        return "Quitting simulation.";
    }
}
