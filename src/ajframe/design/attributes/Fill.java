package ajframe.design.attributes;

import ajframe.components.Component;
import ajframe.design.Shape;
import de.sirarthur.math.Vector;

import java.awt.*;

public class Fill implements DesignAttribute {

    Color color;

    public Fill(Color color) {
        this.color = color;
    }

    @Override
    public void render(Graphics2D g, Shape shape, Vector offset, Component component) {
        // nur schnelles Rechteck
        g.setColor(color);
        g.fillRect(offset.getRdX() + component.getPosition().getRdX(), offset.getRdY() + component.getPosition().getRdY(), component.getSize().getRdX(), component.getSize().getRdY());
    }
}
