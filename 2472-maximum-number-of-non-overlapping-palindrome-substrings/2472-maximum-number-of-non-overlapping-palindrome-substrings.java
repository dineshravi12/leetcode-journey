class Solution {
    public int maxPalindromes(String s, int k) {
        int n = s.length();

        boolean[][] pal = new boolean[n][n];

        // Palindrome preprocessing
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    if (j - i < 2 || pal[i + 1][j - 1]) {
                        pal[i][j] = true;
                    }
                }
            }
        }

        int[] dp = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            dp[i] = dp[i + 1]; // skip current position

            for (int j = i + k - 1; j < n; j++) {
                if (pal[i][j]) {
                    dp[i] = Math.max(dp[i], 1 + dp[j + 1]);
                    break; // shortest palindrome is enough
                }
            }
        }

        return dp[0];
    }
}