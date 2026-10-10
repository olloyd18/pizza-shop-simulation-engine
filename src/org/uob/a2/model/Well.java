package org.uob.a2.model;

import org.uob.a2.engine.*;

public class Well extends Producer {

    public Well(String name) {
        super(name, ResourceType.WATER, 4);
        addCost(ResourceType.CREDITS, 30);
    }

    @Override
    public void tick(Context ctx) {
        produce(ctx);
    }

    @Override
    public void produce(Context ctx) {
        ctx.state().addResource(ResourceType.WATER, amount);
    }

    @Override
    public String toCSV() {
        return getClass().getSimpleName() + "," + getName() + "," + amount;
    }
}
