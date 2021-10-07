package ajframe.components;

import ajframe.design.Theme;
import math.ajvector.BVector;
import math.ajvector.Vecthur;
import math.ajvector.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Holder extends Component {

    protected List<Component> components = new ArrayList<>(); // All components

    /**
     * Constructors
     *
     * @param size
     * @param position
     */

    public Holder(BVector position, BVector size) {
        super(position, size);
    }

    // Returns Component on the chosen Location
    public Component getOnPosition(Vector testPos) {
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
    public void render(Graphics2D g, Vector offset, boolean forceRender) {

        if (shouldRender || forceRender) {
            forceRender = true;
            super.render(g, offset, forceRender);
            components.remove(this);
        }

        // Renders every Child if fullRender, else renders renderList
        for (Component c : components) {
            // Renders every child component
            c.render(g, offset, shouldRender || forceRender);
        }

    }

    // Adds new Component and renders it
    public Component add(Component component) {
        components.add(component);
        component.setParent(this);
        component.getDesign().compile();

        callRender();

        return component;
    }

    // compiles designs
    protected void updateTheme() {
        for (Component component : components) {
            if (component.getTheme() == null) {
                component.getDesign().compile();
            }
        }
    }

    @Override
    public void setTheme(Theme theme) {
        super.setTheme(theme);
        updateTheme();
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
