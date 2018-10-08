public class NBody {
    /** read radius of the universe in given file */
    public static double readRadius(String filename) {
        In in = new In(filename);
        // read number of planets
        int n = in.readInt();
        // read and return radius of the universe
        return in.readDouble();
    }

    /** read data file and return an array of planets listed in the file */
    public static Planet[] readPlanets(String filename) {
        In in = new In(filename);
        // read number of planets
        int n = in.readInt();
        System.out.println("" + n);
        // read radius of the universe
        double r = in.readDouble();
        System.out.println("" + r);
        // read Planets
        Planet[] result = new Planet[n];
        int k = 0;
        while(k < n) {
            // read a line for planet parameters
            double xxPos = in.readDouble();
            double yyPos = in.readDouble();
            double xxVel = in.readDouble();
            double yyVel = in.readDouble();
            double mass = in.readDouble();
            String imgFileName = in.readString();
            // put in planet array
            result[k] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
            // increment k
            k += 1;
        }
        return result;
    }

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];

        // Planets
        Planet[] planets = readPlanets(filename);
        // universe radius
        double r = readRadius(filename);

        /* Draw background */
        String bkgImage = "./images/starfield.jpg";
        StdDraw.setScale(-r, r);
        StdDraw.clear();
        StdDraw.picture(0, 0, bkgImage, 2*r, 2*r);

        /* Draw planets */
        int n = planets.length; // number of planets
        for (int i = 0; i < n; i += 1) {
            planets[i].draw();
        }

        /* Animation */
        double t = 0;

        /* refreshing loop */
        while (t < T) {
            double[] xForces = new double[n];
            double[] yForces = new double[n];
            /* calculate net forces for each planet */
            for (int i = 0; i < n; i += 1) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            }
            /* update each planet's status */
            for (int i = 0; i < n; i += 1) {
                planets[i].update(dt, xForces[i], yForces[i]);
            }
            /* draw all planets */
            for (int i = 0; i < n; i += 1) {
                planets[i].draw();
            }
            /* Pause for certain time */
            StdDraw.show(10);
            /* increment t */
            t += dt;
        }

        /* print final state of the universe */
        System.out.format("%d%n", n);
        System.out.format("%7.2e%n", r);
        for (int i = 0; i < n; i += 1) {
            System.out.format("%11.4e  %11.4e  %11.4e  %11.4e  %11.4e %12s%n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel,
            planets[i].mass, planets[i].imgFileName);
        }
    }
}
