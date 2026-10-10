package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class TickCommand extends Command {

    public TickCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        return ctx.engine().nextTick();
    }
}