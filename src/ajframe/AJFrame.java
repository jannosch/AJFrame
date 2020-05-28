package ajframe;

import ajframe.components.Component;
import ajframe.components.Holder;
import ajframe.design.Design;
import ajframe.design.Designable;
import ajframe.design.attributes.Fill;
import de.sirarthur.math.PVector;
import de.sirarthur.math.Vector;

import javax.swing.*;
import java.awt.*;

public class AJFrame extends Holder {

    private JFrame jFrame = new JFrame(); // JFrame holding the JPanel
    private Panel jPanel = new Panel(); // Panel in which everything is Painted
    private Vector offset; // OS-Specific
    private boolean handmadeRender;

    public AJFrame(PVector size) {
        super(size, new PVector());
        init();
    }

    private void init() {
        // Sets Designable
        designable = new Design(new Fill(new Color(0x252628)));

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

        // Shows the Frame the first time
        render();
    }

    @Override
    protected void callRender(Component component) {
        renderList.add(component);

        // TODO: Time to frames
        render();
    }

    public void render() {
        handmadeRender = true;
        jPanel.repaint();
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
        public void paint(Graphics graphics) {
            Graphics2D g = (Graphics2D)graphics;

            // Updates size
            AJFrame.this.updateSize();

            // Activates Antialiasing
            g.setRenderingHints(new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            ));

            render(g, new Vector(), designable, !handmadeRender);

            handmadeRender = false;
        }
    }


    @Override
    public void setSize(PVector size) {
        super.setSize(size);
        jFrame.setSize(size.getRdX() + offset.getRdX(), size.getRdY() + offset.getRdY());
    }
}
