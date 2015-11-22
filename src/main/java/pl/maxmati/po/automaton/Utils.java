package pl.maxmati.po.automaton;

/**
 * Created by maxmati on 11/22/15
 */
public class Utils {
    public static int mod(int a, int b){
        int result = a % b;
        if (result < 0)
            result += b;
        return result;
    }
}
