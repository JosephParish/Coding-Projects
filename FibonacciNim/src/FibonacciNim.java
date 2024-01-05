import java.util.Scanner;
public class FibonacciNim
{
    /*
        Hi, this code is for a game called "Fibonacci Nim" here are the rules:
            The game is for two players.
            Players take it in turns to play.
            The players have 3 heaps of coins.
            Each heap will start with 13 coins.
            They take it in turns to take some number of coins from ONE heap.
            On the first turn they cannot take all the coins from a heap (at most 12).
            On all later turns they CAN take AT MOST X2 as many coins as were taken from that heap on the turn before.
            They must take at least 1 coin - they cant skip a turn.
            They cant take more coins than are present in a heap.
            The player who takes the last coin wins.

        The code is split up into:
            - a main function where the code is run
                - a turn function to play out every turn
                    - a refill function to handle resetting the heaps
                    - a take function to handle removal of coins from the heaps
            - a Player class to hold Player information
     */
    public static final int MAX_HEAP = 13;
    public static final int MAX_RETRIEVAL = 12;
    public static final int NUM_OF_HEAPS = 3;
    public static final int OUT_OF_RANGE_INITIALIZER = 9999;

    /*
     * This is the main function where the game loop occurs until someone wins and the common variable are declared
     */

    public static void main(String[] args)
    {
        /*
         * - loops until someone takes the last coin
         *      - switches player
         *      - prints the heaps and calls turn function
         *      - if all heaps are empty then the game ends
         * - the winner is declared
         */

        Scanner input = new Scanner(System.in);
        int[] heaps = {0, MAX_HEAP, MAX_HEAP, MAX_HEAP};
        int[] retrieval = {0, MAX_RETRIEVAL, MAX_RETRIEVAL, MAX_RETRIEVAL}; // stores maximum take-able coins
        String player = "Player 2"; // is switched at start of game
        boolean game = true;

        while (game)
        {
            if (player.equals("Player 2"))
            {
                player = "Player 1";
            }
            else
            {
                player = "Player 2";
            }
            System.out.println("Remaining coins: " + heaps[1] +", "+ heaps[2] +", "+ heaps[NUM_OF_HEAPS]);
            turn(player, heaps, retrieval, input);
            if (heaps[1] == 0 && heaps[2] == 0 && heaps[NUM_OF_HEAPS] == 0)
            {
                game = false;
            }
        }
        System.out.println(player + " wins!");
    }

    /*
     * The turn function takes user input, checks it and calls functions and returns error messages whe appropriate
     */

    private static void turn(String player, int[] heaps, int[] retrieval, Scanner input)
    {
        /*
         * - loops until it is the end of the turn
         *      - takes and tests user input and produces error message if not 1,2,3 or -1,-2,-3
         *          - if negative it will call the refill function
         *          - if trying to take coins from an empty heap produce error message
         *          - if 1,2,3 and the heaps not empty
         */

        int selectedHeap = OUT_OF_RANGE_INITIALIZER;
        boolean turn = true;
        while (turn)
        {
            boolean negative = false;
            System.out.print(player + ": " + "choose a heap: ");

            boolean incorrectInput1 = true;
            while (incorrectInput1)
            {
                if (input.hasNextInt())
                {
                    int input1 = input.nextInt();
                    if ((input1 < -NUM_OF_HEAPS) || (input1 > NUM_OF_HEAPS) || (input1 == 0))
                    {
                        System.out.println("Sorry that's not a legal heap choice.");
                        System.out.print(player + ": " + "choose a heap: ");
                    }
                    else
                    {
                        incorrectInput1 = false;
                        if (input1 < 0)
                        {
                            input1 = input1 - input1 * 2; // make negative input positive i.e. -3 -> 3
                            negative = true;
                        }
                        selectedHeap = input1;
                    }
                }
                else
                {
                    System.out.println("Sorry you must enter an integer in the range -3 to 3, excluding zero.");
                    System.out.print(player + ": " + "choose a heap: ");
                }
                input.nextLine();
            }
            if (negative)
            {
                turn = refill(player, heaps, retrieval, selectedHeap);
            }
            else if (heaps[selectedHeap] == 0)
            {
                System.out.println("Sorry that's not a legal heap choice.");
            }
            else
            {
                turn = take(heaps, retrieval, selectedHeap, input);
            }
        }
    }

