package ajframe.design;

import ajframe.components.Component;
import math.ajvector.Vector;

import java.awt.*;

public interface Designable {

    // Renders every Design attribute
    void render(Graphics2D g, Vector offset, Component component);

    Shape getShape(Component component);

}
