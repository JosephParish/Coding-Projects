/**
 * <p>This ShelfSpace class is intended to hold a book and a reference to a ShelfSpace either side</p>
 *
 * @author Joseph Parish
 * @version 1.0.5
 */
public class ShelfSpace
{
    private Book book;
    private ShelfSpace leftSpace;
    private ShelfSpace rightSpace;

    /**
     * Constructs an instance of the ShelfSpace class
     * @param bookToPlace The book in the ShelfSpace
     */
    public ShelfSpace(Book bookToPlace)
    {
        this.book = bookToPlace;
    }

    /**
     * @return book - The book in the ShelfSpace
     */
    public Book getThisBook()
    {
        return book;
    }

    /**
     * @return leftSpace - The next ShelfSpace on the left
     */
    public ShelfSpace getLeftSpace()
    {
        return leftSpace;
    }

    /**
     * @return rightSpace - The next ShelfSpace on the right
     */
    public ShelfSpace getRightSpace()
    {
        return rightSpace;
    }

    /**
     * @param leftSpace The next ShelfSpace on the left
     */
    public void setLeftSpace(ShelfSpace leftSpace)
    {
        this.leftSpace = leftSpace;
    }

    /**
     * @param rightSpace The next ShelfSpace on the right
     */
    public void setRightSpace(ShelfSpace rightSpace)
    {
        this.rightSpace = rightSpace;
    }

    /**
     * @return Book.toString() - The Book in this ShelfSpace in String format
     */
    @Override
    public String toString()
    {
        return book.toString();
    }
}
