package de.sirarthur.math;

public class TEST {

    public static void main(String[] args) {
        PVector.stdDigits = 0;

        PVector u = new PVector(1, true, 2, 2);

        PVector v = new PVector(u,2,2);
        PVector w = new PVector(v, 2, 2);

        u.print();
        v.print();
        w.print();

        System.out.println(Vecthur.dist(new Vector(1,1), new PVector(0,0)));

        u.add(3,0);

        System.out.println();
        u.print();
        v.print();
        w.print();
    }
}
