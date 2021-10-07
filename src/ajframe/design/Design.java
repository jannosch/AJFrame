package ajframe.design;

import ajframe.components.Component;
import ajframe.components.ComponentState;
import ajframe.design.attributes.DesignAttribute;
import math.ajvector.Vector;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Design {

    private HashMap<ComponentState, List<DesignAttribute>> rawAttributes = new HashMap<>();
    private HashMap<ComponentState, Cursor> rawCursors = new HashMap<>();
    private HashMap<ComponentState, List<DesignAttribute>> compiledAttributes;
    private HashMap<ComponentState, Cursor> compiledCursors;
    private boolean needCompile = true;

    /** Constructors */
    public Design(DesignAttribute ...designAttributes) {
        List<DesignAttribute> attributes = new ArrayList<>();
        Collections.addAll(attributes, designAttributes);
        rawAttributes.put(Component.State.NONE, attributes);
    }

    // Renders every Designattribute
    public void render(Graphics2D g, Vector offset, Component component) {
        if (needCompile) startCompile(component);
        List<DesignAttribute> attributes;
        ComponentState state = component.getState();

        if (compiledAttributes.containsKey(state)) {
            attributes = compiledAttributes.get(state);
        } else {
            if (state.equals(Component.State.ACTIVE_HOVERED)) attributes = compiledAttributes.get(Component.State.HOVERED);
            else attributes = compiledAttributes.get(Component.State.NONE);
        }
        for (DesignAttribute designAttribute : attributes) {
            designAttribute.render(g, component.getShape(), offset, component);
        }
    }

    // Adds a DesignAttribute to Design for State.NONE
    public void addDesignAttribute(DesignAttribute designAttribute) {
        rawAttributes.get(Component.State.NONE).add(designAttribute);
    }

    // Updates cursor
    public Cursor getCursor(Component component) {
        if (compiledCursors.containsKey(component.getState())) {
            return compiledCursors.get(component.getState());
        } else {
            return compiledCursors.get(Component.State.NONE);
        }
    }

    // Start compiling on next render
    public void compile() {
        needCompile = true;
    }

    // Compile
    private void startCompile(Component component) {
        // Gets Theme and uses rawAttributes if Theme is null
        Theme theme = component.getTheme();
        if (theme == null) {
            compiledAttributes = rawAttributes;
            compiledCursors = rawCursors;
            return;
        }

        compiledAttributes = new HashMap<>();
        HashMap<ComponentState, List<DesignAttribute>> themeAttributes = theme.getAttributes(component.getClass());
        if (themeAttributes == null) themeAttributes = theme.getAttributes(Component.class);

        // Collect all defined states
        ArrayList<ComponentState> states = new ArrayList<>();
        states.addAll(themeAttributes.keySet());
        for (ComponentState themeState : themeAttributes.keySet()) {
            if (!states.contains(themeState)) states.add(themeState);
        }

        // Merges Theme and Design
        for (ComponentState state : states) {
            List<DesignAttribute> designAttributes = new ArrayList<>();

            // Add rawAttributes to CompiledAttributes
            if (rawAttributes.containsKey(state)) designAttributes.addAll(rawAttributes.get(state));

            // Add Theme's Attributes to compiled Attributes if no other
            // Attribute of this class exists
            if (themeAttributes.containsKey(state)) {
                for (DesignAttribute attribute : themeAttributes.get(state)) {
                    if (!listContainsClass(attribute.getClass(), designAttributes)) {
                        designAttributes.add(attribute);
                    }
                }
            }

            compiledAttributes.put(state, designAttributes);
        }

        // Compile Cursors
        compiledCursors = new HashMap<>();
        HashMap<ComponentState, Cursor> themeCursors = theme.getCursors(component.getClass());
        if (themeCursors == null) themeCursors = theme.getCursors(Component.class);

        // Collect all defined states
        states = new ArrayList<>();
        states.addAll(themeCursors.keySet());
        for (ComponentState themeState : themeCursors.keySet()) {
            if (!states.contains(themeCursors)) states.add(themeState);
        }

        for (ComponentState state : states) {
            if (rawCursors.containsKey(state)) {
                compiledCursors.put(state, rawCursors.get(state));
            } else {
                compiledCursors.put(state, themeCursors.get(state));
            }
        }


    }

    private boolean listContainsClass(Class containsClass, List list) {
        for (Object object : list) {
            if (object.getClass().equals(containsClass)) return true;
        }
        return false;
    }

    // Getters 'n Setters
    public HashMap<ComponentState, List<DesignAttribute>> getRawAttributes() {
        return rawAttributes;
    }

    public HashMap<ComponentState, List<DesignAttribute>> getCompiledAttributes() {
        return compiledAttributes;
    }
}
