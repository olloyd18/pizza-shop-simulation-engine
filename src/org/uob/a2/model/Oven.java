package org.uob.a2.model;

import org.uob.a2.engine.*;

// Converts DOUGH into PIZZA
public class Oven extends Converter {

    public Oven(String name) {
        super(name, ResourceType.DOUGH, 1, ResourceType.PIZZA, 1);
        addCost(ResourceType.CREDITS, 100);
    }

    @Override
    public void tick(Context ctx) {
        int before = ctx.state().getResourceAmount(ResourceType.PIZZA);
    
        convert(ctx);
    
        int after = ctx.state().getResourceAmount(ResourceType.PIZZA);
        int produced = after - before;
    
        if (produced > 0) {
            System.out.println("[" + getName() + "] Produced " + produced + " pizza(s).");
        }
    }

    public void convert(Context ctx) {
    
        boolean hasDough = ctx.state().removeResource(ResourceType.DOUGH, 2);
        boolean hasCheese = ctx.state().removeResource(ResourceType.CHEESE, 1);
        boolean hasSauce = ctx.state().removeResource(ResourceType.SAUCE, 1);
    
        if (hasDough && hasCheese && hasSauce) {
            ctx.state().addResource(ResourceType.PIZZA, 1);
            return;
        }
    
        // If recipe fails, refund anything that was removed
        if (hasDough) ctx.state().addResource(ResourceType.DOUGH, 2);
        if (hasCheese) ctx.state().addResource(ResourceType.CHEESE, 1);
        if (hasSauce) ctx.state().addResource(ResourceType.SAUCE, 1);
    }

    public String toCSV() {
        return getClass().getSimpleName() + "," + getName();
    }
}
