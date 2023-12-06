/**
 * <pre>
 * User: clean_brain
 * Date: 2023-12-04
 * Comments:
 * </pre>
 */
public class Main {
    private static int cap;
    private static int n;
    private static int[] deliveries;
    private static int[] pickups;

    public static void main(String[] args) {
        // result : 16
        // init1();
        // result : 30
        init2();
        // result : 6
        // init3();
        // result : 8
        // init4();
        // result : 8
        // init5();
        long distance = new Solution().solution(cap, n, deliveries, pickups);
        System.out.println(distance);
    }

    // result : 16
    public static void init1() {
        cap = 4;
        n = 5;
        deliveries = new int[]{1, 0, 3, 1, 2};
        pickups = new int[]{0, 3, 0, 4, 0};
    }

    // result : 30
    public static void init2() {
        cap = 2;
        n = 7;
        deliveries = new int[]{1, 0, 2, 0, 1, 0, 2};
        pickups = new int[]{0, 2, 0, 1, 0, 2, 0};
    }

    // result : 6
    public static void init3() {
        cap = 1;
        n = 5;
        deliveries = new int[]{0, 0, 1, 0, 0};
        pickups = new int[]{0, 0, 0, 0, 0};
    }

    // result : 8
    public static void init4() {
        cap = 2;
        n = 2;
        deliveries = new int[]{0, 0};
        pickups = new int[]{0, 4};
    }

    // result : 8
    public static void init5() {
        cap = 3;
        n = 2;
        deliveries = new int[]{2, 4};
        pickups = new int[]{4, 2};
    }
}

