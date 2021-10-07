package ajframe;

import ajframe.components.Component;
import ajframe.components.Holder;
import ajframe.design.Theme;
import ajframe.listener.MouseListener;
import math.ajvector.BVector;
import math.ajvector.Vector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class AJFrame extends Holder {

    private JFrame jFrame = new JFrame(); // JFrame holding the JPanel
    private Panel jPanel = new Panel(); // Panel in which everything is Painted
    private Vector offset; // OS-Specific
    private boolean handmadeRender;
    private boolean shouldStartRender = true;
    private int resizeArea = 4;
    private int resizeCornerArea = 8;

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
        jPanel.addMouseMotionListener(mouseAdapter);

        // Shows the Frame the first time
        render();
    }

    // TODO: Add method to request full render but dont execute it
    @Override
    public void callRender() {
        // TODO: Time to frames
        shouldStartRender = true;
        shouldRender = true;
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
    private boolean updateSize() {
        if (size.getRdX() != jFrame.getWidth() || size.getRdY() != jFrame.getHeight()) {
            size.setX(jFrame.getWidth());
            size.setY(jFrame.getHeight());
            jPanel.updateSize();
            return true;
        }
        return false;
    }

    private class Panel extends JPanel {

        BufferedImage buffer;
        Graphics2D g;

        void updateBuffer() {
            buffer = new BufferedImage(size.getRdX(), size.getRdY(), BufferedImage.TYPE_4BYTE_ABGR);
            g = buffer.createGraphics();

            // Activates Antialiasing
            g.setRenderingHints(new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            ));
            //g.dispose();
        }

        // Updates the size to JFrame size
        void updateSize() {
            setBounds(offset.getRdX(), offset.getRdY(), size.getRdX(), size.getRdY());
            setSize(size.getRdX(), size.getRdY());
            updateBuffer();
        }

        @Override
        public void paint(Graphics graphics) {

            // Updates size
            if (AJFrame.this.updateSize()) {
                updateBuffer();
            }

            render(g, new Vector(), !handmadeRender);

            graphics.drawImage(buffer, 0, 0, this);
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

    private MouseAdapter mouseAdapter = new MouseAdapter() {

        Component pressedComponent = null;
        Component hoveredComponent = null;
        Component resizeComponent = null;
        private Vector lastMousePosition = new Vector();
        private boolean resizing = false;
        private boolean[] resizeAxis = new boolean[4];; // Indexes: W N E S

        // Fires MouseListener of clicked Component
        @Override
        public void mousePressed(MouseEvent e) {
            // Just Pressed Component
            if (hoveredComponent != null) {
                if (hoveredComponent instanceof MouseListener) {
                    // Trigger mouse listener
                    ((MouseListener) hoveredComponent).onPressed(e);
                }
            } else if (resizeComponent != null) {
                resizing = true;
            }
            pressedComponent = hoveredComponent;

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
            if (hoveredComponent != null) {
                if (hoveredComponent instanceof MouseListener) {
                    // TODO: Add exeption for resize
                    ((MouseListener) hoveredComponent).onReleased(e);
                    if (pressedComponent != null) {
                        if (hoveredComponent.equals(pressedComponent)) {
                            ((MouseListener) hoveredComponent).onClicked(e);
                        }
                    }
                }
            }
            pressedComponent = null;
            resizeComponent = null;
            resizing = false;

            // Every Component
            for (Component c : getComponents(true)) {
                if (c instanceof MouseListener) {
                    ((MouseListener) c).mouseReleased(e);
                }
            }

            render();
        }

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
                        resetResizeAxis(true, true, false, false);
                    } else if (distanceE < resizeCornerArea && distanceN < resizeCornerArea) {
                        // NE
                        cursor = Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR);
                        resetResizeAxis(false, true, true, false);
                    } else if (distanceE < resizeCornerArea && distanceS < resizeCornerArea) {
                        // SE
                        cursor = Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR);
                        resetResizeAxis(false, false, true, true);
                    } else if (distanceW < resizeCornerArea && distanceS < resizeCornerArea) {
                        // NE
                        cursor = Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR);
                        resetResizeAxis(true, false, false, true);
                    } else if (distanceW < resizeArea) {
                        // W
                        cursor = Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR);
                        resetResizeAxis(true, false, false, false);
                    } else if (distanceN < resizeArea) {
                        // N
                        cursor = Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR);
                        resetResizeAxis(false, true, false, false);
                    } else if (distanceE < resizeArea) {
                        // E
                        cursor = Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR);
                        resetResizeAxis(false, false, true, false);
                    } else if (distanceS < resizeArea) {
                        // S
                        cursor = Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR);
                        resetResizeAxis(false, false, false, true);
                    }

                    if (!component.equals(hoveredComponent) || cursor != null) {

                        // Reset state of previous hovered Component
                        if (hoveredComponent != null) {
                            if (hoveredComponent instanceof MouseListener)
                                ((MouseListener) hoveredComponent).onExited(e);
                            if (hoveredComponent.getState() == State.HOVERED) hoveredComponent.setState(State.NONE);
                            else if (hoveredComponent.getState() == State.ACTIVE_HOVERED)
                                hoveredComponent.setState(State.ACTIVE);
                        }

                        if (cursor == null) {
                            // Update new hovered Component
                            hoveredComponent = component;
                            if (component instanceof MouseListener) ((MouseListener) component).onEntered(e);
                            if (hoveredComponent.getState() == State.NONE) hoveredComponent.setState(State.HOVERED);
                            else if (hoveredComponent.getState() == State.ACTIVE)
                                hoveredComponent.setState(State.ACTIVE_HOVERED);

                        } else {
                            hoveredComponent = null;
                        }
                    }

                    if (cursor != null) {
                        resizeComponent = component;
                    } else {
                        if (component instanceof MouseListener) {
                            // Calls onHovered
                            ((MouseListener) component).onHovered(e);
                        }
                        cursor = component.getDesign().getCursor(component);
                    }
                    jFrame.setCursor(cursor);

                }
            }

            // Every Component
            for (Component c : getComponents(true)) {
                if (c instanceof MouseListener) {
                    ((MouseListener) c).mouseMoved(e);
                }
            }

            render();

            lastMousePosition.setX(e.getX());
            lastMousePosition.setY(e.getY());
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            if (resizing) {
                // Resize component
                if (resizeAxis[0]) resizeComponent.resizeLeft(e.getX() - lastMousePosition.getRdX());
                if (resizeAxis[1]) resizeComponent.resizeTop(e.getY() - lastMousePosition.getRdY());
                if (resizeAxis[2]) resizeComponent.resizeRight(e.getX() - lastMousePosition.getRdX());
                if (resizeAxis[3]) resizeComponent.resizeBottom(e.getY() - lastMousePosition.getRdY());
                callRender();
                render();
            }


            lastMousePosition.setX(e.getX());
            lastMousePosition.setY(e.getY());
        }

        private void resetResizeAxis(boolean west, boolean north, boolean east, boolean south) {
            resizeAxis = new boolean[resizeAxis.length];
            resizeAxis[0] = west;
            resizeAxis[1] = north;
            resizeAxis[2] = east;
            resizeAxis[3] = south;
        }
    };
}
