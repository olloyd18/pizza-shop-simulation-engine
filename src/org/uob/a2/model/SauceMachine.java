package org.uob.a2.model;

import org.uob.a2.engine.*;

// Converts TOMATO into SAUCE
public class SauceMachine extends Converter {

    public SauceMachine(String name) {
        super(name, ResourceType.TOMATO, 3, ResourceType.SAUCE, 1);
        addCost(ResourceType.CREDITS, 80);
    }

    public void tick(Context ctx) {
        convert(ctx);
    }

    public void convert(Context ctx) {
        if (ctx.state().removeResource(input, inputAmount)) {
            ctx.state().addResource(output, outputAmount);
        }
    }

    public String toCSV() {
        return getClass().getSimpleName() + "," + getName();
    }
}
