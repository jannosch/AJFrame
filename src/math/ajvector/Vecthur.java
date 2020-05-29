package math.ajvector;

public abstract class Vecthur implements Cloneable {

    public abstract double angle();
    public abstract double length();

    // manipulation methods
    public abstract void add(Vecthur v);
    public abstract void add(double x, double y);
    public abstract void sub(Vecthur v);
    public abstract void sub(double x, double y);
    public abstract void mult(double s);
    public abstract void div(double s);
    public abstract void normalize();
    public abstract void limit(double l);

    // non-altering methods
    public abstract double dot(Vecthur v);

    // static methods
    static double dist(Vecthur v1, Vecthur v2) {
        return new Vector(v1.getX()-v2.getX(), v1.getY()-v2.getY()).length();
    }

    // getters and setters
    public abstract double getX();
    public abstract double getY();
    public abstract void setX(double x);
    public abstract void setY(double y);

    // rounded getters
    public abstract int getRdX();
    public abstract int getRdY();

    // print methods
    static int stdDigits = 2;

    /**@param n after-comma digits*/
    public abstract void print(int n);
    public abstract void print();

    /**@param n after-comma digits*/
    public abstract String getPrint(int n);
    public abstract String getPrint();

    /**@param n after-comma digits*/
    public abstract void printV(int n);
    public abstract void printV();


    public Vecthur clone() {
        try {
            return (Vecthur) super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public int hashCode() {
        String s = getX()+";"+getY();
        return s.hashCode();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Vecthur)
            return hashCode() == obj.hashCode();
        else
            return false;
    }
}
