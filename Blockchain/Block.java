import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Block
{
    private int previoushash;
    private String[] transactions;
    private int[] signatures;
    private int myhash;
    public Block(int previoushash,String[] transactions, int[] signatures)
    {
        this.previoushash = previoushash;
        this.transactions = transactions;
        this.signatures = signatures;

        Object[] contents = {Arrays.hashCode(transactions), previoushash};
        this.myhash = Arrays.hashCode(contents);
    }
    public int getPrevioushash()
    {
        return previoushash;
    }
    public String[] gettransactions()
    {
        return transactions;
    }

    public static boolean checksignatures(int[] expectedsignatures, int[] signatures)
    {
        if(expectedsignatures == signatures)
        {
            return true;
        }else
        {
            return false;
        }
    }
}
