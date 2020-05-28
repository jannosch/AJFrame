package ajframe.design;

import ajframe.components.Component;
import ajframe.design.attributes.DesignAttribute;
import de.sirarthur.math.Vector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Design implements Designable {

    private List<DesignAttribute> designAttributes = new ArrayList<>(); // Stores the DesignAttributes
    private Shape shape = new Shape.Rect();

    /** Constructors */
    public Design(DesignAttribute designAttribute) {
        designAttributes.add(designAttribute);
    }

    // Renders every Designattribute
    @Override
    public void render(Graphics2D g, Vector offset, Component component) {
        for (DesignAttribute d : designAttributes) {
            d.render(g, shape, offset, component);
        }
    }

    @Override
    public Shape getShape(Component component) {
        return shape;
    }
}
