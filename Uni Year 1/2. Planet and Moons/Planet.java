import java.text.DecimalFormat;
import java.util.ArrayList;

/*  Response to feedback
 *  1 - Shortened and structured comments
 *  2 - Made class names singular
 *  3 - Didn't declare a class within a class, nor make them static
 *  4 - Made strings constants at top of code
 */

public class Planet
{
   /*   This is the Planet class which holds all the information regarding the Planet and its Moons.
    *   It contains several methods to output information on the Planet and its Moons,
    *   as well as methods to add Moons to the planet and a constructor for the Planet class.
    *
    *   Methods:
    *       - addMoon
    *       - getMoonByName
    *       - furthest
    *       - closest
    *       - toString
    */

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");
    private static final String OUTSTRING = "Planet %s has moons:\n";
    private static final String OUT_EXTENDED = "%s%s is %d km from it's planet, orbits in %s days,"
                                               + " has a mass of %s * 10e15 kg, a diameter of %d km,"
                                               + " and a density of %s g/cm^2 - it is probably %s\n";
    private static final String OUT = "%s%s is %d km from its planet, and orbits in %s days\n";
    private final String name;
    private final int correctionFactor;
    private final ArrayList<Moon> moons = new ArrayList<>();

    //  Constructor for Planets class

    Planet(String name, int correctionFactor)
    {
        this.name = name;
        this.correctionFactor = correctionFactor;
    }

    //  Adds new Moons (that are Extended to have mass and diameter) to the arraylist of moons

    public void addMoon(String name, double distance, double mass, double diameter)
    {
        moons.add(new Moon(name, distance, mass, diameter));
    }

    //  Adds new Moons to the arraylist of moons

    public void addMoon(String name, double distance)
    {
        moons.add(new Moon(name, distance));
    }

    //  Retrieves Moons by name from arraylist of moons

    public Moon getMoonByName(String name)
    {
        for (Moon moon: moons)
        {
            if (moon.getName().equals(name))
            {
                return moon;
            }
        }
        return null;
    }

    //  Retrieves Moon that is furthest from the Planet

    public Moon furthest()
    {
        Moon tempMoon = moons.get(0);
        for (Moon moon: moons)
        {
            if (moon.getDistance() > tempMoon.getDistance())
            {
                tempMoon = moon;

            }
        }
        return tempMoon;
    }

    //  Retrieves Moon that is closest to the Planet

    public Moon closest()
    {
        Moon tempMoon = moons.get(0);
        for (Moon moon: moons)
        {
            if (moon.getDistance() < tempMoon.getDistance())
            {
                tempMoon = moon;

            }
        }
        return tempMoon;
    }

    //  Overrides the Object toString() method to one that outputs the Planets' Moons with all their information

    @Override
    public String toString()
    {
        String outString = OUTSTRING.formatted(name);
        for (Moon moon: moons)
        {
            if (moon.getExtended()) // If the Moon contains mass and diameter values
            {
                outString = assembleExtendedOutString(outString, moon);
            }
            else
            {
                outString = assembleOutString(outString, moon);
            }
        }
        return outString;
    }

    // Assembles the outString variable of toString extended

    private String assembleExtendedOutString(String outString, Moon moon)
    {
        return (OUT_EXTENDED).formatted(outString, moon.getName(), moon.getDistance(),
                    DECIMAL_FORMAT.format(moon.getOrbitalPeriod(correctionFactor)),
                    DECIMAL_FORMAT.format(moon.getMass()),
                    Math.round(moon.getDiameter()), DECIMAL_FORMAT.format(moon.getDensity()),
                    moon.likelyComp());
    }

    // Assembles the outString variable of toString

    private String assembleOutString(String outString, Moon moon)
    {
        return (OUT).formatted(outString, moon.getName(), moon.getDistance(),
                    DECIMAL_FORMAT.format(moon.getOrbitalPeriod(correctionFactor)));
    }
}
