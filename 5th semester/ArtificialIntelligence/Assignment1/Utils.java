import java.io.IOException;

public class Utils {
    /**
     * Calculates the variance of the array
     * @param arr
     */
    public static double var(int[] arr){
        double sum = 0;
        for(int xi : arr){
            sum += xi;
        }
        double avg = sum / arr.length;
        sum = 0;
        for(int xi : arr){
            sum += (xi - avg) * (xi - avg);
        }
        return sum / arr.length;
    }
    public static void main(String[] args)throws IOException {
        System.out.println();
    }
}
