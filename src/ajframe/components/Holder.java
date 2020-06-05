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
    public Component getOnPosition(Vecthur testPos) {
        // Loops through every inner Component
        for (Component c : components) {
            if (c.isOnPosition(testPos)) {
                if (c instanceof Holder) {
                    return ((Holder) c).getOnPosition(testPos);
                } else {
                    return c;
                }
            }
        }

        // If no inner Component on Position
        if (isOnPosition(testPos)) {
            return this;
        } else {
            return null;
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

    // Returns all Components, but not components of child Components
    public List<Component> getComponents() {
        return components;
    }

    // Returns all Components with child Components
    public List<Component> getComponents(boolean childComponents) {
        if (!childComponents) return getComponents();

        List<Component> list = new ArrayList<>();
        list.add(this);

        for (Component c : components) {
            list.add(c);
            if (c instanceof Holder) {
                list.addAll(((Holder) c).getComponents(true));
            }
        }

        return list;

    }
}
