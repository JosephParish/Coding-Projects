/**
 * <p> CombSort Algorithm to sort unsorted graphs,
 * uses combing factor to swap many elements at once.</p>
 *
 * @author Joseph Parish.
 * @version 1.0.7
 * Last Changed: 01/07/25
 */
public class CombSort {
  
    /**
     * CombSort main body - goes through array swapping all values
     * that aren't in order that have a gap of a certain shrinking factor recursively.
     *
     * @param array - array to be sorted
     */
    static void combSort(int[] array) {
      int gap = array.length;
      boolean swapped = true;

      while (gap != 1 || swapped == true) {
          gap = getNextGap(gap);
          swapped = false;

          for (int i = 0; i < n-gap; i++) {
              if (arr[i] > arr[i+gap]) {
                  swap(array, i, i+gap);
                  swapped = true;
                }
            }
        }
    }
  
    /**
     * swap() - swaps elements in an array
     *
     * @param array - array to be sorted
     * @param i - element to be swaped
     * @param j - location to swap with
     */
    static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
    
    /**
     * getNextGap() - calculates next gap
     *
     * @param gap - current gap factor
     */
    static int getNextGap(int gap) {
        gap = (gap * 10)/13;
        if (gap < 1) {
            return 1;
        }
        return gap;
    }
}
