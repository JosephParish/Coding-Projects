/**
 * <p> BubbleSort Algorithm to sort unsorted graphs.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.1
 * Last Changed: 9/03/25
 */

public class BubbleSort {

    /**
     * BubbleSort main body - goes through array swapping all values
     * that aren't in order e.g. (1 4 2 5 3 becomes 1 2 4 3 5 becomes 1 2 3 4 5) recursively.
     *
     * @param array - array to be sorted
     */
    static void bubbleSort(int[] array) {
        boolean swapped;

        // Traverse through all array elements
        for (int i = 0; i < array.length - 1; i++) {
            swapped = false;

            // Last i elements are already sorted, no need to check them
            for (int j = 0; j < array.length - i - 1; j++) {
                // If the next element is lesser, swap
                if (array[j] > array[j + 1]) {
                    swap(array, j, j + 1);
                    swapped = true;
                }
            }

            // Check if array is sorted
            if (!swapped) {
                break;
            }
        }
    }

    /**
     * swap() - swaps elements in an array
     *
     * @param array - array to be sorted
     */
    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
