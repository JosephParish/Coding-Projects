public class Moon
{
    /*   This is the Moon class which holds all the Moons information.
     *   It contains several methods to output information on the Moon,
     *   as well as two Constructors for a Moon.
     *
     *   Methods:
     *       - equals()
     *       - likelyComp()
     *       - getExtended()
     *       - getName()
     *       - getDistance()
     *       - getPeriod()
     *       - getMass()
     *       - getDiameter()
     *       - getDensity()
     */

    private static final float ICE_COMPOSITION = 0.9F;
    private static final int ROCK_COMPOSITION = 2;
    private static final String M_ICE = "mostly ice";
    private static final String M_ROCK = "mostly rock";
    private static final String MIXTURE = "a mixture of rock and ice";
    private static final int DENSITY_CONSTANT = 4;
    private static final double MILLI_TO_GRAM = 10E2; // Used to convert the measurement from milligram to gram
    private final boolean extended; // If the Moon has recorded mass and diameter
    private final String name;
    private final double distance;
    private double mass;
    private double diameter;
    private double density;

    //  Constructor for Moons class with mass and diameter (Extended)

    public Moon(String name, double distance, double mass, double diameter)
    {
        this.extended = true;
        this.name = name;
        this.distance = distance;

        this.mass = mass;
        this.diameter = diameter;
        double radius = diameter / 2;
        this.density = mass/(Math.PI * DENSITY_CONSTANT / (DENSITY_CONSTANT-1) * (radius * radius * radius))
                        * MILLI_TO_GRAM;
    }

    //  Constructor for Moons class without mass and diameter (not Extended)

    public Moon(String name, double distance)
    {
        this.extended = false;
        this.name = name;
        this.distance = distance;
    }

    //  Checks if two Moons are the same by comparing the name and distance to a Planet of both Moons

    public boolean equals(Moon inputmoon)
    {
        if (inputmoon.getName().equals(this.name))
        {
            if (inputmoon.getDistance() == (this.distance))
            {
                return true;
            }
        }
        return false;
    }


    //  Determines what range the density falls into and outputs its likely composition

    public String likelyComp()
    {
        if (this.density < ICE_COMPOSITION)
        {
            return M_ICE;
        }
        else if (this.density > ROCK_COMPOSITION)
        {
            return M_ROCK;
        }
        else
        {
            return MIXTURE;
        }
    }

    //  Returns whether the Moon Object was initialized with mass and diameter

    public boolean getExtended()
    {
        return extended;
    }

    //  Returns the name of the Moon

    public String getName()
    {
        return this.name;
    }

    //  Returns the distance of the moon to the planet

    public int getDistance()
    {
        return (int) this.distance;
    }

    //  Calculates and returns the orbital period of the moon

    public double getOrbitalPeriod(int correctionFactor)
    {
        return (Math.sqrt(distance * distance * distance) / correctionFactor);
    }

    //  Returns mass of the Moon

    public double getMass()
    {
        return this.mass;
    }

    //  Returns diameter of the Moon

    public double getDiameter()
    {
        return diameter;
    }

    //  Returns density of the Moon

    public double getDensity()
    {
        return density;
    }
}
