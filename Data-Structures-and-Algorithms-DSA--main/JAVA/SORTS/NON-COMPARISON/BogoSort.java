public class BogoSort {

  public static int[] bogoSort(int[] array) {
    while (isSorted(array) == false) {
      for (int i = 1; i < array.length; i++) {
        swap(array, i, (int)(Math.random() * i));
      }
    }
  }

  private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++){
            if (array[i] < array[i - 1]){
                return false;
            }
        }
        return true;
    }
}





