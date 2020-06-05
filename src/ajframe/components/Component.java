package ajframe.components;

import ajframe.design.Design;
import ajframe.design.Designable;
import ajframe.design.attributes.Fill;
import math.ajvector.PVector;
import math.ajvector.Vecthur;
import math.ajvector.Vector;

import java.awt.*;

public class Component {

    protected PVector size; // Sets size of Component cannot be null
    protected PVector position; // Position of Component relative to Holder, if null the position is determined by Layout
    protected Designable designable = new Design(new Fill(new Color(29, 94, 184))); // Handles the Appearance of Component  // TODO: change
    protected Holder holder; // Holder will receive call to render

    /** Constructors */
    public Component(PVector size, PVector position) {
        this.size = size;
        this.position = position;
    }

    // Tells the Designable to render and do additional Component specific stuff
    public void render(Graphics2D g, Vector offset, Designable holdersDesignable, boolean fullRender) {
        // If has design, use it, if not dann halt des andere
        if (designable != null) {
            designable.render(g, offset, this);
        } else {
            holdersDesignable.render(g, offset, this);
        }
    }

    // Tells holder to Render
    public void callRender() {
        holder.callRender(this);
    }

    // Returns true if on Position
    public boolean isOnPosition(Vecthur testPos) {
        Vector testPosition = (Vector) testPos.clone();
        testPos.sub(position.getX(), position.getY());
        if (testPos.getX() < 0 || testPos.getY() < 0 || testPos.getX() > size.getX() || testPos.getY() > size.getY()) return false;
        return designable.getShape(this).isOnPosition(testPosition, size);
    }

    public Designable getDesignable() {
        return designable;
    }

    public void setDesignable(Designable designable) {
        this.designable = designable;
    }

    public Holder getHolder() {
        return holder;
    }

    // Protected because user can mess up something with it
    protected void setHolder(Holder holder) {
        this.holder = holder;
    }

    public PVector getSize() {
        return size;
    }

    public void setSize(PVector size) {
        this.size = size;
    }

    public PVector getPosition() {
        return position;
    }

    public void setPosition(PVector position) {
        this.position = position;
    }
}
