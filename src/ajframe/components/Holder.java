package ajframe.components;

import ajframe.design.Designable;
import math.ajvector.PVector;
import math.ajvector.Vecthur;
import math.ajvector.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Holder extends Component {

    protected List<Component> components = new ArrayList<>(); // All components
    protected List<Component> renderList = new ArrayList<>(); // All components which are rendered in next cycle

    /**
     * Constructors
     *
     * @param size
     * @param position
     */

    public Holder(PVector size, PVector position) {
        super(size, position);
        renderList.add(this);
    }

    // Calls its holder to render as well
    protected void callRender(Component component) {
        renderList.add(component);
        holder.callRender(this);
    }

    // Returns Component on the chosen Location
    @Override
    public Component onPosition(Vecthur testPos) {
        for (Component c : components) {
            if (c.isOnPosition(testPos)) {
                return true;
            }
        }
        if (super.isOnPosition(testPos)) {
            return true;
        }
    }

    // Renders itself and child Components
    @Override
    public void render(Graphics2D g, Vector offset, Designable holdersDesignable, boolean fullRender) {

        if (renderList.contains(this) || fullRender) {
            fullRender = true;
            super.render(g, offset, holdersDesignable, fullRender);
            components.remove(this);
        }

        // Renders every Child if fullRender, else renders renderList
        for (Component c : (fullRender) ? components : renderList) {
            // Renders every child component
            renderList.remove(c);
            c.render(g, offset, (designable != null) ? designable : holdersDesignable, fullRender);
        }

    }

    // Add's new Component and renders it
    public Component add(Component component) {
        components.add(component);
        component.setHolder(this);
        callRender(component);

        return component;
    }
}
