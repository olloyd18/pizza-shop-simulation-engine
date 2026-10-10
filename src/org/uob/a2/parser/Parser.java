package org.uob.a2.parser;

import org.uob.a2.*;

import java.util.List;
import java.util.Arrays;


public class Parser {

    public Command parse(String input) {

        if (input == null || input.trim().isEmpty()) {
            return new InvalidCommand(Arrays.asList("invalid", "empty"));
        }

        // Split into a list of words
        List<String> words = Arrays.asList(input.trim().toLowerCase().split("\\s+"));

        String main = words.get(0);

        switch (main) {

            case "b":
            case "build":
                return new BuildCommand(words);

                
            case "i":
            case "info":
                return new InfoCommand(words);

            case "g":
            case "graph":
                return new GraphCommand(words);

            case "s":
            case "save":
                return new SaveCommand(words);

                
            case "l":
            case "load":
                return new LoadCommand(words);

            case "t":
            case "tick":
                return new TickCommand(words);

            case "launch":
                return new LaunchCommand(words);

            case "help":
                return new HelpCommand(words);

            case "cheat":
                return new CheatCommand(words);

            case "quit":
                return new QuitCommand(words);

            default:
                return new InvalidCommand(words);
        }
    }
}