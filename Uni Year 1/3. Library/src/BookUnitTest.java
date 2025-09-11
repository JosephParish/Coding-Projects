/**
 * <p>A unit test for the Book class</p>
 *
 * @author Joseph Parish
 * @version 1.0.7
 */
public class BookUnitTest
{
    private static boolean t1 = false;
    private static boolean t2 = false;
    private static boolean t3 = false;
    private static boolean t4 = false;

    /**
     * The method that the Java Virtual Machine (JVM) calls and starts executing and where our unit test takes place.
     */
    public static void main(String[] args)
    {
        testConstructorAndGetters();
        testSetters();
        testBookID();
        testToString();

        if(t1 && t2 && t3 && t4)
        {
            System.out.println("UnitTest passed for tests 1-4");
        }
        else
        {
            System.out.println("UnitTest failed for tests 1-4");
        }
    }

    /**
     * <p>Tests the constructors and get methods to see if they provide valid output</p>
     *
     * <p>Tests whether these Book class methods work as intended:</p>
     * <ol>
     *     <li>toString</li>
     *     <li>getAuthorFamilyNames</li>
     *     <li>getAuthorRestOfNames</li>
     *     <li>getTitle</li>
     *     <li>getGenre</li>
     *     <li>getBookID</li>
     *     <li>getShortString</li>
     * </ol>
     * <p>and if they do t1 is set to true</p>
     */
    public static void testConstructorAndGetters()
    {
        boolean t1_1 = false;
        boolean t1_2 = false;
        boolean t1_3 = false;
        boolean t1_4 = false;
        boolean t1_5 = false;
        boolean t1_6 = false;
        boolean t1_7 = false;
        boolean t1_8 = false;
        String expected1 = "1231003825FANTASY";
        String expected2 = "nullnullnull1003826null";
        String expected3 = "1";
        String expected4 = "2";
        String expected5 = "3";
        Genre expected6 = Genre.FANTASY;
        int expected7 = 1003825;
        String expected8 = "123";
        Book b1 = new Book("1","2","3",Genre.FANTASY);
        Book b1_1 = new Book();

        System.out.println("--- Testing The Constructor And Getter Methods ---");

        System.out.println("Expected: " + expected1);
        System.out.println("Actual: " + b1.toString());
        if(expected1.equals(b1.toString()))
        {
            t1_1 = true;
        }
        System.out.println("passed: " + t1_1 + "\n");

        System.out.println("Expected: " + expected2);
        System.out.println("Actual: " + b1_1.toString());
        if(expected2.equals(b1_1.toString()))
        {
            t1_2 = true;
        }
        System.out.println("passed: " + t1_2 + "\n");

        System.out.println("Expected: " + expected3);
        System.out.println("Actual: " + b1.getAuthorFamilyNames());
        if(expected3.equals(b1.getAuthorFamilyNames()))
        {
            t1_3 = true;
        }
        System.out.println("passed: " + t1_3 + "\n");

        System.out.println("Expected: " + expected4);
        System.out.println("Actual: " + b1.getAuthorRestOfName());
        if(expected4.equals(b1.getAuthorRestOfName()))
        {
            t1_4 = true;
        }
        System.out.println("passed: " + t1_4 + "\n");

        System.out.println("Expected: " + expected5);
        System.out.println("Actual: " + b1.getTitle());
        if(expected5.equals(b1.getTitle()))
        {
            t1_5 = true;
        }
        System.out.println("passed: " + t1_5 + "\n");

        System.out.println("Expected: " + expected6);
        System.out.println("Actual: " + b1.getGenre());
        if(expected6 == b1.getGenre())
        {
            t1_6 = true;
        }
        System.out.println("passed: " + t1_6 + "\n");

        System.out.println("Expected: " + expected7);
        System.out.println("Actual: " + b1.getBookID());
        if(expected7 == b1.getBookID())
        {
            t1_7 = true;
        }
        System.out.println("passed: " + t1_7 + "\n");

        System.out.println("Expected: " + expected8);
        System.out.println("Actual: " + b1.getShortString());
        if(expected8.equals(b1.getShortString()))
        {
            t1_8 = true;
        }
        System.out.println("passed: " + t1_8 + "\n");

        if(t1_1 && t1_2 && t1_3 && t1_4 && t1_5 && t1_6 && t1_7 && t1_8)
        {
            t1 = true;
            System.out.println("testConstructorAndGetters Passed!\n");
        }
        else
        {
            System.out.println("testConstructorAndGetters Failed!\n");
        }

    }

