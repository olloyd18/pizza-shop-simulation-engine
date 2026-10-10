package org.uob.a2.parser;

import java.util.List;
import org.uob.a2.engine.Context;
import org.uob.a2.engine.Engine;


public class CheatCommand extends Command {

    public CheatCommand(List<String> words) {
        super(words);
    }

    @Override
    public String execute(Context ctx) {
        // Just call Engine.cheat()
        return ctx.engine().cheat();
    }
}