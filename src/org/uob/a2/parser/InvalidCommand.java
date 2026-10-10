package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class InvalidCommand extends Command {

    public InvalidCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        return "Invalid command: " + words.get(0);
    }
}