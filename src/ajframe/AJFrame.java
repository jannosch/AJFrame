package ajframe;

import ajframe.components.Component;
import ajframe.components.Holder;
import ajframe.design.Design;
import ajframe.design.Theme;
import ajframe.design.attributes.Fill;
import ajframe.listener.MouseListener;
import math.ajvector.BVector;
import math.ajvector.Vecthur;
import math.ajvector.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class AJFrame extends Holder {

    private JFrame jFrame = new JFrame(); // JFrame holding the JPanel
    private Panel jPanel = new Panel(); // Panel in which everything is Painted
    private Vector offset; // OS-Specific
    private boolean handmadeRender;
    private boolean shouldStartRender = true;
    private int resizeArea = 6;
    private int resizeCornerArea = 10;

    public AJFrame(BVector size) {
        super(new BVector(), size);
        init();
    }

    private void init() {
        // Sets Theme
        setTheme(Theme.DARK);

        // Sets offset
        /*
        if (System.getProperty("os.name").contains("Mac OS")) {
            offset = new Vector(0, 22);
        } else {
            offset = new Vector(6, 28);
        }
        */
        offset = new Vector();

        // Sets Size
        setSize(size);

        // Init JFrame and JPanel
        jPanel.updateSize();
        jPanel.setLayout(null);
        jPanel.setBackground(Color.BLACK);

        jFrame.setLayout(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.add(jPanel);
        jFrame.setVisible(true);

        // Listener
        jPanel.addMouseListener(mouseAdapter);
        jPanel.addMouseMotionListener(mouseMotionAdapter);

        // Shows the Frame the first time
        render();
    }

    @Override
    public void callRender() {
        // TODO: Time to frames
        shouldStartRender = true;
    }

    public void render() {
        if (shouldStartRender) {
            System.out.println("render");
            handmadeRender = true;
            jPanel.repaint();
            shouldStartRender = false;
        }
    }

    // Updates size when scaled
    private void updateSize() {
        size.setX(jFrame.getWidth());
        size.setY(jFrame.getHeight());
        jPanel.updateSize();
    }

    private class Panel extends JPanel {

        // Updates the size to JFrame size
        void updateSize() {
            setBounds(offset.getRdX(), offset.getRdY(), size.getRdX(), size.getRdY());
            setSize(size.getRdX(), size.getRdY());
        }

        @Override
        public void paintComponent(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics;

            // Updates size
            AJFrame.this.updateSize();

            // Activates Antialiasing
            g.setRenderingHints(new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            ));

            render(g, new Vector(), !handmadeRender);

            handmadeRender = false;
        }
    }


    @Override
    public void setSize(BVector size) {
        super.setSize(size);
        jFrame.setSize(size.getRdX() + offset.getRdX(), size.getRdY() + offset.getRdY());
    }

    @Override
    public void setResizeable(boolean resizeable) {
        super.setResizeable(resizeable);
        jFrame.setResizable(resizeable);
    }

    public int getResizeArea() {
        return resizeArea;
    }

    public void setResizeArea(int resizeArea) {
        this.resizeArea = resizeArea;
    }

    // Mouse is always in Frame when this request is done
    @Override
    public boolean isOnPosition(Vector testPos) {
        return true;
    }

    private java.awt.event.MouseAdapter mouseAdapter = new MouseAdapter() {

        Component pressedComponent = null;

        // Fires MouseListener of clicked Component
        @Override
        public void mousePressed(MouseEvent e) {
            // Just Pressed Component
            Component component = getOnPosition(new Vector(e.getX(), e.getY()));
            if (component != null) {
                if (component instanceof MouseListener) {
                    pressedComponent = component;
                    ((MouseListener) component).onPressed(e);
                }
            }

            // Every Component
            for (Component c : getComponents(true)) {
                if (c instanceof MouseListener) {
                    ((MouseListener) c).mousePressed(e);
                }
            }

            render();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Just Released and Clicked Component
            Component component = getOnPosition(new Vector(e.getX(), e.getY()));
            if (component != null) {
                if (component instanceof MouseListener) {
                    ((MouseListener) component).onReleased(e);
                    if (pressedComponent != null) {
                        if (component.equals(pressedComponent)) {
                            ((MouseListener) component).onClicked(e);
                            pressedComponent = null;
                        }
                    }
                }
            }

            // Every Component
            for (Component c : getComponents(true)) {
                if (c instanceof MouseListener) {
                    ((MouseListener) c).mouseReleased(e);
                }
            }

            render();
        }
    };

    private MouseMotionAdapter mouseMotionAdapter = new MouseMotionAdapter() {

        Component hoveredComponent = null;

        @Override
        public void mouseMoved(MouseEvent e) {
            Component component = getOnPosition(new Vector(e.getX(), e.getY()));

            if (component != null) {
                Cursor cursor = null;

                // If component is resizable
                if (component.isResizeable()) {
                    int distanceW = e.getX() - component.getPosition().getRdX();
                    int distanceN = e.getY() - component.getPosition().getRdY();
                    int distanceE = component.getSize().getRdX() - (e.getX() - component.getPosition().getRdX());
                    int distanceS = component.getSize().getRdY() - (e.getY() - component.getPosition().getRdY());

                    if (distanceW < resizeCornerArea && distanceN < resizeCornerArea) {
                        // NW
                        cursor = Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR);
                    } else if (distanceE < resizeCornerArea && distanceN < resizeCornerArea) {
                        // NE
                        cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                    } else if (distanceE < resizeCornerArea && distanceS < resizeCornerArea) {
                        // NE
                        cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                    } else if (distanceW < resizeCornerArea && distanceS < resizeCornerArea) {
                        // NE
                        cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                    } else if (distanceW < resizeArea) {
                        // W
                        cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                    } else if (distanceN < resizeArea) {
                        // N
                        cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                    } else if (distanceE < resizeArea) {
                        // E
                        cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                    } else if (distanceS < resizeArea) {
                        // S
                        cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                    }


                }

                //TODO: If component get resized dont select it but deselect old one
                // If new component is hovered
                if (!component.equals(hoveredComponent)) {

                    // Reset state of previous hovered Component
                    if (hoveredComponent != null) {
                        if (hoveredComponent instanceof MouseListener)
                            ((MouseListener) hoveredComponent).onExited(e);
                        if (hoveredComponent.getState() == State.HOVERED) hoveredComponent.setState(State.NONE);
                        else if (hoveredComponent.getState() == State.ACTIVE_HOVERED)
                            hoveredComponent.setState(State.ACTIVE);
                    }

                    // Update new hovered Component
                    hoveredComponent = component;
                    if (component instanceof MouseListener) ((MouseListener) component).onEntered(e);
                    if (hoveredComponent.getState() == State.NONE) hoveredComponent.setState(State.HOVERED);
                    else if (hoveredComponent.getState() == State.ACTIVE)
                        hoveredComponent.setState(State.ACTIVE_HOVERED);
                }


                if (component instanceof MouseListener) {
                    // Calls onHovered
                    ((MouseListener) component).onHovered(e);
                }

                if (cursor == null) {
                    cursor = component.getDesign().getCursor(component);
                }
                jFrame.setCursor(cursor);
            }

            // Every Component
            for (Component c : getComponents(true)) {
                if (c instanceof MouseListener) {
                    ((MouseListener) c).mouseMoved(e);
                }
            }

            render();
        }
    };
}
