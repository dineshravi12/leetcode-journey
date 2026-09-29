class Solution {

    private Boolean[][][] memo;
    private int m, n;

    public boolean hasValidPath(char[][] grid) {

        m = grid.length;
        n = grid[0].length;

        // first must be '('
        if (grid[0][0] == ')')
            return false;

        // last must be ')'
        if (grid[m - 1][n - 1] == '(')
            return false;

        int len = m + n - 1;

        // valid parenthesis string length must be even
        if ((len & 1) == 1)
            return false;

        memo = new Boolean[m][n][len + 1];

        return dfs(grid, 0, 0, 1);
    }

    private boolean dfs(char[][] grid, int r, int c, int balance) {

        if (balance < 0)
            return false;

        if (memo[r][c][balance] != null)
            return memo[r][c][balance];

        // reached destination
        if (r == m - 1 && c == n - 1) {
            return balance == 0;
        }

        boolean ans = false;

        // down
        if (r + 1 < m) {
            int nextBalance = balance +
                    (grid[r + 1][c] == '(' ? 1 : -1);

            ans |= dfs(grid, r + 1, c, nextBalance);
        }

        // right
        if (!ans && c + 1 < n) {
            int nextBalance = balance +
                    (grid[r][c + 1] == '(' ? 1 : -1);

            ans |= dfs(grid, r, c + 1, nextBalance);
        }

        return memo[r][c][balance] = ans;
    }
}