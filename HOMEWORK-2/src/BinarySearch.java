public class BinarySearch {
    public static void main(String[] args) {
        int x = Integer.parseInt(args[0]);
        int[] data = new int[args.length-1];
        for (int i = 1; i < args.length; i++) {
            data[i-1] = Integer.parseInt(args[i]);
        }
        System.out.println(binarySearch(data, x));
    }

    public static int binarySearch(int[] a, int x) {
        return _iterativeBinarySearch(a, x, 0, a.length);
    }

    public static int _iterativeBinarySearch(int [] a, int x, int l, int r) {
        // pre: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT
        while (l < r) {
            int mid = l+(r-l)/2;
            if (a[mid] > x)
                l = mid+1;
            else
                r = mid;
        }
        return r;
    }

    public static int _recursiveBinarySearchWith(int [] a, int x, int l, int r) {
        // pre: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT
        if (l >= r)
            return r;
        // post: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r
        // pre: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r
        int mid = l+(r-l)/2;
        // post: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r && mid = l+(r-l)/2
        // pre: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r && mid = l+(r-l)/2
        if (a[mid] > x)
            return _recursiveBinarySearchWith(a, x, mid+1, r);
        // post: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r && mid = l+(r-l)/2 && a[mid] <= x
        // pre: l >= 0 && r >= 0 && l <= MAX_INT && r <= MAX_INT && l < r && mid = l+(r-l)/2 && a[mid] <= x
        return _recursiveBinarySearchWith(a, x, l, mid);
        // post: False
    }
}
