import java.io.*;
import java.util.*;

/**
 * <p> RadixSort Algorithm to sort unsorted arrays,
 * does this by sorting by digit.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.7
 * Last Changed: 01/07/25
 */

class RadixSort {

    /**
     * RadixSort main body - goes through array and for every digit swaps them to be in order.
     *
     * @param array - array to be sorted
     */
    static void radixSort(int[] array)
    {
        int max = getMax(array);

        for (int exp = 1; max / exp > 0; exp *= 10) {
            countSort(array, exp);
        }
    }

    /**
     * CountSort main body,
     * uses a counting array to count the value of every number in the array,
     * then adds the count of all smaller numbers to each value,
     * then goes and puts every value from back to front in order by putting it in the reduced countArray value.
     *
     * @param array - array to be sorted
     * @param exp - exp is the 10^i factor of what digit is to be sorted
     */
    static void countSort(int[] array, int exp)
    {
        int[] output = new int[array.length];
        int i;
        int[] count = new int[10];
        Arrays.fill(count, 0);

        for (i = 0; i < array.length; i++) {
            count[(array[i] / exp) % 10]++;
        }

        for (i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }

        for (i = array.length - 1; i >= 0; i--) {
            output[count[(array[i] / exp) % 10] - 1] = array[i];
            count[(array[i] / exp) % 10]--;
        }

        for (i = 0; i < array.length; i++) {
            array[i] = output[i];
        }
    }

    // A utility function to get maximum value in array
    /**
     * getMax() - gets maximum value in a array
     *
     * @param array - array to look in
     */
    static int getMax(int[] array)
    {
        int max = array[0];
        for (int i = 1; i < array.length; i++)
            if (array[i] > max)
                max = array[i];
        return max;
    }
}
