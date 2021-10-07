package math.ajvector;

public class Binding {

    public static final int ADD = 0; // Just adds the bindVector * scale to Vector
    public static final int SUB = 1; // Substracts the bindVector * scale from Vector
    public static final int MUL = 2; // Multiplies the Vector times bindVector * scale
    public static final int DIV = 3; // Divides the Vector by bindVector * scale

    private Vecthur vecthur;
    private double scaleX = 1;
    private double scaleY = 1;
    private int type = ADD;

    /*
    Bind a BVector to another Vector.
     */

    public Binding(Vecthur vecthur) {
        this.vecthur = vecthur;
    }

    public Binding(Vecthur vecthur, double scaleX, double scaleY) {
        this.vecthur = vecthur;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public Binding(Vecthur vecthur, int type) {
        this.vecthur = vecthur;
        this.type = type;
    }

    public Binding(Vecthur vecthur, double scaleX, double scaleY, int type) {
        this.vecthur = vecthur;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.type = type;
    }

    // Calculates new X-Coordinate for the binded vector
    public double newX(double oldX) {
        if (type == ADD) return oldX + vecthur.getX() * scaleX;
        if (type == SUB) return oldX - vecthur.getX() * scaleX;
        if (type == MUL) return oldX * vecthur.getX() * scaleX;
        if (type == DIV) return oldX / (vecthur.getX() * scaleX);
        return -1;
    }

    // Calculates new Y-Coordinate for the binded vector
    public double newY(double oldY) {
        if (type == ADD) return oldY + vecthur.getY() * scaleY;
        if (type == SUB) return oldY - vecthur.getY() * scaleY;
        if (type == MUL) return oldY * vecthur.getY() * scaleY;
        if (type == DIV) return oldY / (vecthur.getY() * scaleY);
        return -1;
    }


}
