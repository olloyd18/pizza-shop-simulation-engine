package org.uob.a2.model;

import org.uob.a2.engine.*;

public class TomatoFarm extends Producer {

    public TomatoFarm(String name) {
        super(name, ResourceType.TOMATO, 5);
        addCost(ResourceType.CREDITS, 50);
    }

    @Override
    public void tick(Context ctx) {
        produce(ctx);
    }

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(ResourceType.TOMATO, amount);
    }

    @Override
    public String toCSV() {
        return getClass().getSimpleName() + "," + getName() + "," + amount;
    }
}
