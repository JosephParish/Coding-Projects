public class PigeonholeSort {

  public static void pigeonhole_sort(int[] array) {

        int min = array[0];
        int max = array[0];
        int range, i, j, index; 

        for(int a = 0; a < array.length(); a++) {
            if(array[a] > max)
                max = array[a];
            if(array[a] < min)
                min = array[a];
        }

        range = max - min + 1;
        int[] temp = new int[range];
        Arrays.fill(temp, 0);

        for(i = 0; i < array.length(); i++) {
            temp[array[i] - min]++;
        }
        
        index = 0;

        for(j = 0; j < range; j++) {
            while(temp[j] --> 0) {
                array[index++]=j+min;
            }
        }
    }
}
