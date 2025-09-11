/**
 * <p> ThreeWayMergeSort Algorithm to sort unsorted arrays,
 * using a Divide and Conquer approach. </p>
 *
 * @author Joseph Parish.
 * @version 1.0.0
 * Last Changed: 08/05/25
 */
public class ThreeWayMergeSort {
    /**
     * Main Body of ThreeWayMergeSort,
     * divides array into sub-arrays of its elements
     * then calls an auxiliary function for recombination
     *
     * @param array - array to be sorted
     * @param left - lowest index
     * @param right - highest index
     */
    static void threeWayMergeSort(int[] array, int left, int right) {
        if (left >= right) {
            return;
        }

        int mid1 = left + (right - left) / 3;
        int mid2 = left + 2 * (right - left) / 3;

        threeWayMergeSort(array, left, mid1);
        threeWayMergeSort(array, mid1 + 1, mid2);
        threeWayMergeSort(array, mid2 + 1, right);
        merge(array, left, mid1, mid2, right);
    }

    /**
     * Merges sub-arrays of elements back together by creating temporary copies,
     * then loops and compares them adding the lowest,
     * finally once one has run out of elements it adds the rest of the last sub-array.
     *
     * @param array - array to be sorted
     * @param left - lowest index (left side)
     * @param mid1 - end of first third
     * @param mid2 - end of second third
     * @param right - highest index (right index)
     */
    static void merge(int[] array, int left, int mid1, int mid2, int right) {
        int size1 = mid1 - left + 1;
        int size2 = mid2 - mid1;
        int size3 = right - mid2;
        int[] leftArr = new int[size1];
        int[] midArr = new int[size2];
        int[] rightArr = new int[size3];

        for (int i = 0; i < size1; i++) {
            leftArr[i] = array[left + i];
        }
        for (int i = 0; i < size2; i++) {
            midArr[i] = array[mid1 + 1 + i];
        }
        for (int i = 0; i < size3; i++) {
            rightArr[i] = array[mid2 + 1 + i];
        }

        int i = 0, j = 0, k = 0, index = left;
        while (i < size1 || j < size2 || k < size3) {
            int minValue = Integer.MAX_VALUE, minIdx = -1;

            if (i < size1 && leftArr[i] < minValue) {
                minValue = leftArr[i];
                minIdx = 0;
            }
            if (j < size2 && midArr[j] < minValue) {
                minValue = midArr[j];
                minIdx = 1;
            }
            if (k < size3 && rightArr[k] < minValue) {
                minValue = rightArr[k];
                minIdx = 2;
            }

            if (minIdx == 0) {
                array[index++] = leftArr[i++];
            } else if (minIdx == 1) {
                array[index++] = midArr[j++];
            } else {
                array[index++] = rightArr[k++];
            }
        }
    }
}
