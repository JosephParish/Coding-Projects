import java.util.ArrayList;
import java.util.List;

/**
 * <p> BucketSort Algorithm to sort unsorted arrays (of decimals),
 * using separte lists (buckets) and another sorting algorithm (insertion sort)</p>
 *
 * @author Joseph Parish.
 * @version 1.0.6
 * Last Changed: 26/06/25
 */
public class BucketSort {

  /**
    * BucketSort main body - sorts the elements into buckets (lists) via first significant bit
    * then performing a sorting algorithm (insertion sort) and then recombining the buckets.
    * 
    * @param array - array to be sorted
    */
  satic void bucketSort(float[] array) {
      int n = array.length;
      List<Float>[] buckets = new ArrayList[n];
    
      for (int i = 0; i < n; i++) {
          buckets[i] = new ArrayList<>();
      }
      for (int i = 0; i < n; i++) {
          int bi = (int) (n * array[i]);
          buckets[bi].add(arr[i]);
      }
      for (int i = 0; i < n; i++) {
          insertionSort(buckets[i]);
      }
    
      int index = 0;
      for (int i = 0; i < n; i++) {
          for (int j = 0; j < buckets[i].size(); j++) {
              array[index++] = buckets[i].get(j);
          }
      }
  }

  /**
     * InsertionSort, loops though elements
     * placing current element behind all larger elements behind it
     *
     * @param bucket - list to be sorted
     */
  public static void insertionSort(List<Float> bucket) {
      for (int i = 1; i < bucket.size(); ++i) {
          float key = bucket.get(i);
          int j = i - 1;
          while (j >= 0 && bucket.get(j) > key) {
              bucket.set(j + 1, bucket.get(j));
              j--;
          }
          bucket.set(j + 1, key);
      }
  }
}
