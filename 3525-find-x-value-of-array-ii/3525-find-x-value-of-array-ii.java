class Solution {

    class Node {
        int mul;
        long[] freq;

        Node() {
            freq = new long[k];
        }
    }

    private int k;
    private int n;
    private Node[] seg;

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        this.k = k;
        this.n = nums.length;

        seg = new Node[4 * n];
        build(1, 0, n - 1, nums);

        int[] ans = new int[queries.length];

        for (int qi = 0; qi < queries.length; qi++) {
            int idx = queries[qi][0];
            int val = queries[qi][1];
            int start = queries[qi][2];
            int x = queries[qi][3];

            update(1, 0, n - 1, idx, val);

            Node res = query(1, 0, n - 1, start, n - 1);

            ans[qi] = (int) res.freq[x];
        }

        return ans;
    }

    private void build(int node, int l, int r, int[] nums) {
        if (l == r) {
            seg[node] = createLeaf(nums[l]);
            return;
        }

        int mid = (l + r) >>> 1;

        build(node << 1, l, mid, nums);
        build(node << 1 | 1, mid + 1, r, nums);

        seg[node] = merge(seg[node << 1], seg[node << 1 | 1]);
    }

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            seg[node] = createLeaf(val);
            return;
        }

        int mid = (l + r) >>> 1;

        if (idx <= mid) {
            update(node << 1, l, mid, idx, val);
        } else {
            update(node << 1 | 1, mid + 1, r, idx, val);
        }

        seg[node] = merge(seg[node << 1], seg[node << 1 | 1]);
    }

    private Node query(int node, int l, int r, int ql, int qr) {
        if (ql <= l && r <= qr) {
            return seg[node];
        }

        int mid = (l + r) >>> 1;

        if (qr <= mid) {
            return query(node << 1, l, mid, ql, qr);
        }

        if (ql > mid) {
            return query(node << 1 | 1, mid + 1, r, ql, qr);
        }

        Node left = query(node << 1, l, mid, ql, qr);
        Node right = query(node << 1 | 1, mid + 1, r, ql, qr);

        return merge(left, right);
    }

    private Node createLeaf(int val) {
        Node node = new Node();

        int r = val % k;

        node.mul = r;
        node.freq[r] = 1;

        return node;
    }

    private Node merge(Node A, Node B) {
        Node res = new Node();

        res.mul = (int) (((long) A.mul * B.mul) % k);

        for (int i = 0; i < k; i++) {
            res.freq[i] += A.freq[i];
        }

        for (int r = 0; r < k; r++) {
            int newResidue = (int) (((long) A.mul * r) % k);
            res.freq[newResidue] += B.freq[r];
        }

        return res;
    }
}