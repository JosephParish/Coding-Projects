import java.io.IOException;

public class Introsort {

    private int[] array;
    private int n;
    
    // Initialises and begins Introsort
    private void introsort(int[] array) {
      
      this.array = array;
      this.n = 0;
      int depthLimit = (int)(2 * Math.floor(Math.log(n)/Math.log(2))); //2*log(length(data))
      this.sortData(0, n - 1, depthLimit);
    }
  
    // The main function that implements Introsort
    // low  --> Starting index,
    // high  --> Ending index,
    // depthLimit  --> recursion level
    private void sortData(int begin, int end, int depthLimit) {
        if (end - begin > 16) {
            if (depthLimit == 0) {
                this.heapSort(begin, end);
                return;
            }

            depthLimit = depthLimit - 1;
            int pivot = findPivot(begin, begin + ((end - begin) / 2) + 1, end);
            swap(pivot, end);
            int partitionIndex = partition(begin, end);

            // Separately sort elements before partition and after partition
            sortData(begin, partitionIndex - 1, depthLimit);
            sortData(partitionIndex + 1, end, depthLimit);
        }

        else {
            insertionSort(begin, end);
        }
    }

    // The utility function to insert the data
    private void dataAppend(int temp) {
        array[n] = temp;
        n++;
    }

    // The utility function to swap two elements
    private void swap(int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    // To maxHeap a subtree rooted with node i which is
    // an index in a[]. heapN is size of heap
    private void maxHeap(int i, int heapN, int begin) {
        int temp = array[begin + i - 1];
        int child;

        while (i <= heapN / 2) {
            child = 2 * i;

            if (child < heapN && array[begin + child - 1] < array[begin + child])
                child++;

            if (temp >= array[begin + child - 1])
                break;

            array[begin + i - 1] = array[begin + child - 1];
            i = child;
        }
        array[begin + i - 1] = temp;
    }

    // Function to build the heap (rearranging the array)
    private void heapify(int begin, int end, int heapN) {
        for (int i = (heapN) / 2; i >= 1; i--)
            maxHeap(i, heapN, begin);
    }

    // main function to do heapsort
    private void heapSort(int begin, int end) {
        int heapN = end - begin;

        // Build heap (rearrange array)
        this.heapify(begin, end, heapN);

        // One by one extract an element from heap
        for (int i = heapN; i >= 1; i--) {

            // Move current root to end
            swap(begin, begin + i);

            // call maxHeap() on the reduced heap
            maxHeap(1, i, begin);
        }
    }

    // function that implements insertion sort
    private void insertionSort(int left, int right) {

        for (int i = left; i <= right; i++) {
            int key = array[i];
            int j = i;
          
            while (j > left && array[j - 1] > key) {
                array[j] = array[j - 1];
                j--;
            }
            array[j] = key;
        }
    }

    // Function for finding the median of the three elements
    private int findPivot(int el1, int el2, int el3) {
        int max = Math.max(Math.max(array[el1], array[el2]), array[el3]);
        int min = Math.min(Math.min(array[el1], array[el2]), array[el3]);
        int median = max ^ min ^ array[el1] ^ array[el2] ^ array[el3];
        if (median == array[el1])
            return el1;
        if (median == array[el2])
            return el2;
        return el3;
    }

    // This function takes the last element as pivot, places
    // the pivot element at its correct position in sorted
    // array, and places all smaller (smaller than pivot)
    // to the left of the pivot and greater elements to the right of the pivot
    private int partition(int low, int high) {
        
      int pivot = array[high];
      int i = (low - 1);
      
      for (int j = low; j <= high - 1; j++) {
          if (array[j] <= pivot) {
              i++;
              swap(i, j);
          }
      }
      swap(i + 1, high);
      return (i + 1);
    }

    // A utility function to print the array data
    private void printData() {
      for (int i = 0; i < n; i++)
          System.out.print(array[i] + " ");
    }
}
