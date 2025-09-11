/**
 * <p> MergeSort Algorithm to sort unsorted arrays,
 * using a Divide and Conquer approach.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.0
 * Last Changed: 30/04/25
 */
public class MergeSort {

    /**
     * Main body, divides array into sub-arrays of its elements
     * then calls an auxiliary function for recombination
     *
     * @param array - array to be sorted
     * @param low   - lowest position of array
     * @param high  - highest position of array
     */
    static void mergeSort(int[] array, int low, int high) {
        if (low < high) {

            int middle = low + (high - low) / 2;

            mergeSort(array, low, middle);
            mergeSort(array, middle + 1, high);

            merge(array, low, middle, high);
        }
    }

    /**
     * Merges sub-arrays of elements back together by creating temporary copies,
     * then loops and compares the two adding the lowest,
     * finally once one has run out of elements it adds the rest of the other sub-array.
     *
     * @param array - array to be sorted
     * @param low   - lowest position of array
     * @param middle - middle position of array
     * @param high  - highest position of array
     */
    static void merge(int[] array, int low, int middle, int high) {
        // First Step Create temporary sub-arrays

        int n1 = middle - low + 1;
        int n2 = high - middle;
        int[] left = new int[n1];
        int[] right = new int[n2];

        for (int i = 0; i < n1; ++i) {
            left[i] = array[low + i];
        }
        for (int i = 0; i < n2; ++i) {
            right[i] = array[middle + 1 + i];
        }

        // Second Step Merge

        int i = 0, j = 0;
        int k = low;

        // Compare while they both have elements left
        while (i < n1 && j < n2) {
            array[k++] = (left[i] <= right[j]) ? left[i++] : right[j++];
        }

        // Add leftover elements
        while (i < n1) {
            array[k++] = left[i++];
        }
        while (j < n2) {
            array[k++] = right[j++];
        }
    }
}
