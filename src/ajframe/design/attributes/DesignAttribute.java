package ajframe.design.attributes;

import ajframe.components.Component;
import ajframe.design.Shape;
import de.sirarthur.math.Vector;

import java.awt.*;

public interface DesignAttribute {

    void render(Graphics2D g, Shape shape, Vector offset, Component component);

}
