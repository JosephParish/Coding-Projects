package cw2.cw2;

import java.util.ArrayList;

/**
 * Class to represent a ProcessingFacility object.
 * You will heavily edit this class and therefore no further comments have been added.
 * Ensure you add comments when submitting.
 */
public class ProcessingFacility
{
    private final int X;
    private final int Y;
    private final String ADDRESS;
    private ArrayList<ProcessingFacility> neighbours = new ArrayList<>();
    private final int ID;
    private static int nextId = 1;

    /**
     * Constructs a new ProcessingFacility with specified coordinates.
     *
     * @param x the x-coordinate of the facility
     * @param y the y-coordinate of the facility
     */
    public ProcessingFacility(int x, int y)
    {
        this.X = x;
        this.Y = y;
        this.ADDRESS = "X" + x + "Y" + y;

        ID = nextId;
        nextId++;
    }

    /**
     * Constructs a new ProcessingFacility with specified coordinates and initial neighbors.
     *
     * @param x         the x-coordinate of the facility
     * @param y         the y-coordinate of the facility
     * @param neighbours the initial list of neighboring facilities
     */
    public ProcessingFacility(int x, int y, ArrayList<ProcessingFacility> neighbours)
    {
        this.X = x;
        this.Y = y;
        this.ADDRESS = "X" + x + "Y" + y;
        this.neighbours = neighbours;

        ID = nextId;
        nextId++;
    }

    /**
     * Returns a string representation of the processing facility.
     *
     * @return a string containing the address and list of neighbor addresses
     */
    @Override
    public String toString()
    {
        StringBuilder returnString = new StringBuilder("Address:" + ADDRESS + " Neighbours:");
        for (int i = 0; i < neighbours.size(); i++)
        {
            if (i == neighbours.size() - 1)
            {
                returnString.append(neighbours.get(i).getAddress());
            }
            else
            {
                returnString.append(neighbours.get(i).getAddress()).append(" ");
            }
        }
        return returnString.toString();
    }

    /**
     * Returns the x-coordinate of the processing facility.
     *
     * @return the x-coordinate
     */
    public int getX()
    {
        return X;
    }

    /**
     * Returns the y-coordinate of the processing facility.
     *
     * @return the y-coordinate
     */
    public int getY()
    {
        return Y;
    }

    /**
     * Returns the unique ID of the processing facility.
     *
     * @return the ID
     */
    public int getId()
    {
        return ID;
    }

    /**
     * Returns the address of the processing facility.
     *
     * @return the address
     */
    public String getAddress()
    {
        return this.ADDRESS;
    }

    /**
     * Adds a neighboring processing facility.
     *
     * @param neighbour the neighboring facility to add
     */
    public void addNeighbour(ProcessingFacility neighbour)
    {
        this.neighbours.add(neighbour);
    }

    /**
     * Returns the list of neighboring processing facilities.
     *
     * @return the list of neighbors
     */
    public ArrayList<ProcessingFacility> getNeighbours()
    {
        return this.neighbours;
    }
}
