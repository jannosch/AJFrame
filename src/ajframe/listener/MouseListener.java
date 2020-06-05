package ajframe.listener;

import java.awt.event.MouseEvent;

public interface MouseListener {

    /** this.order = execution.order */

    // Fires when Mouse pressed down while hovering
    void onPressed(MouseEvent mouseEvent);

    // Fires when Mouse pressed
    void mousePressed(MouseEvent mouseEvent);

    // Fires when Mouse released while hovering
    void onReleased(MouseEvent mouseEvent);

    // Fires when Mouse pressed and released on this component
    void onClicked(MouseEvent mouseEvent);

    // Fires when Mouse released
    void mouseReleased(MouseEvent mouseEvent);




    // Fires when mouse enters Component
    void onEntered(MouseEvent mouseEvent);

    // Fires when mouse leaves Component
    void onExited(MouseEvent mouseEvent);

    // Fires when mouse moves while hover
    void onHovered(MouseEvent mouseEvent);

    // Fires when Mouse moves
    void mouseMoved(MouseEvent mouseEvent);

}