    /**
     * <p>Tests the set methods to see if they provide valid output</p>
     *
     * <p>Tests whether these Book class methods work as intended:</p>
     * <ol>
     *     <li>setAuthorFamilyNames</li>
     *     <li>setAuthorRestOfName</li>
     *     <li>setGenre</li>
     * </ol>
     * <p>and if they do t2 is set to true</p>
     */
    public static void testSetters()
    {
        boolean t2_1 = false;
        boolean t2_2 = false;
        String expected2_1 = "123";
        Genre expected2_2 = Genre.FANTASY;
        Book b2 = new Book();

        System.out.println("--- Testing The Setter Methods ---");

        System.out.println("Expected: " + expected2_1);
        b2.setAuthorFamilyNames("1");
        b2.setAuthorRestOfName("2");
        b2.setTitle("3");
        System.out.println("Actual: " + b2.getShortString());
        if(expected2_1.equals(b2.getShortString()))
        {
            t2_1 = true;
        }
        System.out.println("passed: " + t2_1 + "\n");

        System.out.println("Expected: " + expected2_2);
        b2.setGenre(expected2_2);
        System.out.println("Actual: " + b2.getGenre());
        if(expected2_2 == b2.getGenre())
        {
            t2_2 = true;
        }
        System.out.println("passed: " + t2_2 + "\n");

        if(t2_1 && t2_2)
        {
            t2 = true;
            System.out.println("testSetters Passed!\n");
        }
        else
        {
            System.out.println("testSetters Failed!\n");
        }
    }

    /**
     * <p>Tests whether Book class IDs are automatically set appropriately</p>
     * <p>and if they do t3 is set to true</p>
     */
    public static void testBookID()
    {
        boolean t3_1 = false;
        boolean t3_2 = false;
        int expected3_1 = 1003828;
        int expected3_2 = 1003829;
        Book b3 = new Book("1","2","3",Genre.FANTASY);

        System.out.println("--- Testing The BookID Method ---");

        System.out.println("Expected: " + expected3_1);
        System.out.println("Actual: " + b3.getBookID());
        if(expected3_1 == b3.getBookID())
        {
            t3_1 = true;
        }
        System.out.println("passed: " + t3_1 + "\n");


        Book b4 = new Book("1","2","3",Genre.FANTASY);
        System.out.println("Expected: " + expected3_2);
        System.out.println("Actual: " + b4.getBookID());
        if(expected3_2 == b4.getBookID())
        {
            t3_2 = true;
        }
        System.out.println("passed: " + t3_2 + "\n");

        if(t3_1 && t3_2)
        {
            t3 = true;
            System.out.println("testBookID Passed!\n");
        }
        else
        {
            System.out.println("testBookID Failed!\n");
        }
    }

    /**
     * <p>Tests the Book class toString methods work as intended</p>
     * <p>and if it does t4 is set to true</p>
     */
    public static void testToString()
    {
        boolean t4_1 = false;
        String expected4_1 = "1231003830FANTASY";
        Book b5 = new Book("1","2","3",Genre.FANTASY);

        System.out.println("--- Testing The ToString Method ---");

        System.out.println("Expected: " + expected4_1);
        System.out.println("Actual: " + b5.toString());
        if(expected4_1.equals(b5.toString()))
        {
            t4_1 = true;
        }
        System.out.println("passed: " + t4_1 + "\n");

        if(t4_1)
        {
            t4 = true;
            System.out.println("testToString Passed!\n");
        }
        else
        {
            System.out.println("testToString Failed!\n");
        }
    }
}