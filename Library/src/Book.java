/**
 * <p>This Book class is intended to hold information related to the authors full name,
 * the title of the book, its Genre, its unique ID as well as the number of pages,
 * the publisher and the year. The Book class should also be able to return the information individually, as a short string,
 * or as a citation. Also has capability to hold multiple authors and output suitable outputs when this is
 * the case.</p>
 *
 * @author Joseph Parish
 * @version 2.0.15
 */
public class Book
{
    private String authorFamilyName;
    private String authorRestOfName;
    private String[] names;
    private String title;
    private static int books = 1003824;
    private final int bookID;
    private Genre genre;
    private int numPages;
    private String publisher;
    private int year;

    /**
     * Constructs an instance of the Book class for multiple authors with extra information
     * @param names An array of all the authors family and first names
     * @param title The title of the Book
     * @param genre The genre of the Book
     * @param year The year the Book was published
     * @param publisher The publisher of the Book
     * @param numPages The number of pages of the Book
     */
    public Book(String[] names, String title, Genre genre, int year, String publisher, int numPages)
    {
        books++;
        this.bookID = books;
        this.names = names;
        this.title = title;
        this.genre = genre;

        this.year = year;
        this.publisher = publisher;
        this.numPages = numPages;
    }

    /**
     * Constructs an instance of the Book class for a single author with extra information
     * @param authorFamilyName The family name of the only author
     * @param authorRestOfName The first name of the only author
     * @param title The title of the Book
     * @param genre The genre of the Book
     * @param year The year the Book was published
     * @param publisher The publisher of the Book
     * @param numPages The number of pages of the Book
     */
    public Book(String authorFamilyName, String authorRestOfName, String title, Genre genre,
                int year, String publisher, int numPages)
    {
        books++;
        this.bookID = books;
        this.authorFamilyName = authorFamilyName;
        this.authorRestOfName = authorRestOfName;
        this.title = title;
        this.genre = genre;

        this.year = year;
        this.publisher = publisher;
        this.numPages = numPages;
    }

    /**
     * Constructs an instance of the Book class for a single author with no extra information
     * @param authorFamilyName The family name of the only author
     * @param authorRestOfName The first name of the only author
     * @param title The title of the Book
     * @param genre The genre of the Book
     */
    public Book(String authorFamilyName, String authorRestOfName, String title, Genre genre)
    {
        books++;
        this.bookID = books;
        this.authorFamilyName = authorFamilyName;
        this.authorRestOfName = authorRestOfName;
        this.title = title;
        this.genre = genre;
    }

    /**
     * Constructs an instance of the Book class with only an ID
     */
    public Book()
    {
        books++;
        this.bookID = books;
    }

    /**
     * @return <ul>
     *     <li>names[0] - The first author's family name</li>
     *     <li>authorFamilyName - The sole author's family name</li>
     * </ul>
     */
    public String getAuthorFamilyNames()
    {
        if (names != null)
        {
            return names[0];
        }
        return authorFamilyName;
    }

    /**
     * @param newAuthorFamilyName The new family name of the first or only author
     */
    public void setAuthorFamilyNames(String newAuthorFamilyName)
    {
        if (names != null)
        {
            this.names[0] = newAuthorFamilyName;
        }
        else
        {
            this.authorFamilyName = newAuthorFamilyName;
        }
    }

    /**
     * @return <ul>
     *     <li>names[1] - The first author's first name</li>
     *     <li>authorRestOfName - The sole author's first name</li>
     * </ul>
     */
    public String getAuthorRestOfName()
    {
        if (names != null)
        {
            return names[1];
        }
        return authorRestOfName;
    }

    /**
     * @param newAuthorRestOfName The new first name of the first or only author
     */
    public void setAuthorRestOfName(String newAuthorRestOfName)
    {
        if (names != null)
        {
            this.names[1] = newAuthorRestOfName;
        }
        else
        {
            this.authorRestOfName = newAuthorRestOfName;
        }
    }

    /**
     * @return title - The title of this Book
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param newTitle The new title of the Book
     */
    public void setTitle(String newTitle)
    {
        this.title = newTitle;
    }

    /**
     * @return bookID - The bookID of this Book
     */
    public int getBookID()
    {
        return bookID;
    }

    /**
     * @return genre - The genre of this Book
     */
    public Genre getGenre()
    {
        return genre;
    }

    /**
     * @param newGenre The new Genre of the Book
     */
    public void setGenre(Genre newGenre)
    {
        this.genre = newGenre;
    }

    /**
     * @return numPages - The number of pages this Book has
     */
    public int getNumPages()
    {
        return  this.numPages;
    }

    /**
     * @param numPages The new number of pages of the Book
     */
    public void setNumPages(int numPages)
    {
        this.numPages = numPages;
    }

    /**
     * @return publisher - The publisher that published this Book
     */
    public String getPublisher()
    {
        return this.publisher;
    }

    /**
     * @param publisher The new publisher who published the Book
     */
    public void setPublisher(String publisher)
    {
        this.publisher = publisher;
    }

    /**
     * @return year - The year this Book was published
     */
    public int getYear()
    {
        return this.year;
    }

    /**
     * @param year The new year the Book was published
     */
    public void setYear(int year)
    {
        this.year = year;
    }

    /**
     * @return returnString - The family and first name of the first author + the title of the book
     */
    public String getShortString()
    {
        String returnString = authorFamilyName + authorRestOfName + title;
        if (names != null)
        {
            returnString = this.names[0] + this.names[1] + "et al." + title;
        }
        return (returnString);
    }

    /**
     * @return A citation of this Book - Containing: names, title, publisher, year and number of pages
     */
    public String getCitation()
    {
        if(getGenre() == Genre.NON_FICTION)
        {
            return (firstPartOfCitation() + " " + this.publisher + " (" + this.year + ") " + this.numPages +"pp.");
        }
        else
        {
            return (firstPartOfCitation());
        }
    }

    /**
     * <p>Returns the names formatted for citation in case of many names or just one.</p>
     * <ul>
     *     <li>In the case of many names for 2 people: "1, 2 and 3, 4. this.title." where 1,2,3,4 are family and first names</li>
     *     <li>In the case of one name: "1, 2. this.title" where 1,2 are the family and first name</li>
     * </ul>
     * @return The family and first name of the author/s and the title
     */
    private String firstPartOfCitation()
    {
        if (names != null)
        {
            StringBuilder returnString = new StringBuilder();
            for (int i = 0; i < names.length; i++)
            {
                if (i == names.length-1)
                {
                    returnString.append(this.names[i]);
                }
                else if (i % 2 == 0)
                {
                    returnString.append(this.names[i]).append(", ");
                }
                else
                {
                    returnString.append(this.names[i]).append(" and ");
                }
            }
            return (returnString + ". " + this.title + ".");
        }
        return (this.authorFamilyName + ", " + this.authorRestOfName + ". " + this.title + ".");
    }

    /**
     * @return The Book in String format without extra information (e.g. numPages, year, publisher)
     */
    @Override
    public String toString()
    {
        return (this.getShortString() + bookID + genre);
    }
}
