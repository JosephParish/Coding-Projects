import java.util.ArrayList;
import java.util.Scanner;
public class Blockchain
{

    ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args)
    {
        String[] transactions = {"1", "2"};
        int[] signatures = {1, 2};
        Block genisis = new Block(0, transactions, signatures);
    }
    public boolean isvalid(String[] transactions, int[] signatures, Block Blockinquestion)
    {

        int[] expectedsignatures = new int[0];
        Block.checksignatures(expectedsignatures, signatures);
    }
}
