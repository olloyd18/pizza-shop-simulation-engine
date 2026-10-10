package org.uob.a2.model;

import org.uob.a2.engine.*;

public class Restaurant extends Consumer {

    public Restaurant(String name) {
        super(name, ResourceType.PIZZA, 1);
        addCost(ResourceType.CREDITS, 150);
    }
    
    @Override
    public void tick(Context ctx) {
        int before = ctx.state().getResourceAmount(ResourceType.PIZZA);
    
        consume(ctx);
    
        int after = ctx.state().getResourceAmount(ResourceType.PIZZA);
    
        if (after < before) {
            System.out.println("[" + getName() + "] Sold 1 pizza → earned 10 credits.");
        }
    }


    // Regular tick: eat 1 pizza → earn 10 credits
    @Override
    public void consume(Context ctx) {
        if (ctx.state().removeResource(consumedResource, amount)) {
            ctx.state().addResource(ResourceType.CREDITS, 10);
        }
    }

    // Marketing campaign: costs credits proportional to pizzas sold
    public int launchSale(Context ctx) {
    
        int pizzas = ctx.state().getResourceAmount(ResourceType.PIZZA);
        if (pizzas <= 0) return -1; // signal: nothing to sell
    
        // marketing campaign cost (5 credits per pizza)
        int marketingCost = pizzas * 1;
        System.out.println("Marketing Costs: £" + marketingCost);
    
        // check if credits are available
        int credits = ctx.state().getResourceAmount(ResourceType.CREDITS);
        if (credits < marketingCost) {
            return -2; // signal: not enough credits
        }
    
        // remove marketing cost
        ctx.state().removeResource(ResourceType.CREDITS, marketingCost);

        
        int salePrice = 10;
        int revenue = pizzas * salePrice;
    
        // remove pizzas
        ctx.state().removeResource(ResourceType.PIZZA, pizzas);
    
        // add earned credits
        ctx.state().addResource(ResourceType.CREDITS, revenue);
    
        // return NET profit
        return revenue - marketingCost;
    }

    @Override
    public String toCSV() {
        return getClass().getSimpleName() + "," + getName();
    }
}
