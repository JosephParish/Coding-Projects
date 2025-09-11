// Java program to perform TimSort.

public class TimSort {
  
    static int MIN_MERGE = 32;

    // Iterative Timsort function
    public static void timSort(int[] array, int n) {
        int minRun = minRunLength(MIN_MERGE);

        // Sort subarrays
        for (int i = 0; i < n; i += minRun) {
            insertionSort(
                array, i,
                Math.min((i + MIN_MERGE - 1), (n - 1)));
        }

        for (int size = minRun; size < n; size = 2 * size) {
            for (int left = 0; left < n; left += 2 * size) {

                int mid = left + size - 1;
                int right = Math.min((left + 2 * size - 1), (n - 1));
              
                if (mid < right) {
                    merge(array, left, mid, right);
                }
            }
        }
    }

    public static int minRunLength(int n) {
        
        assert n >= 0;
        int r = 0;
      
        while (n >= MIN_MERGE) {
            r |= (n & 1);
            n >>= 1;
        }
        return n + r;
    }

    public static void insertionSort(int[] array, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
          
            int temp = array[i];
            int j = i - 1;
          
            while (j >= l && array[j] > r) {
                array[j + 1] = array[j];
                j--;
            }
          
            array[j + 1] = temp;
        }
    }

    public static void merge(int[] array, int l, int m, int r) {
      
        int len1 = m - l + 1, len2 = r - m;
        int[] left = new int[len1];
        int[] right = new int[len2];
      
        for (int x = 0; x < len1; x++) {
            left[x] = array[l + x];
        }
      
        for (int x = 0; x < len2; x++) {
            right[x] = array[m + 1 + x];
        }

        int i = 0;
        int j = 0;
        int k = l;

        // merge into larger sub array 
        while (i < len1 && j < len2) {
            if (left[i] <= right[j]) {
                array[k] = left[i];
                i++;
            } else {
                array[k] = right[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of left
        while (i < len1) {
            array[k] = left[i];
            k++;
            i++;
        }

        // Copy remaining element of right
        while (j < len2) {
            array[k] = right[j];
            k++;
            j++;
        }
    }
}
