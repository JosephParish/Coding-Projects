/**
 * <p> SelectionSort Algorithm to sort unsorted arrays,
 * does this by puts smallest element at the front of the array
 * then (considering only the leftover elements) does the same.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.0
 * Last Changed: 06/05/25
 */
public class SelectionSort {
    /**
     * Main body of SelectionSort,
     * puts smallest element at the front of the array then (considering only the leftover elements) does the same.
     *
     * @param array - array to be sorted
     */
    static void selectionSort(int[] array){
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int min_idx = i;

            for (int j = i + 1; j < n; j++) {
                if (array[j] < array[min_idx]) {
                    min_idx = j;
                }
            }

            int temp = array[i];
            array[i] = array[min_idx];
            array[min_idx] = temp;
        }
    }
}
