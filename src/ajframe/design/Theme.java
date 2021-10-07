package ajframe.design;

import ajframe.AJFrame;
import ajframe.components.Component;
import ajframe.components.ComponentState;
import ajframe.design.attributes.DesignAttribute;
import ajframe.design.attributes.Fill;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Theme {

    public static final Theme DARK = new Theme();
    public static final Theme LIGHT = new Theme();

    static {
        DARK.addAttribute(Component.class, Component.State.NONE, new Fill(new Color(49, 51, 53)));
        DARK.addAttribute(Component.class, Component.State.HOVERED, new Fill(new Color(60, 63, 65)));
        DARK.addAttribute(AJFrame.class, Component.State.NONE, new Fill(new Color(43, 43, 43)));
        DARK.setCursor(Component.class, Component.State.HOVERED, new Cursor(Cursor.HAND_CURSOR));
        DARK.setCursor(AJFrame.class, Component.State.NONE, Cursor.getDefaultCursor());

        LIGHT.addAttribute(Component.class, Component.State.NONE, new Fill(new Color(255, 255, 255)));
        LIGHT.addAttribute(Component.class, Component.State.HOVERED, new Fill(new Color(230, 230, 230)));
        LIGHT.addAttribute(AJFrame.class, Component.State.NONE, new Fill(new Color(243, 243, 243)));
    }


    private HashMap<Class, HashMap<ComponentState, List<DesignAttribute>>> componentAttributes = new HashMap<>();
    private HashMap<Class, HashMap<ComponentState, Cursor>> cursors = new HashMap<>();

    public Theme() {
        setCursor(Component.class, Component.State.NONE, Cursor.getDefaultCursor());
    }

    // Adds Attribute
    public void addAttribute(Class componentClass, ComponentState componentState, DesignAttribute designAttribute) {
        HashMap<ComponentState, List<DesignAttribute>> stateAttributes;
        List<DesignAttribute> designAttributes;

        // Create HashMap if needed
        if (componentAttributes.containsKey(componentClass)) {
            stateAttributes = componentAttributes.get(componentClass);
        } else {
            stateAttributes = new HashMap<>();
            componentAttributes.put(componentClass, stateAttributes);
        }

        // Create ArrayList if needed
        if (stateAttributes.containsKey(componentState)) {
            designAttributes = stateAttributes.get(componentState);
        } else {
            designAttributes = new ArrayList<>();
            stateAttributes.put(componentState, designAttributes);
        }

        designAttributes.add(designAttribute);

    }

    public void setCursor(Class componentClass, ComponentState componentState, Cursor cursor) {
        HashMap<ComponentState, Cursor> stateCursor;

        // Create HashMap if needed
        if (cursors.containsKey(componentClass)) {
            stateCursor = cursors.get(componentClass);
        } else {
            stateCursor = new HashMap<>();
            cursors.put(componentClass, stateCursor);
        }

        // Sets Cursor
        stateCursor.put(componentState, cursor);
    }

    // Returns all Attributes for specific component (type)
    public HashMap<ComponentState, List<DesignAttribute>> getAttributes(Class componentClass) {
        return componentAttributes.get(componentClass);
    }

    // Returns all Cursors for specific component (type)
    public HashMap<ComponentState, Cursor> getCursors(Class componentClass) {
        return cursors.get(componentClass);
    }

}
