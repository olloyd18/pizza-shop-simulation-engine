package org.uob.a2.engine;

import org.uob.a2.model.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;


public class Engine {

    private int currentTick = 0;
    private SimulationState state;

    public Engine(SimulationState state) {
        this.state = state;
        this.state.addResource(ResourceType.CREDITS, 1000);
    }

    public int getCurrentTick() {
        return currentTick;
    }



    
    public String nextTick() {
        Context ctx = new Context(this, state);
        currentTick++;

        StringBuilder out = new StringBuilder();
        out.append("Tick ").append(currentTick).append(":\n");

        for (Producer p : state.getProducers()) {
            p.tick(ctx);
            out.append("- Producer updated: ").append(p.getName()).append("\n");
        }

        for (Converter c : state.getConverters()) {
            c.tick(ctx);
            out.append("- Converter updated: ").append(c.getName()).append("\n");
        }

        for (Consumer c : state.getConsumers()) {
            c.tick(ctx);
            out.append("- Consumer updated: ").append(c.getName()).append("\n");
        }

        state.updateHistory();
        return out.toString().trim();
    }

    public String build(String entityName) {
    
        if (entityName == null || entityName.isEmpty()) {
            return "Invalid entity name.";
        }
    
        String lower = entityName.toLowerCase();
        Entity newEntity = null;



        
        // to create entitiies and assign their building costs 
    
        switch (lower) {
    
            case "wheatfarm":
                newEntity = new WheatFarm("WheatFarm" + (state.getProducers().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 50);
                break;
    
            case "tomatofarm":
                newEntity = new TomatoFarm("TomatoFarm" + (state.getProducers().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 40);
                break;
    
            case "dairyfarm":
                newEntity = new DairyFarm("DairyFarm" + (state.getProducers().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 80);
                break;
    
            case "well":
                newEntity = new Well("Well" + (state.getProducers().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 20);
                break;
    
            case "mixer":
                newEntity = new Mixer("Mixer" + (state.getConverters().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 120);
                break;
    
            case "saucemachine":
                newEntity = new SauceMachine("SauceMachine" + (state.getConverters().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 90);
                break;
    
            case "oven":
                newEntity = new Oven("Oven" + (state.getConverters().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 150);
                break;
    
            case "restaurant":
                newEntity = new Restaurant("Restaurant" + (state.getConsumers().size() + 1));
                newEntity.addCost(ResourceType.CREDITS, 200);
                break;
    
            default:
                return "Unknown entity: " + entityName;
        }




        
        // CHECK IF PLAYER CAN AFFORD IT 
        int totalCost = 0;
        for (var entry : newEntity.getCosts().entrySet()) {
            int cost = entry.getValue();
            totalCost += cost;
    
            if (state.getResourceAmount(entry.getKey()) < cost) {
                return "Not enough " + entry.getKey() + " to build " + newEntity.getName();
            }
        }
    
        // DEDUCT COST 
        for (var entry : newEntity.getCosts().entrySet()) {
            state.removeResource(entry.getKey(), entry.getValue());
        }
    
        // ADD NEW ENTITY 
        if (newEntity instanceof Producer) {
            state.getProducers().add((Producer) newEntity);
        } else if (newEntity instanceof Converter) {
            state.getConverters().add((Converter) newEntity);
        } else if (newEntity instanceof Consumer) {
            state.getConsumers().add((Consumer) newEntity);
        }



        
        // RETURN MESSAGE 
        return "Built " + newEntity.getName() + " (Cost: " + totalCost + " credits)";
    }

    // INFO COMMAND
    public String info(String category) {

        if (category == null) return "Invalid info category.";

        String lower = category.toLowerCase();
        StringBuilder out = new StringBuilder();

        switch (lower) {

            case "resources":
                out.append("Resources:\n");
                for (var e : state.getInventory().entrySet()) {
                    out.append(e.getKey()).append(": ").append(e.getValue()).append("\n");
                }
                return out.toString().trim();

            case "producers":
                out.append("Producers:\n");
                for (Producer p : state.getProducers()) {
                    out.append(p.getName()).append("\n");
                }
                return out.toString().trim();

            case "converters":
                out.append("Converters:\n");
                for (Converter c : state.getConverters()) {
                    out.append(c.getName()).append("\n");
                }
                return out.toString().trim();

            case "consumers":
                out.append("Consumers:\n");
                for (Consumer c : state.getConsumers()) {
                    out.append(c.getName()).append("\n");
                }
                return out.toString().trim();

            case "all":
                return info("resources") + "\n\n"
                     + info("producers") + "\n\n"
                     + info("converters") + "\n\n"
                     + info("consumers");

            default:
                return "Unknown info category: " + category;
        }
    }

    // HELP COMMAND
    public String help(String[] args) {

        return """
        Commands:
        - build <entity>
        - tick
        - info <resources|producers|converters|consumers|all>
        - graph <resource>
        - save <file>
        - load <file>
        - launch <consumer>
        - cheat
        - quit
        """.trim();
    }


        
    public String graph(String resourceName) {
    
        // If no resource provided
        if (resourceName == null || resourceName.isEmpty()) {
            return "Invalid graph command.";
        }
    

        ResourceType target = null;
        for (ResourceType r : ResourceType.values()) {
            if (r.name().equalsIgnoreCase(resourceName)) {
                target = r;
                break;
            }
        }

        
        if (target == null) {
            return "Unknown resource: " + resourceName;
        }


        
        // Build graph output
        StringBuilder out = new StringBuilder();
        out.append("Graph for ").append(target).append(":\n");
    
        List<Map<ResourceType,Integer>> history = state.getHistory();
    
        for (int tick = 0; tick < history.size(); tick++) {
            int amount = history.get(tick).getOrDefault(target, 0);
    
            out.append("Tick ").append(tick).append(": ");
    
            for (int i = 0; i < amount; i++) {
                out.append("*");
            }
    
            out.append("\n");
        }
    
        return out.toString();
    }



    
    public String launch(String entityName) {
    
        if (entityName == null || entityName.isEmpty()) {
            return "Specify a restaurant for the marketing campaign.";
        }
    
        String lower = entityName.toLowerCase();
    
        for (Consumer c : state.getConsumers()) {
            if (c instanceof Restaurant 
                && c.getName().toLowerCase().equals(lower)) {
    
                Restaurant r = (Restaurant) c;
                Context ctx = new Context(this, state);
    
                int result = r.launchSale(ctx);
    
                if (result == -1) {
                    return r.getName() + " has no pizzas to sell.";
                }
                if (result == -2) {
                    return r.getName() + " cannot afford the marketing campaign.";
                }
    
                //if success result is net profit
                return r.getName() + " launched a marketing campaign.\n"
                     + "Net profit: " + result + " credits.";
            }
        }
    
        return "No restaurant named " + entityName + " found.";
    }



    // SAVE
    public String save(String filename) {
        try {
            File file = new File(filename);
    

    
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
    
            // first line: current tick
            writer.write("CurrentTick," + currentTick);
            writer.newLine();
    
            // resources
            for (var entry : state.getInventory().entrySet()) {
                writer.write("RESOURCE," + entry.getKey() + "," + entry.getValue());
                
                writer.newLine();
            }
    
            // producers
            for (Producer p : state.getProducers()) {
                writer.write("PRODUCER," + p.toCSV());
                writer.newLine();
            }
    
            // converters
            for (Converter c : state.getConverters()) {
                writer.write("CONVERTER," + c.toCSV());
                writer.newLine();
            }
    
            // consumers
            for (Consumer c : state.getConsumers()) {
                writer.write("CONSUMER," + c.toCSV());
                writer.newLine();
            }
    
            writer.close();
            return "Saved to " + filename;
    
        } catch (Exception e) {
            return "Error saving: " + e.getMessage();
        }
    }

        
    public String load(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
    
            // reset everything
            currentTick = 0;
            state.getInventory().clear();
            state.getProducers().clear();
            state.getConverters().clear();
            state.getConsumers().clear();
            state.getHistory().clear();
    
            // reset all resource types to zero so the inventory is consistent
            for (ResourceType r : ResourceType.values()) {
                state.updateResource(r, 0);
            }
    
            String line;
    
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
    
                switch (parts[0]) {
    
                    case "CurrentTick":
                        currentTick = Integer.parseInt(parts[1]);
                        break;
    
                    case "RESOURCE":
                        state.updateResource(
                            ResourceType.valueOf(parts[1]),
                            Integer.parseInt(parts[2])
                        );
                        break;
    
                    case "PRODUCER":
                        state.getProducers().add(
                            new WheatFarm(parts[2])    // simple reconstruction
                        );
                        break;
    
                    case "CONVERTER":
                        state.getConverters().add(
                            new Mixer(parts[2])        // simple reconstruction
                        );
                        break;
    
                    case "CONSUMER":
                        state.getConsumers().add(
                            new Restaurant(parts[2])   // simple reconstruction
                        );
                        break;
                }
            }
    
            state.updateHistory();
            return "Loaded " + filename;
    
        } catch (Exception e) {
            return "Error loading: " + e.getMessage();
        }
    }



    // DEFAULT ENTITIES
    public String initialiseDefaults() {
        List<String> names = new ArrayList<>();

        Producer wf = new WheatFarm("WheatFarm1");
        state.getProducers().add(wf);
        names.add(wf.getName());

        Converter mx = new Mixer("Mixer1");
        state.getConverters().add(mx);
        names.add(mx.getName());

        Consumer rst = new Restaurant("Restaurant1");
        state.getConsumers().add(rst);
        names.add(rst.getName());

        state.updateHistory();

        return "Initialised defaults: " + String.join(", ", names);
    }

    // CHEAT COMMAND
    public String cheat() {
        // Give the player a resource boost
        state.addResource(ResourceType.CREDITS, 500);
        state.addResource(ResourceType.FLOUR, 50);
        state.addResource(ResourceType.TOMATO, 50);
        state.addResource(ResourceType.CHEESE, 50);
        state.addResource(ResourceType.WATER, 50);
        state.addResource(ResourceType.PIZZA, 50);
        state.addResource(ResourceType.DOUGH, 50);
        state.addResource(ResourceType.SAUCE, 50);
    
        // Also record this new state in the history so graph works
        state.updateHistory();
    
        return "Cheat activated: +500 CREDITS, +50 FLOUR, TOMATO, CHEESE, WATER PIZZAS";
    }

}
