public class Planet {
    double xxPos; // current x position
    double yyPos; // current y position
    double xxVel; // current x velocity
    double yyVel; // current y velocity
    double mass; // mass
    String imgFileName; // name of image

    public static final double G = 6.67E-11;

    /** constructor 1 */
    public Planet(double xP, double yP, double xV,double yV, double m, String img) {
        xxPos = xP;
        yyPos = yP;
        xxVel = xV;
        yyVel = yV;
        mass = m;
        imgFileName = img;
    }

    /** constructor 2 */
    public Planet(Planet p) {
        xxPos = p.xxPos;
        yyPos = p.yyPos;
        xxVel = p.xxVel;
        yyVel = p.yyVel;
        mass = p.mass;
        imgFileName = p.imgFileName;
    }

    /** method to calculate distance between current Planet and another Planet */
    public double calcDistance(Planet p) {
        return Math.sqrt((xxPos-p.xxPos)*(xxPos-p.xxPos)
                        + (yyPos-p.yyPos)*(yyPos-p.yyPos));
    }

    /** calculate the total force exerted on this planet by the given planet. */
    public double calcForceExertedBy(Planet p) {
        return G * mass * p.mass / (calcDistance(p)*calcDistance(p));
    }

    /** calculate the force exerted on this planet in X by the given planet. */
    public double calcForceExertedByX(Planet p) {
        double dx = p.xxPos - xxPos;
        return calcForceExertedBy(p) * dx / calcDistance(p);
    }

    /** calculate the force exerted on this planet in Y by the given planet. */
    public double calcForceExertedByY(Planet p) {
        double dy = p.yyPos - yyPos;
        return calcForceExertedBy(p) * dy / calcDistance(p);
    }

    /** calculate net force in X exerted by all planets in given array */
    public double calcNetForceExertedByX(Planet[] ps) {
        int k;
        double result;
        for (k = 0, result = 0; k < ps.length; k += 1) {
            if (!this.equals(ps[k])) {
            result += calcForceExertedByX(ps[k]);
            }
        }
        return result;
    }

    /** calculate net force in Y exerted by all planets in given array */
    public double calcNetForceExertedByY(Planet[] ps) {
        int k;
        double result;
        for (k = 0, result = 0; k < ps.length; k += 1) {
            if (!this.equals(ps[k])) {
            result += calcForceExertedByY(ps[k]);
            }
        }
        return result;
    }

    /** update position and velocity for Planet given force and time that the force was applied*/
    public void update(double dt, double fX, double fY) {
        // acceleration
        double ax = fX / mass;
        double ay = fY / mass;
        // new velocity
        xxVel += ax * dt;
        yyVel += ay * dt;
        // new position
        xxPos += xxVel * dt;
        yyPos += yyVel * dt;
    }

    /* draw the planet */
    public void draw() {
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
        // StdDraw.show();
    }
}
