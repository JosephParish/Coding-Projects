/**
 * <p> CycleSort Algorithm to sort unsorted arrays,
 * does this by dividing the input array into cycles, 
 * where each cycle consists of elements that belong to the same position in the sorted output array
 * and swapping them until they are in the correct position in the cycle.</p>
 *
 * @author Joseph Parish.
 * @version 1.1.0
 * Last Changed: 07/05/25
 */
public class CycleSort {
    
    /**
     * Main Body of CycleSort,
     * swaps values with how many elements should be behind it (smaller),
     * this forms cycles.
     *
     * @param array - array to be sorted
     * @param n - highest number
     */
    public static void cycleSort(int[] array, int n)
    {
        int writes = 0;

        for (int cycle_start = 0; cycle_start <= n - 2; cycle_start++) {
            int item = array[cycle_start];
            
            int pos = cycle_start;
            for (int i = cycle_start + 1; i < n; i++){
                if (array[i] < item){
                    pos++;
                }
            }
            
            if (pos == cycle_start){
                continue;
            }
            
            while (item == array[pos]){
                pos += 1;
            }
            
            if (pos != cycle_start) {
                swap(array, cycle_start, pos);
                writes++;
            }
            
            while (pos != cycle_start) {
                pos = cycle_start;
                
                for (int i = cycle_start + 1; i < n; i++){
                    if (array[i] < item){
                        pos++;
                    }
                }
                
                while (item == array[pos]){
                    pos++;
                }
                
                if (item != array[pos]) {
                    swap(array, cycle_start, pos);
                    writes++;
                }
            }
        }
    }
    
    /**
     * simpler version of CycleSort,
     * puts values in the correct place as they are from 1 to n so 1 is in position 0 etc.
     *
     * @param array - array to be sorted
     * @param n - highest number
     */
    static void simpleCycleSort(int[] array, int n)
    {
        int i = 0;
        while (i < n) {
            int correctpos = array[i] - 1;
            if (array[i] < n && array[i] != array[correctpos]) {
                swap(array, i, correctpos);
            }
            else {
                i++;
            }
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
