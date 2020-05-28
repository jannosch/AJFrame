package ajframe.design;

import math.ajvector.Vecthur;

public abstract class Shape {

    // x & y sind relativ zum Komponenten (obere linke Ecke: (0, 0))
    public abstract boolean isOnPosition(Vecthur testPos, Vecthur size);

    // Rechteck
    public static class Rect extends Shape {

        @Override
        public boolean isOnPosition(Vecthur testPos, Vecthur size) {
            return true;
        }
    }

    // Abgerundetes Rechteck
    public static class Rounded extends Shape {
        private double rounded;

        public Rounded(double rounded) {
            this.rounded = rounded;
        }

        public double getRounded() {
            return rounded;
        }

        public void setRounded(double rounded) {
            this.rounded = rounded;
        }

        @Override
        public boolean isOnPosition(Vecthur testPos, Vecthur size) {
            double x = testPos.getX();
            double y = testPos.getY();
            double width = size.getX();
            double height = size.getY();
            if ((rounded <= x && x <= width - rounded) || (rounded <= y && y <= height - rounded)) return true;
            if (x > rounded) x = x - width + 2 * rounded;
            if (y > rounded) y = y - height + 2 * rounded;
            width = 2 * rounded;
            height = 2 * rounded;
            return Assistant.isInCircle(x, y, width, height);
        }
    }

    // Oval
    public static class Oval extends Shape {

        @Override
        public boolean isOnPosition(Vecthur testPos, Vecthur size) {
            return Assistant.isInCircle(testPos.getX(), testPos.getY(), size.getX(), size.getY());
        }
    }


    private static class Assistant {

        static boolean isInCircle(double x, double y, double width, double height) {
            x -= width / 2;
            y -= height / 2;

            // Testet ob innerhalb einer Kreisgleichung
            return java.lang.Math.abs(y) <= (height / 2) * java.lang.Math.sqrt(1- java.lang.Math.pow(-x/(width / 2), 2));
        }
    }
}

