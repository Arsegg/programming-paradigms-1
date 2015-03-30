public class BinarySearchSpan {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int len = args.length - 1;
        int[] a = new int[len];
        for (int i = 0; i < len; i++) {
            a[i] = Integer.parseInt(args[i + 1]);
        }
        int l = lowerBound(a, x, -1, len);
        int r = upperBound(a, x, -1, len);
        System.out.println(l + " " + (r - l) );
    }

    // pre: l <= r && a[l] >= x >= a[r] && a = {ai | i = 0..N-1} && l < 0 && r > N-1
    public static int lowerBound(int[] a, int x, int l, int r) {
        // pre:
        // inv: l' <= R <= r' && a[l'] > x >= a[r']
        while (l + 1 < r) {
            // pre:
            int mid = l + (r - l) / 2;
            // post: mid' = (l + r) / 2
            // pre:
            if (a[mid] > x) {
                // pre:
                l = mid;
                // post: l' = mid && a[l] > x
            } else {
                // pre:
                r = mid;
                // post: r' = mid && x >= a[r]
            }
            // post: a[l'] > x >= a[r']
        }
        // post: l + 1 = r && a[r + 1] > x >= a[r]
        return r;
    }
    // post: l <= R <= r && a[R] <= x < a[R + 1]

    // pre: l <= r && a[l] >= x >= a[r]
    public static int upperBound(int[] a, int x, int l, int r) {
        // inv: l' <= R <= r' && a[l'] >= x > a[r']
        // pre:
        if (l + 1 >= r) {
            return r;
        }
        // post: l + 1 < r
        // pre:
        int mid = l + (r - l) / 2;
        // post: mid' = (l + r) / 2
        // pre:
        if (a[mid] >= x) {
            return upperBound(a, x, mid, r);
        }
        // post: a[mid] < x
        // pre:
        return upperBound(a, x, l, mid);
    }
    // post: l <= R <= r && a[R] < x <= a{R + 1]
}