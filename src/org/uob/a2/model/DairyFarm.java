package org.uob.a2.model;

import org.uob.a2.engine.*;

public class DairyFarm extends Producer {

    public DairyFarm(String name) {
        super(name, ResourceType.CHEESE, 3);
        addCost(ResourceType.CREDITS, 60);
    }

    @Override
    public void tick(Context ctx) {
        produce(ctx);
    }

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(ResourceType.CHEESE, amount);
    }

    @Override
    public String toCSV() {
        return getClass().getSimpleName() + "," + getName() + "," + amount;
    }
}
