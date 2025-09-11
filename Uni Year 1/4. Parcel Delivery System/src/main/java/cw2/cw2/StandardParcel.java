package cw2.cw2;
/**
 * Represents a standard parcel that extends the base Parcel class.
 */
public class StandardParcel extends Parcel
{

    private static final double COST = 1.25;
    private final double WEIGHT_KG;
    private static final String OUTSTRING = "Standard Parcel [from %s to %s] - ID: %d Price: %.2f Weight: %.2f";

    /**
     * Constructs a new StandardParcel object.
     *
     * @param sender    the processing facility where the parcel originates
     * @param recipient the processing facility where the parcel is to be delivered
     * @param weight    the weight of the parcel in kilograms
     */
    public StandardParcel(ProcessingFacility sender, ProcessingFacility recipient, double weight)
    {
        super(sender, recipient, 3); // Priority 3 for standard parcel
        this.WEIGHT_KG = weight;
    }

    /**
     * Processes the standard parcel by calculating its price based on weight.
     *
     * @param processingFacility the processing facility handling the parcel
     */
    @Override
    public void process(ProcessingFacility processingFacility)
    {
        StandardParcel.super.setPrice(COST * WEIGHT_KG);
    }

    /**
     * Generates a string representation of the standard parcel.
     *
     * @return the string containing information about the standard parcel
     */
    @Override
    public String toString()
    {
        return OUTSTRING.formatted(getSender().getAddress(), getRecipient().getAddress(), getID(), getPrice(), WEIGHT_KG);
    }
}