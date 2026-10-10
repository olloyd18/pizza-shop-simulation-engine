package org.uob.a2.engine;

import org.uob.a2.model.*;

import java.util.List;
import java.util.ArrayList;

import java.util.EnumMap;
import java.util.Map;

public class SimulationState {

    private List<Producer> producers;
    private List<Converter> converters;
    private List<Consumer> consumers;
    private Map<ResourceType, Integer> inventory = new EnumMap<>(ResourceType.class);
    private List<Map<ResourceType, Integer>> resourceHistory = new ArrayList<>();


    //Constructor
    public SimulationState() {
        this.producers = new ArrayList<>();
        this.converters = new ArrayList<>();
        this.consumers = new ArrayList<>();

        // Initialise all resources to 0 so we never hit null lookups
        for (ResourceType type : ResourceType.values()) {
            inventory.put(type, 0);
        }
    }
    
    public List<Producer> getProducers() {
        return producers;
    }

    public List<Converter> getConverters() {
        return converters;
    }

    public List<Consumer> getConsumers() {
        return consumers;
    }




    // Add a new resource amount to the current inventory
    public void addResource(ResourceType resource, int amount) {
        if (resource == null || amount <= 0) {
            return; // nothing meaningful to add
        }

        int current = inventory.getOrDefault(resource, 0);
        inventory.put(resource, current + amount);
    }

    // Set a resource to a new amount (overwrite behaviour)
    public void updateResource(ResourceType resource, int amount) {
        if (resource == null) {
            return;
        }

        // Store zero instead of removing the key.
        // This avoids null lookups and keeps the map consistent.
        inventory.put(resource, Math.max(amount, 0));
    }

    // Return how much of a resource exists. If none stored, treat as zero.
    public int getResourceAmount(ResourceType resource) {
        if (resource == null) {
            return 0;
        }
        return inventory.getOrDefault(resource, 0);
    }

    // Try to remove an amount of a resource. Return true if successful.
    public boolean removeResource(ResourceType resource, int amount) {
        if (resource == null || amount <= 0) {
            return false;
        }

        int current = inventory.getOrDefault(resource, 0);

        // Not enough resources
        if (current < amount) {
            return false;
        }

        inventory.put(resource, current - amount);
        return true;
    }

    // Store a snapshot of the current inventory into the history list
    public void updateHistory() {
        Map<ResourceType, Integer> snapshot = new EnumMap<>(ResourceType.class);

        // Deep copy each entry so history is not tied to the live inventory
        for (Map.Entry<ResourceType, Integer> entry : inventory.entrySet()) {
            snapshot.put(entry.getKey(), entry.getValue());
        }

        resourceHistory.add(snapshot);
    }

    // Used later by the graph command to read past values
    public List<Map<ResourceType, Integer>> getHistory() {
        return resourceHistory;
    }

    // Gives Engine access to the inventory map when saving/loading
    public Map<ResourceType, Integer> getInventory() {
        return inventory;
    }

}