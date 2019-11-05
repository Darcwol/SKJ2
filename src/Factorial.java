public class Factorial {
    static public int compute(int n) {
        int acc = 1;
        for(int i = 1 ; i <= n ; i++) {
            acc = acc * i;
        }
        return acc;
    }
}