package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class InfoCommand extends Command {

    public InfoCommand(List<String> words) {
        super(words);
    }

    
    @Override
    public String execute(Context ctx) {
        // If user typed only "info", default to "all"
        String target = (words.size() < 2) ? "all" : words.get(1);
        return ctx.engine().info(target);
    }
}