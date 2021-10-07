package math.ajvector;

import java.util.ArrayList;

public class BVector extends Vector {

    ArrayList<Binding> bindings = new ArrayList<>();

    public BVector(BVector vCopy) {
        super(vCopy);
    }

    public BVector() {
    }

    public BVector(double x, double y) {
        super(x, y);
    }

    public BVector(Vector vCopy, ArrayList<Binding> bindings) {
        super(vCopy);
        this.bindings = bindings;
    }

    public BVector(ArrayList<Binding> bindings) {
        this.bindings = bindings;
    }

    public BVector(double x, double y, ArrayList<Binding> bindings) {
        super(x, y);
        this.bindings = bindings;
    }


    /** Binds this BVector to another Vecthur */
    public BVector bind(Vecthur vecthur) {
        bindings.add(new Binding(vecthur));
        return this;
    }

    /** Binds this BVector to another Vecthur.
     * Scales the values of Vecthur before applying
     */
    public BVector bind(Vecthur vecthur, double scaleX, double scaleY) {
        bindings.add(new Binding(vecthur, scaleX, scaleY));
        return this;
    }

    /** Binds this BVector to another Vecthur.
     * Bindingtype can be chosen
     */
    public BVector bind(Vecthur vecthur, int type) {
        bindings.add(new Binding(vecthur, type));
        return this;
    }

    /** Binds this BVector to another Vecthur.
     * Scales the values of Vecthur before applying.
     * Bindingtype can be chosen
     */
    public BVector bind(Vecthur vecthur, double scaleX, double scaleY, int type) {
        bindings.add(new Binding(vecthur, scaleX, scaleY, type));
        return this;
    }


    // Getters 'n Setters
    public ArrayList<Binding> getBindings() {
        return bindings;
    }

    @Override
    public double getX() {
        double xValue = super.getX();

        for (Binding binding : bindings) {
            xValue = binding.newX(xValue);
        }

        return xValue;
    }

    @Override
    public double getY() {
        double yValue = super.getY();

        for (Binding binding : bindings) {
            yValue = binding.newY(yValue);
        }

        return yValue;
    }
}
