/**
 * <p> HeapSort Algorithm to sort unsorted arrays,
 * does this by using MAX binary heaps then substituting the root node
 * with the last element/node, ignoring the biggest element and reheapifying the MAX heap,
 * doing then repeating recursively.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.1
 * Last Changed: 05/05/25
 */
public class HeapSort {
    /**
     * Main body of HeapSort, 
     * creates MAX heap, swaps 0 and n nodes, makes reduced heap a MAX heap.
     *
     * @param array - array to be sorted
     */
    static void heapSort(int[] array) {
        int n = array.length;

        for (int rootIndex = n / 2 - 1; rootIndex >= 0; rootIndex--) {
            heapify(array, n, rootIndex);
        }
        
        for (int i = n - 1; i > 0; i--) {
            swap(array, 0, i);
            heapify(array, i, 0);
        }
    }
    /**
     * Ensures that a binary heap holds the properties of one recursively
     *
     * @param array - array to be sorted
     * @param n - array length
     * @param rootIndex - root index of heap
     */
    static void heapify(int[] array, int n, int rootIndex) {
        int largest = rootIndex;
        int l = 2 * rootIndex + 1;
        int r = 2 * rootIndex + 2;

        if (l < n && array[l] > array[largest]) {
            largest = l;
        }
        if (r < n && array[r] > array[largest]) {
            largest = r;
        }

        if (largest != rootIndex) {
            swap(array, rootIndex, largest);
            heapify(array, n, largest);
        }
    }
    /**
     * swap function to swap data in an array
     *
     * @param array - array to be sorted
     * @param i - index of current element
     * @param newpos - correct position for element
     */
    static void swap(int[] array, int i, int newpos)
    {
        int temp = array[i];
        array[i] = array[newpos];
        array[newpos] = temp;
    }
}
