package cw2.cw2;

/**
 * Abstract class to represent a Parcel object.
 * This class serves as a base for specific types of parcels.
 */
public abstract class Parcel
{

    private final ProcessingFacility sender;
    private final ProcessingFacility recipient;
    private double price;
    private final int priority;
    private final int ID;
    private static int nextId = 1;

    /**
     * Constructs a new Parcel object.
     *
     * @param sender    the processing facility where the parcel originates
     * @param recipient the processing facility where the parcel is to be delivered
     * @param priority  the priority level of the parcel
     */
    public Parcel(ProcessingFacility sender, ProcessingFacility recipient, int priority)
    {
        this.sender = sender;
        this.recipient = recipient;
        this.priority = priority;
        this.ID = nextId++;
    }

    /**
     * Returns the sender processing facility of the parcel.
     *
     * @return the sender processing facility
     */
    public ProcessingFacility getSender()
    {
        return sender;
    }

    /**
     * Returns the recipient processing facility of the parcel.
     *
     * @return the recipient processing facility
     */
    public ProcessingFacility getRecipient()
    {
        return recipient;
    }

    /**
     * Returns the priority level of the parcel.
     *
     * @return the priority level
     */
    public int getPriority()
    {
        return priority;
    }

    /**
     * Returns the unique id of the parcel.
     *
     * @return the unique id
     */
    public int getID()
    {
        return ID;
    }

    /**
     * Returns the current price of the parcel.
     *
     * @return the price of the parcel
     */
    public double getPrice()
    {
        return price;
    }

    /**
     * Sets the price of the parcel.
     *
     * @param price the price to set
     */
    protected void setPrice(double price)
    {
        this.price = price;
    }

    /**
     * Abstract method to process the parcel at a specified processing facility.
     * This method should be implemented by specific parcel types.
     *
     * @param processingFacility the processing facility where the parcel is being processed
     */
    public abstract void process(ProcessingFacility processingFacility);
}
