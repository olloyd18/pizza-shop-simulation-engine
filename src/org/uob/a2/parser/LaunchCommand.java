package org.uob.a2.parser;
import java.util.List;
import org.uob.a2.engine.Context;

public class LaunchCommand extends Command {

    public LaunchCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        if (words.size() < 2) {
            return "No target specified.";
        }

        return ctx.engine().launch(words.get(1));
    }
}
