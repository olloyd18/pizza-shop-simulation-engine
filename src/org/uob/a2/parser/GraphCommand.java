package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class GraphCommand extends Command {

    public GraphCommand(List<String> words) {
        super(words);
    }

    
    @Override
    public String execute(Context ctx) {
        if (words.size() < 2) return "Missing resource name.";
        return ctx.engine().graph(words.get(1));
    }
}