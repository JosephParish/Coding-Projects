/**
 * <p> CountSort Algorithm to sort unsorted arrays,
 * using an extra array to count number of each int
 * works well on smaller data sets with less variance in the data i.e. 1 1 2 3 4 4 5. </p>
 *
 * @author Joseph Parish.
 * @version 1.0.1
 * Last Changed: 12/05/25
 */
public class CountSort {
    /**
     * CountSort main body,
     * uses a counting array to count the value of every number in the array,
     * then adds the count of all smaller numbers to each value,
     * then goes and puts every value from back to front in order by putting it in the reduced countArray value.
     *
     * @param array - array to be sorted
     */
    public static int[] countSort(int[] array) {
        int n = array.length;
        int m = 0;

        for(int i = 0; i < n; i++) {
            m = Math.max(m, array[i]);
        }

        int[] countArray = new int[m + 1];

        for(int i = 0; i < n; i++) {
            countArray[array[i]]++;
        }

        for(int i = 1; i <= m; i++) {
            countArray[i] += countArray[i - 1];
        }

        int[] sortedArray = new int[n];

        for(int i = n - 1; i >= 0; i--) {
            sortedArray[countArray[array[i]] - 1] = array[i];
            countArray[array[i]]--;
        }

        return sortedArray;
    }
}
