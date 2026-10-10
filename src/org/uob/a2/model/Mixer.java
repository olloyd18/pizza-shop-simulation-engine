package org.uob.a2.model;

import org.uob.a2.engine.*;

// Converts FLOUR into DOUGH
public class Mixer extends Converter {

    public Mixer(String name) {
        super(name, ResourceType.FLOUR, 3, ResourceType.DOUGH, 1);
        addCost(ResourceType.CREDITS, 80);
    }

    public void tick(Context ctx) {
        convert(ctx);
    }
    
    public void convert(Context ctx) {
        // Check it has both required resources
        boolean hasFlour = ctx.state().removeResource(ResourceType.FLOUR, 1);
        boolean hasWater = ctx.state().removeResource(ResourceType.WATER, 1);
    
        // Only convert if both ingredients wereremoved
        if (hasFlour && hasWater) {
            ctx.state().addResource(ResourceType.DOUGH, 1);
        } else {
            // If one ingredient was missing refund the other if removed
            if (hasFlour) ctx.state().addResource(ResourceType.FLOUR, 1);
            if (hasWater) ctx.state().addResource(ResourceType.WATER, 1);
        }
    }


    public String toCSV() {
        return getClass().getSimpleName() + "," + getName();
    }
}
