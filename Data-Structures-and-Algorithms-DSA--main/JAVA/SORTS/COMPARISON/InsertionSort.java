/**
 * <p> InsertionSort Algorithm to sort unsorted arrays,
 * does this by placing elements in the correct place one by one.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.3
 * Last Changed: 05/05/25
 */
public class InsertionSort {
    /**
     * Main body, loops though elements
     * placing current element behind all larger elements behind it
     *
     * @param array - array to be sorted
     */
    static void insertionSort(int array[])
    {
        for (int i = 1; i < array.length; ++i) {
            int j = i - 1;
            while (j >= 0 && array[j] > array[i]) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = array[i];
        }
    }
}
