package de.sirarthur.math;

public class Vector3 {

    double x, y, z;

    // constructors
    /**
     * Creates new Vector that copies the values of <code>vCopy</code>.
     * @param vCopy
     */
    public Vector3(Vector3 vCopy) {
        this.x = vCopy.x;
        this.y = vCopy.y;
        this.z = vCopy.z;
    }

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Creates a Vector3 with values set to 0.
     *
     * If 1-3 booleans are passed, values will be randomized:
     *  (true -> all=random;
     *   false, true -> x=0, y=random, z=0)
     *
     * The random Vector will be at most <code>maxLen</code> long.
     * @param maxLen maximum vector length
     * @param b up to 3 boolean values
     */
    public Vector3(double maxLen, boolean ... b) {
        x = 0;
        y = 0;
        z = 0;

        if (b.length > 0) {
            if (b[0])
                x = Math.random()*maxLen * Math.signum(Math.random()-0.5);
            if ((b.length > 1 && b[1]) || (b.length == 1 && b[0]))
                y = Math.random()*maxLen * Math.signum(Math.random()-0.5);
            if ((b.length > 2 && b[2]) || (b.length == 1 && b[0]))
                z = Math.random()*maxLen * Math.signum(Math.random()-0.5);
            limit(maxLen);
        }
    }

    // vector property getters
    public double length() {
        return Math.sqrt(x*x + y*y + z*z);
    }

    // manipulation methods
    public void add(Vector3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public void sub(Vector3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public void mult(double s) {
        x *= s;
        y *= s;
        z *= s;
    }

    public void div(double s) {
        x /= s;
        y /= s;
        z /= s;
    }

    public void normalize() {
        double l = length();
        x /= l;
        y /= l;
        z /= l;
    }

    public void limit(double l) {
        if (length() > l) {
            normalize();
            mult(l);
        }
    }

    // non-altering methods
    /**
     * Dot product of this vector and a 2nd vector.
     * @param v another Vector3
     * @return double
     */
    public double dot(Vector3 v) {
        return (x*v.x + y*v.y + z*v.z);
    }

    /**
     * Angle between this vector and a 2nd vector.
     * @param v another Vector3
     * @return double
     */
    public double angle(Vector3 v) {
        return Math.acos(dot(v) / (length() * v.length()));
    }

    /**
     * NOT IMPLEMENTED YET
     * Creates cross product Vector.
     * @param v another Vector3
     * @return Vector3
     */
    public Vector3 cross(Vector3 v) {
        return null;
    }

    // static methods
    /**
     * Distance between to vectors/length of vector between v1 and v2 (i.e. v1.sub(v2)).
     * @param v1
     * @param v2
     * @return double
     */
    public static double dist(Vector3 v1, Vector3 v2) {
        Vector3 v = new Vector3(v1.x-v2.x, v1.y-v2.y, v1.z-v2.z);
        return v.length();
    }


    public int getRdX() {
        return (int) Math.round(x);
    }

    public int getRdY() {
        return (int) Math.round(y);
    }

    public int getRdZ() {
        return (int) Math.round(z);
    }

    // print methods
    /**@param n after-comma digits*/
    public void print(int n) {
        String s = "(%.2f|%.2f|%.2f)%n".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        System.out.printf(s, x, y, z);
    }

    /**@param n after-comma digits*/
    public String getPrint(int n) {
        String s = "(%.2f|%.2f|%.2f)".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        return String.format(s, x, y, z);
    }

    /**@param n after-comma digits*/
    public void printV(int n) {
        String s = "(%.2f|%.2f|%.2f; l=%.2f)%n".replaceAll("2", (n>=0?Integer.toString(n):"2"));
        System.out.printf(s, x, y, length());
    }
}
