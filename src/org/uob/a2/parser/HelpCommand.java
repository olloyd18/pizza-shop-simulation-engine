package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class HelpCommand extends Command {

    private final List<String> words;

    public HelpCommand(List<String> words) {
        super(words);
        this.words = words;
    }

    @Override
    public String execute(Context ctx) {
        // convert to array because engine.help expects String[]
        return ctx.engine().help(words.toArray(new String[0]));
    }
}
