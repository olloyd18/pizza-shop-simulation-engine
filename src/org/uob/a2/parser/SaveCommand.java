package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class SaveCommand extends Command {

    public SaveCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        if (words.size() < 2) return "Missing filename.";
        return ctx.engine().save(words.get(1));
    }
}