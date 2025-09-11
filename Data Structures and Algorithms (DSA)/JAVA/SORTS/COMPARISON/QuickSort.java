import java.util.Random;

/**
 * <p> QuickSort Algorithm to sort unsorted arrays,
 * using different pivot selection methods and partitioning.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.2
 * Last Changed: 9/03/25
 */

public class QuickSort {

    /**
     * QuickSort main body - picks a pivot, puts all smaller numbers on the left 
     * and bigger on the right then solves recursively
     * 
     * @param array - array to be sorted
     * @param low - lowest position in array
     * @param high - highest position in array
     * @param pivotSelectionMethod - type of pivot selection method used
     */
    static void quickSort(int[] array, int low, int high, int pivotSelectionMethod) {
        if (low < high) {

            // partition index
            int pi = partition(array, low, high, pivotSelectionMethod);

            // Recursion
            quickSort(array, low, pi - 1, pivotSelectionMethod);
            quickSort(array, pi + 1, high, pivotSelectionMethod);
        }
    }

    /**
     * pivot() - selects the pivot in an array based on the type of method chosen
     *
     * @param array - array to be sorted
     * @param pivotSelectionMethod - type of pivot selection method used
     */
    static int pivot(int[] array, int pivotSelectionMethod) {

        if (pivotSelectionMethod == 0) {
            // using first element
            return 0;
        }
        else if (pivotSelectionMethod == 1) {
            // using random element
            return new Random().nextInt(array.length - 1);
        }
        else {
            // using median
            return (array.length - 1)/2;
        }
    }

    /**
     * partition() - puts all smaller numbers on the left 
     * and bigger on the right of a pivot using swap()
     *
     * @param array - array to be sorted
     * @param low - lowest position in array
     * @param high - highest position in array
     * @param pivotSelectionMethod - type of pivot selection method used
     */
    static int partition(int[] array, int low, int high, int pivotSelectionMethod) {

        int pivot = pivot(array, pivotSelectionMethod);

        // Index of smaller element
        int i = low - 1;

        // Traverse array and move elements to the left side if smaller
        for (int j = low; j <= high - 1; j++) {
            if (array[j] < pivot) {
                i++;
                swap(array, i, j);
            }
        }

        // Move pivot after smaller elements and return its position
        swap(array, i + 1, high);
        return i + 1;
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
