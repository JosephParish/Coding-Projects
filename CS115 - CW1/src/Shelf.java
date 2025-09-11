/**
 * <p>This Shelf class is intended to hold references to ShelfSpaces at left and right end of the Shelf
 * as well as methods to add a Book to the left (updating the references) and remove and return a Book from the
 * right end (updating the references). Also contains methods to print all books in the Shelf from right to left
 * removing them from the Shelf and print all books in the Shelf from left to right not removing them,
 * printing "empty" if empty.</p>
 *
 * @author Joseph Parish
 * @version 2.0.21
 */
public class Shelf
{
    private ShelfSpace leftEnd;
    private ShelfSpace rightEnd;

    /**
     * Constructs an instance of the Library class
     */
    public Shelf(){}


    /**
     * Adds a Book to the left end of the Shelf and updates the references.
     * @param newBook The Book to be added
     */
    public void addLeft(Book newBook)
    {
        ShelfSpace newShelfSpace = new ShelfSpace(newBook);
        if(leftEnd == null)
        {
            this.leftEnd = newShelfSpace;
            this.rightEnd = newShelfSpace;
        }
        else
        {
            newShelfSpace.setRightSpace(this.leftEnd);
            this.leftEnd.setLeftSpace(newShelfSpace);

            this.leftEnd = newShelfSpace;
        }
    }

    /**
     * Removes a Book from the right end of the Shelf, and returns it. Again, updates the
     * references appropriately and if there is now only one Book left it is both the left and right end.
     * @return returnBook - The Book from the right end of the Shelf
     */
    public Book takeRight()
    {
        if(rightEnd == null)
        {
            return null;
        }
        else
        {
            Book returnBook = rightEnd.getThisBook();

            if(rightEnd.getLeftSpace() == null)
            {
                rightEnd = null;
                leftEnd = null;
            }
            else
            {
                this.rightEnd = rightEnd.getLeftSpace();
                rightEnd.setRightSpace(null);
            }

            return returnBook;
        }
    }

    /**
     * Prints the contents of the Shelf to the command line in right to left order, removing each
     * Book as it goes. It prints using getShortString. If the Shelf is already empty, it prints nothing.
     */
    public void empty()
    {
        if(rightEnd != null)
        {
            Book currentBook = takeRight();

            do
            {
                String temp = currentBook.getShortString();
                System.out.println(temp);
                currentBook = takeRight();
            }
            while (currentBook != null);
        }
    }

    /**
     * Returns a String with the contents of the Shelf in left to right order, removing nothing from the Shelf
     * and updating the references. Each Book short String will be on a new line. If the Shelf is empty,
     * it will return "empty".
     * @return returnString - The String being returned
     */
    public String printAll()
    {
        boolean empty = true;
        ShelfSpace currentBook = leftEnd;
        String returnString = "";

        if(currentBook == null)
        {
            returnString = "empty";
        }
        else
        {
            returnString = formatter(returnString, currentBook);
        }

        return returnString;
    }

    /**
     * Returns a String with the contents of the Shelf in left to right order, removing nothing
     * from the Shelf and updating the references. Each Book short String will be on a new line.
     * @param returnString the String being returned
     * @param currentBook the left most Book to start with
     */
    private String formatter(String returnString, ShelfSpace currentBook)
    {
        if(currentBook == rightEnd)
        {
            returnString += currentBook.getThisBook().getShortString() + "\n";
            return returnString;
        }

        returnString += currentBook.getThisBook().getShortString() + "\n";
        currentBook = currentBook.getRightSpace();
        return formatter(returnString, currentBook);
    }
}










