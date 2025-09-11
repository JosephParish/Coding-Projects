package cw2.cw2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Class to hold data that is added to the "database".
 * You may add methods to this class.
 */
public class Data
{

    /**
     * Attributes to save our data to the "database"
     */
    private static ArrayList<ProcessingFacility> processingFacilities = new ArrayList<>();
    private static Stack<Parcel> parcels = new Stack<>();
    private static Stack<Parcel> processedParcels = new Stack<>();

    /**
     * Method to return the Stack of parcels
     * @return parcels which is a stack of parcels
     */
    public static Stack<Parcel> getParcels()
    {
        return parcels;
    }

    /**
     * Method to return the processing facilities
     * @return array list of processing facilities
     */
    public static ArrayList<ProcessingFacility> getProcessingFacilities()
    {
        return processingFacilities;
    }

    /**
     * Method to return the processed parcels.
     * @return stack of parcels called completed deliveries.
     */
    public static Stack<Parcel> getProcessedParcels()
    {
        return processedParcels;
    }

    /**
     * DO NOT EDIT ANY CODE ABOVE THIS COMMENT. You may need to write additional methods below.
     */

    /**
     * Finds a processing facility by its address.
     *
     * @param address the address of the processing facility to find
     * @return the ProcessingFacility object if found, or null if not found
     */
    public static ProcessingFacility findProcessingFacilities(String address)
    {
        for (ProcessingFacility facility : processingFacilities)
        {
            if (facility.getAddress().equals(address))
            {
                return facility;
            }
        }
        return null;
    }

    /**
     * Finds a processing facility by its ID.
     *
     * @param id the ID of the processing facility to find
     * @return the ProcessingFacility object if found, or null if not found
     */
    public static ProcessingFacility findProcessingFacilities(int id)
    {
        for (ProcessingFacility facility : processingFacilities)
        {
            if (facility.getId() == id)
            {
                return facility;
            }
        }
        return null;
    }

    /**
     * Reads processing facilities from a file and adds them to the list.
     */
    public static void readProcessingFacilities()
    {
        File inputFile = new File("processingFacilities.txt");
        try (Scanner input = new Scanner(inputFile))
        {

            while (input.hasNextLine())
            {
                int X = input.nextInt();
                int Y = input.nextInt();
                ProcessingFacility processingFacility = new ProcessingFacility(X, Y);
                processingFacilities.add(processingFacility);
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads connections between processing facilities from a file and establishes links.
     */
    public static void readConnections()
    {
        File inputFile = new File("connections.txt");
        try (Scanner input = new Scanner(inputFile))
        {
            while (input.hasNextLine())
            {
                String inputString = input.nextLine();
                String[] words = inputString.split("\\s");
                String address = words[0];
                String address2 = words[1];
                ProcessingFacility facility1 = findProcessingFacilities(address);
                ProcessingFacility facility2 = findProcessingFacilities(address2);
                if (facility1 != null && facility2 != null)
                {
                    facility1.addNeighbour(facility2);
                    facility2.addNeighbour(facility1);
                }
            }
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads parcels from a CSV file, creates Parcel objects, and adds them to the list sorted by priority.
     */
    public static void readParcels()
    {
        try (Scanner input = new Scanner(new File("parcels.csv")))
        {
            List<Parcel> priorityStack = new ArrayList<>();
            while (input.hasNextLine())
            {
                String inputString = input.nextLine();
                String[] words = inputString.split(",");
                String senderAddress = words[0];
                String recipientAddress = words[1];
                String type = words[2];

                ProcessingFacility sender = findProcessingFacilities(senderAddress);
                ProcessingFacility recipient = findProcessingFacilities(recipientAddress);
                if (sender != null && recipient != null)
                {
                    Parcel parcel = createParcel(type, sender, recipient, words);
                    if (parcel != null)
                    {
                        priorityStack.add(parcel);
                    }
                }
            }
            priorityStack.sort(Comparator.comparingInt(Parcel::getPriority).reversed());
            parcels.addAll(priorityStack);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a parcel object based on the type and input details.
     *
     * @param type     the type of parcel
     * @param sender   the sending processing facility
     * @param recipient the receiving processing facility
     * @param details  details array from CSV
     * @return the created Parcel object
     */
    private static Parcel createParcel(String type, ProcessingFacility sender, ProcessingFacility recipient,
                                       String[] details)
    {
        switch (type)
        {
            case "Medical":
                int detailsM = Integer.parseInt(details[3]);
                return new MedicalParcel(sender, recipient, detailsM);
            case "Tracked":
                return new TrackedParcel(sender, recipient);
            case "Standard":
                double detailsS = Double.parseDouble(details[3]);
                return new StandardParcel(sender, recipient, detailsS);
            default:
                return null;
        }
    }

    /**
     * Adds a processing facility to the list of processing facilities.
     *
     * @param p the processing facility to add
     */
    public void addProcessingFacility(ProcessingFacility p)
    {
        processingFacilities.add(p);
    }
}
