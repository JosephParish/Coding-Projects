package cw2.cw2;

/**
 * Represents a medical parcel that extends the base Parcel class.
 */
public class MedicalParcel extends Parcel
{

    private static final double COST = 3;
    private int biohazardLevel;
    private static final String OUTSTRING = "Medical Parcel [from %s to %s] - ID: %d Price: %.2f Biohazard Level: %d";

    /**
     * Constructs a new MedicalParcel object.
     *
     * @param sender         the processing facility where the parcel originates
     * @param recipient      the processing facility where the parcel is to be delivered
     * @param biohazardLevel the biohazard level associated with the medical parcel
     */
    public MedicalParcel(ProcessingFacility sender, ProcessingFacility recipient, int biohazardLevel)
    {
        super(sender, recipient, 1); // Priority 1 for medical parcel
        this.biohazardLevel = biohazardLevel;
    }

    /**
     * Processes the medical parcel by increasing its biohazard level and recalculating its price.
     *
     * @param processingFacility the processing facility handling the parcel
     */
    @Override
    public void process(ProcessingFacility processingFacility)
    {
        biohazardLevel++;
        setPrice(COST * biohazardLevel);
    }

    /**
     * Generates a string representation of the medical parcel.
     *
     * @return the string containing information about the medical parcel
     */
    @Override
    public String toString()
    {
        return OUTSTRING.formatted(getSender().getAddress(), getRecipient().getAddress(), getID(), getPrice(), biohazardLevel);
    }
}
