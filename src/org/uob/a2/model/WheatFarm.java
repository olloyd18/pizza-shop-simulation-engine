package org.uob.a2.model;

import org.uob.a2.engine.*;

public class WheatFarm extends Producer {

    // Produces FLOUR each tick
    public WheatFarm(String name) {
        super(name, ResourceType.FLOUR, 5);
        addCost(ResourceType.CREDITS, 40);   // keeps build economy balanced
    }

    @Override
    public void tick(Context ctx) {
        produce(ctx);
    }

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(ResourceType.FLOUR, amount);
    }

    @Override
    public String toCSV() {
        return getClass().getSimpleName() + "," + getName() + "," + amount;
    }
}
