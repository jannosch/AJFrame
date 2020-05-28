package de.sirarthur.math;

public class Vector implements Vecthur {

    double x, y;

    // constructors
    /**
     * Creates new Vector that copies the values of <code>vCopy</code>.
     * @param vCopy vector to copy
     */
    public Vector(Vector vCopy) {
        this.x = vCopy.x;
        this.y = vCopy.y;
    }

    public Vector() {
        x = 0;
        y = 0;
    }

    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Creates unit-length <code>Vector</code> calculated from given angle (in Radians).
     * With the y-axis going down, the increasing angle describes a counter-clockwise rotation.
     * @param a angle
     * @return Vector
     */
    public static Vector fromAngle(double a) {
        double x = Math.cos(a);
        double y = Math.sin(a);
        return new Vector(x,y);
    }

    // vector property getters
    public double angle() {
        double a;
        a = Math.atan(y/x);
        if (x < 0)
            a += Math.PI;
        if (a < 0)
            a = Math.PI*2 + a;
        return a;
    }

    public double length() {
        return Math.sqrt(x*x+y*y);
    }

    // manipulation methods
    public void add(Vecthur v) {
        x += v.getX();
        y += v.getY();
    }
    public void add(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void sub(Vecthur v) {
        x -= v.getX();
        y -= v.getY();
    }
    public void sub(double x, double y) {
        this.x -= x;
        this.y -= y;
    }

    public void mult(double s) {
        x *= s;
        y *= s;
    }

    public void div(double s) {
        x /= s;
        y /= s;
    }

    public void normalize() {
        double l = length();
        x /= l;
        y /= l;
    }

    public void limit(double l) {
        if (length() > l) {
            normalize();
            mult(l);
        }
    }

    // non-altering methods
    /**
     * Dot product of this vector and a 2nd Vecthur.
     * @param v another Vecthur
     * @return double
     */
    public double dot(Vecthur v) {
        return (x*v.getX() + y*v.getY());
    }

    // getters and setters
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    // rounded getters
    public int getRdX() {
        return (int) Math.round(x);
    }

    public int getRdY() {
        return (int) Math.round(y);
    }

    // print methods
    public static int stdDigits = Vecthur.stdDigits;

    public void print(int n) {
        String s = "(%.2f|%.2f)%n".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        System.out.printf(s, x, y);
    }
    public void print() {
        if (stdDigits < 0)
            stdDigits = 0;
        print(stdDigits);
    }

    public String getPrint(int n) {
        String s = "(%.2f|%.2f)".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        return String.format(s, x, y);
    }
    public String getPrint() {
        if (stdDigits < 0)
            stdDigits = 0;
        return getPrint(stdDigits);
    }

    public void printV(int n) {
        String s = "(%.2f|%.2f; α=%.2f*PI; l=%.2f)%n".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        System.out.printf(s, x, y, angle(), length());
    }
    public void printV() {
        if (stdDigits < 0)
            stdDigits = 0;
        printV(stdDigits);
    }


    @Override
    public Vecthur clone() {
        return new Vector(x, y);
    }

    @Override
    public int hashCode() {
        String s = x+";"+y;
        return s.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return getX() == v.getX() && getY() == v.getY();
        } else if (obj instanceof PVector) {
            PVector v = (PVector) obj;
            return getX() == v.getX() && getY() == v.getY();
        } else
            return super.equals(obj);
    }
}
