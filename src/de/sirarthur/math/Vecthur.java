package de.sirarthur.math;

public interface Vecthur {

    double angle();
    double length();

    // manipulation methods
    void add(Vecthur v);
    void add(double x, double y);
    void sub(Vecthur v);
    void sub(double x, double y);
    void mult(double s);
    void div(double s);
    void normalize();
    void limit(double l);

    // non-altering methods
    double dot(Vecthur v);

    // static methods
    static double dist(Vecthur v1, Vecthur v2) {
        return new Vector(v1.getX()-v2.getX(), v1.getY()-v2.getY()).length();
    }

    // getters and setters
    double getX();
    double getY();
    void setX(double x);
    void setY(double y);

    // rounded getters
    int getRdX();
    int getRdY();

    // print methods
    int stdDigits = 2;

    /**@param n after-comma digits*/
    void print(int n);
    void print();

    /**@param n after-comma digits*/
    String getPrint(int n);
    String getPrint();

    /**@param n after-comma digits*/
    void printV(int n);
    void printV();


    Vecthur clone();
    boolean equals(Object obj);
    int hashCode();
}