    /*
     * The refill function checks if this player has used their refill yet
     * and if they have not then it resets the heaps, sets their refill to the used state/true
     * and ends their turn.
     */

    private static boolean refill(String player, int[] heaps, int[] retrieval, int selectedHeap)
    {
        /*
        * - If the Player this turn has not used their refill
        *        - refill the heap and reset retrieval value
        *        - set their refill to the used state/true
        *        - set the endOfTurn variable to true to end the turn
        * - else say they have used their reset
        */

        if (player.equals("Player 1") && !Players.getplayer1Refill())
        {
            heaps[selectedHeap] = MAX_HEAP;
            retrieval[selectedHeap] = MAX_RETRIEVAL;
            System.out.println("Heap " + selectedHeap + " has been reset");
            Players.setplayer1Refill(true);
            return false;
        }
        else if (player.equals("Player 2") && !Players.getplayer2Refill())
        {
            heaps[selectedHeap] = MAX_HEAP;
            retrieval[selectedHeap] = MAX_RETRIEVAL;
            System.out.println("Heap " + selectedHeap + " has been reset");
            Players.setplayer2Refill(true);
            return false;
        }
        else
        {
            System.out.println("Sorry you have used your reset.");
            System.out.println("Remaining coins: " + heaps[1] +", "+ heaps[2] +", "+ heaps[NUM_OF_HEAPS]);
        }
        return true;
    }

    /*
    * This function takes the players input until its valid then takes that valid amount of coins from the heap selected
    */

    private static boolean take(int[] heaps, int[] retrieval, int selectedHeap, Scanner input)
    {
         /*
         * - takes an input if it is an integer
         *      - checks if coinsTake is in range of 1 and the retrieval
         *             - if so it checks whether to set the retrieval value to the heap value or double the coins
         *      - otherwise the appropriate error message is shown
         * - otherwise the appropriate error message is shown
         */

        int coinsTake = OUT_OF_RANGE_INITIALIZER;
        System.out.print("Now choose a number of coins between 1 and " + retrieval[selectedHeap] + ": ");
        boolean incorrectInput2 = true;

        while (incorrectInput2)
        {
            if (input.hasNextInt())
            {
                coinsTake = input.nextInt();

                if (coinsTake >= 1 && coinsTake <= retrieval[selectedHeap])
                {
                    if (coinsTake * 2 > (heaps[selectedHeap] - coinsTake))
                    {
                        heaps[selectedHeap] -= coinsTake;
                        retrieval[selectedHeap] = heaps[selectedHeap];
                    }
                    else
                    {
                        heaps[selectedHeap] -= coinsTake;
                        retrieval[selectedHeap] = coinsTake * 2;
                    }
                    incorrectInput2 = false;
                }
                else
                {
                    System.out.println("Sorry that's not a legal number of coins for that heap.");
                    System.out.print("Now choose a number of coins between 1 and " + retrieval[selectedHeap] + ": ");
                }
            }
            else
            {
                System.out.println("Sorry you must enter an integer.");
                System.out.print("Now choose between 1 and " + retrieval[selectedHeap] + ": ");
            }
            input.nextLine();
        }
        return false;
    }

   /*
    * The Player class is used to refer the Players variables by reference in the functions and
    * store Player info and values.
    * This class could be used in future to store other abilities, how many coins are taken, player names etc.
    */

    private static final class Players
    {
        private static boolean player1Refill = false; // whether Player 1 has used their refill
        private static boolean player2Refill = false; // whether Player 2 has used their refill

        // get and set functions for all the Players variables
        public static void setplayer1Refill(boolean player1Refill)
        {
            Players.player1Refill = player1Refill;
        }
        public static boolean getplayer1Refill()
        {
            return player1Refill;
        }
        public static void setplayer2Refill(boolean player2Refill)
        {
            Players.player2Refill = player2Refill;
        }
        public static boolean getplayer2Refill()
        {
            return player2Refill;
        }
    }
}

