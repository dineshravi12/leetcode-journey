class Solution {

    static class Result {
        long weight;
        int[] ids;

        Result(long weight, int[] ids) {
            this.weight = weight;
            this.ids = ids;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();

        int[][] arr = new int[n][4]; // start, end, weight, originalIndex
        for (int i = 0; i < n; i++) {
            arr[i][0] = intervals.get(i).get(0);
            arr[i][1] = intervals.get(i).get(1);
            arr[i][2] = intervals.get(i).get(2);
            arr[i][3] = i;
        }

        Arrays.sort(arr, (a, b) -> Integer.compare(a[0], b[0]));

        int[] starts = new int[n];
        for (int i = 0; i < n; i++) {
            starts[i] = arr[i][0];
        }

        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            next[i] = firstGreater(starts, arr[i][1]);
        }

        Result[][] dp = new Result[n + 1][5];

        for (int k = 0; k <= 4; k++) {
            dp[n][k] = new Result(0, new int[0]);
        }

        for (int i = n - 1; i >= 0; i--) {
            dp[i][0] = new Result(0, new int[0]);

            for (int k = 1; k <= 4; k++) {

                Result skip = dp[i + 1][k];

                Result nxt = dp[next[i]][k - 1];
                long takeWeight = arr[i][2] + nxt.weight;

                int[] takeIds = insertSorted(nxt.ids, arr[i][3]);

                Result take = new Result(takeWeight, takeIds);

                dp[i][k] = better(skip, take);
            }
        }

        return dp[0][4].ids;
    }

    private int firstGreater(int[] starts, int end) {
        int l = 0, r = starts.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (starts[m] > end) {
                r = m;
            } else {
                l = m + 1;
            }
        }
        return l;
    }

    private Result better(Result a, Result b) {
        if (a.weight != b.weight) {
            return a.weight > b.weight ? a : b;
        }

        int cmp = lexCompare(a.ids, b.ids);
        return cmp <= 0 ? a : b;
    }

    private int lexCompare(int[] a, int[] b) {
        int m = Math.min(a.length, b.length);

        for (int i = 0; i < m; i++) {
            if (a[i] != b[i]) {
                return Integer.compare(a[i], b[i]);
            }
        }

        return Integer.compare(a.length, b.length);
    }

    private int[] insertSorted(int[] arr, int val) {
        int[] res = new int[arr.length + 1];

        int i = 0, j = 0;
        boolean added = false;

        while (i < arr.length) {
            if (!added && val < arr[i]) {
                res[j++] = val;
                added = true;
            } else {
                res[j++] = arr[i++];
            }
        }

        if (!added) {
            res[j] = val;
        }

        return res;
    }
}