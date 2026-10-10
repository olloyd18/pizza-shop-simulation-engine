package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class BuildCommand extends Command {

    public BuildCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        // build <entity>
        if (words.size() < 2) return "No entity specified.";
        String entity = words.get(1);
        return ctx.engine().build(entity);
    }
}