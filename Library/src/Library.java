import java.util.ArrayList;

/**
 * <p>This Library class is intended to hold arraylists of all Genre of books in the Library,
 * be able to take in a list of books and store them in the correct Genre, output all books
 * in a Genre that posses a certain String in its title.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.13
 */
public class Library
{
    private ArrayList<Book> fantasy = new ArrayList<>();
    private ArrayList<Book> mystery = new ArrayList<>();
    private ArrayList<Book> nonFiction = new ArrayList<>();
    private ArrayList<Book> romance = new ArrayList<>();
    private ArrayList<Book> sciFi = new ArrayList<>();
    private Shelf incoming;

    /**
     * Constructs an instance of the Library class
     * @param newIncoming the arraylist containing Books to be put in the respective Genre arraylist
     */
    public Library(Shelf newIncoming)
    {
        this.incoming = newIncoming;
    }

    /**
     * @param incoming the arraylist containing Books to be put in the respective Genre arraylist
     */
    public void setIncoming(Shelf incoming)
    {
        this.incoming = incoming;
    }

    /**
     * @return incoming - the arraylist containing Books to be put in the respective Genre arraylist
     */
    public Shelf getIncoming()
    {
        return incoming;
    }

    /**
     * @param fantasy the arraylist containing Books of type FANTASY
     */
    public void setFantasy(ArrayList<Book> fantasy)
    {
        this.fantasy = fantasy;
    }

    /**
     * @return fantasy - the arraylist containing Books of type FANTASY
     */
    public ArrayList<Book> getFantasy()
    {
        return fantasy;
    }

    /**
     * @param mystery the arraylist containing Books of type MYSTERY
     */
    public void setMystery(ArrayList<Book> mystery)
    {
        this.mystery = mystery;
    }

    /**
     * @return mystery - the arraylist containing Books of type MYSTERY
     */
    public ArrayList<Book> getMystery()
    {
        return mystery;
    }

    /**
     * @param nonFiction the arraylist containing Books of type NON_FICTION
     */
    public void setNonFiction(ArrayList<Book> nonFiction)
    {
        this.nonFiction = nonFiction;
    }

    /**
     * @return nonFiction - the arraylist containing Books of type NON_FICTION
     */
    public ArrayList<Book> getNonFiction()
    {
        return nonFiction;
    }

    /**
     * @param romance the arraylist containing Books of type ROMANCE
     */
    public void setRomance(ArrayList<Book> romance)
    {
        this.romance = romance;
    }

    /**
     * @return romance - the arraylist containing Books of type ROMANCE
     */
    public ArrayList<Book> getRomance()
    {
        return romance;
    }

    /**
     * @param sciFi the arraylist containing Books of type SCIENCE_FICTION
     */
    public void setSciFi(ArrayList<Book> sciFi)
    {
        this.sciFi = sciFi;
    }

    /**
     * @return sciFi - the arraylist containing Books of type SCIENCE_FICTION
     */
    public ArrayList<Book> getSciFi()
    {
        return sciFi;
    }

    /**
     * puts all incoming books in arraylists and makes incoming empty as a result
     */
    public void process()
    {
        boolean isEmpty = false;
        while (!isEmpty)
        {
            Book currentBook = this.incoming.takeRight();
            if (currentBook == null)
            {
                isEmpty = true;
            }
            else
            {
                switch (currentBook.getGenre())
                {
                    case FANTASY:
                        fantasy.add(currentBook);
                        break;
                    case MYSTERY:
                        mystery.add(currentBook);
                        break;
                    case NON_FICTION:
                        nonFiction.add(currentBook);
                        break;
                    case ROMANCE:
                        romance.add(currentBook);
                        break;
                    case SCIENCE_FICTION:
                        sciFi.add(currentBook);
                        break;
                }
            }
        }
    }

    /**
     * Searches for all books in the genre of this library containing the titleFragment in its title
     * and returns a citation for all of them but if none are found "No results found." is printed
     * @param wantedArrayList The genre of books to search for
     * @param titleFragment The fragment of the title to search for
     */
    public void search(Genre wantedArrayList, String titleFragment)
    {
        String returnString = "";
        switch (wantedArrayList)
        {
            case FANTASY:
                for (int i = 0; i < fantasy.size(); i++)
                {
                    if ((fantasy.get(i).getTitle().toLowerCase()).contains((titleFragment.toLowerCase())))// sees if the title of the current book contains the titleFragment
                    {
                        returnString += fantasy.get(i).getCitation() + "\n";
                    }
                }
                break;
            case MYSTERY:
                for (int i = 0; i < mystery.size(); i++)
                {
                    if (mystery.get(i).getTitle().toLowerCase().contains(titleFragment.toLowerCase()))// sees if the title of the current book contains the titleFragment
                    {
                        returnString += mystery.get(i).getCitation() + "\n";
                    }
                }
                break;
            case NON_FICTION:
                for (int i = 0; i < nonFiction.size(); i++)
                {
                    if (nonFiction.get(i).getTitle().toLowerCase().contains(titleFragment.toLowerCase()))   // sees if the title of the current book contains the titleFragment
                    {
                        returnString += nonFiction.get(i).getCitation() + "\n";
                    }
                }
                break;
            case ROMANCE:
                for (int i = 0; i < romance.size(); i++)
                {
                    if (romance.get(i).getTitle().toLowerCase().contains(titleFragment.toLowerCase()))  // sees if the title of the current book contains the titleFragment
                    {
                        returnString += romance.get(i).getCitation() + "\n";
                    }
                }
                break;
            case SCIENCE_FICTION:
                for (int i = 0; i < sciFi.size(); i++)
                {
                    if (sciFi.get(i).getTitle().toLowerCase().contains(titleFragment.toLowerCase()))    // sees if the title of the current book contains the titleFragment
                    {
                        returnString += sciFi.get(i).getCitation() + "\n";
                    }
                }
                break;
            default:
                break;
        }
        if (returnString.isEmpty())
        {
            returnString = "No results found.";
        }
        System.out.println(returnString);
    }
}
