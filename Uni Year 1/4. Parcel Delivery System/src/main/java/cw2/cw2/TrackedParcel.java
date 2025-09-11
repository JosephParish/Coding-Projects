package cw2.cw2;

import java.util.ArrayList;

/**
 * Represents a tracked parcel that extends the base Parcel class.
 */
public class TrackedParcel extends Parcel
{

    private static final double COST = 1.5;
    private String trackingLocation;
    private ArrayList<ProcessingFacility> locations;
    private String outString = "Tracked Parcel [from %s to %s] - ID: %d Price: %.2f Locations: ";
    /**
     * Constructs a new TrackedParcel object.
     *
     * @param sender    the processing facility where the parcel originates
     * @param recipient the processing facility where the parcel is to be delivered
     */
    public TrackedParcel(ProcessingFacility sender, ProcessingFacility recipient)
    {
        super(sender, recipient, 2); // Priority 2 for tracked parcel

        trackingLocation = "";
        locations = new ArrayList<>();
    }

    /**
     * Processes the tracked parcel by adding the processing facility to its tracking locations,
     * updating the tracking information, and recalculating its price.
     *
     * @param processingFacility the processing facility handling the parcel
     */
    @Override
    public void process(ProcessingFacility processingFacility)
    {
        locations.add(processingFacility);

        trackingLocation = trackingLocation + processingFacility.toString() + ", ";

        setPrice(COST * locations.size());
    }

    /**
     * Generates a string representation of the tracked parcel.
     *
     * @return the string containing information about the tracked parcel
     */
    @Override
    public String toString()
    {
        String returnString =
                outString.formatted(getSender().getAddress(), getRecipient().getAddress(), getID(), getPrice());
        for (int i = 0; i < locations.size(); i++)
        {
            if (i == locations.size() - 1)
            {
                returnString += locations.get(i).getAddress();
            }
            else
            {
                returnString += locations.get(i).getAddress() + ",";
            }
        }
        return returnString;
    }
}