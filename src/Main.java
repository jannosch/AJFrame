import ajframe.AJFrame;
import ajframe.components.Component;
import ajframe.design.Theme;
import ajframe.design.attributes.Fill;
import math.ajvector.BVector;

import java.awt.*;

public class Main {

    public static void main(String[] args) {

        AJFrame ajFrame = new AJFrame(new BVector(1200, 800));
        Component component = new Component(new BVector(100, 100), new BVector(100, 100).bind(ajFrame.getSize(), 0.5, 0.3));
        Component component1 = new Component(new BVector(0, 0).bind(component.getPosition()).bind(component.getSize(), 1, 0), new BVector(50, 50));
        component1.getDesign().addDesignAttribute(new Fill(new Color(80, 30, 110)));
        Component component2 = new Component(new BVector(0, 0).bind(component1.getPosition()).bind(component1.getSize(), 1, 0), new BVector(50, 50));
        component2.getDesign().addDesignAttribute(new Fill(new Color(80, 110, 30)));
        ajFrame.add(component);
        ajFrame.add(component1);
        ajFrame.add(component2);

    }

}
