package ajframe.components;

import ajframe.design.Design;
import ajframe.design.Theme;
import ajframe.design.attributes.Fill;
import math.ajvector.BVector;
import math.ajvector.Vecthur;
import math.ajvector.Vector;

import java.awt.*;

public class Component {

    protected BVector position; // Position of Component relative to Holder, if null the position is determined by Layout
    protected BVector size; // Sets size of Component cannot be null
    protected Design design = new Design(); // Handles the Appearance of Component
    protected Theme theme = null;
    protected Shape shape = new Shape.Rect();
    protected ComponentState state = State.NONE;
    protected Holder parent; // Holder will receive call to render
    protected boolean shouldRender = true;
    protected boolean resizeable = true;

    /** Constructors */
    public Component(BVector position, BVector size) {
        this.position = position;
        this.size = size;
    }

    // Tells the Designable to render and do additional Component specific stuff
    public void render(Graphics2D g, Vector offset, boolean forceRender) {
        if (shouldRender || forceRender) {
            design.render(g, offset, this);
            shouldRender = false;
        }
    }

    // Tells holder to Render everything
    public void callRender() {
        parent.callRender();
    }

    // Returns true if on Position
    public boolean isOnPosition(Vector testPos) {
        Vector testPosition = testPos.clone();
        testPosition.sub(position.getX(), position.getY());
        if (testPosition.getX() < 0 || testPosition.getY() < 0 || testPosition.getX() > size.getX() || testPosition.getY() > size.getY()) return false;
        return shape.isOnPosition(testPosition, size);
    }

    // Resizes one side of component. Value is relative to Axis
    public void resizeLeft(int amount) {
        position.add(amount, 0);
        size.add(-amount, 0);
        callRender();
    }

    // Resizes one side of component. Value is relative to Axis
    public void resizeTop(int amount) {
        position.add(0, amount);
        size.add(0, -amount);
        callRender();
    }

    // Resizes one side of component. Value is relative to Axis
    public void resizeRight(int amount) {
        size.add(amount, 0);
        callRender();
    }

    // Resizes one side of component. Value is relative to Axis
    public void resizeBottom(int amount) {
        size.add(0, amount);
        callRender();
    }

    public Design getDesign() {
        return design;
    }

    // Returns Theme of
    public Theme getTheme() {
        if (parent != null) if (theme == null) return parent.getTheme();
        return theme;
    }

    public void setDesign(Design design) {
        this.design = design;
        this.design.compile();
        shouldRender = true;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
        design.compile();
    }

    public Holder getParent() {
        return parent;
    }

    // Protected because user can mess up something with it
    protected void setParent(Holder parent) {
        this.parent = parent;
        this.shouldRender = true;
    }

    public BVector getSize() {
        return size;
    }

    public void setSize(BVector size) {
        this.size = size;
        shouldRender = true;
    }

    public BVector getPosition() {
        return position;
    }

    public void setPosition(BVector position) {
        this.position = position;
        shouldRender = true;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public ComponentState getState() {
        return state;
    }

    public void setState(ComponentState state) {
        this.state = state;
        shouldRender = true;
        callRender();
    }

    public boolean isShouldRender() {
        return shouldRender;
    }

    public void setShouldRender(boolean shouldRender) {
        this.shouldRender = shouldRender;
    }

    public boolean isResizeable() {
        return resizeable;
    }

    public void setResizeable(boolean resizeable) {
        this.resizeable = resizeable;
    }

    // ComponentStates
    public enum State implements ComponentState {

        NONE, HOVERED, PRESSED, ACTIVE, ACTIVE_HOVERED, ACTIVE_PRESSED, DISABLED;

    }
}
