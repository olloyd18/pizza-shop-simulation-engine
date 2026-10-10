package org.uob.a2;

import org.uob.a2.engine.*;
import org.uob.a2.parser.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Set up the objects
        SimulationState state = new SimulationState();
        Engine engine = new Engine(state);
        Parser parser = new Parser();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Simulation started. Type commands or 'quit' to exit and 'help' to see the commands");

        while (true) {
            String line = scanner.nextLine();

            //Ignore empty inputs
            if (line.isEmpty()) {
                continue;
            }

            // Parse command
            Command comd = parser.parse(line);

            // Execute
            String result = comd.execute(new Context(engine, state));

            // print the output
            if (!result.isEmpty()) {
                System.out.println(result);
            }

            // Quit 
            if (result.equals("Quitting simulation.")) {
                System.out.println("Goodbye.");
                break;
            }
        }

        scanner.close();
    }
}
