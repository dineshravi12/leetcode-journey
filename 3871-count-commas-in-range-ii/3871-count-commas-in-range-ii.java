class Solution {
    public long countCommas(long n) {
        long ans = 0;
        
        long start = 1; // 10^(d-1)
        for (int d = 1; start <= n; d++) {
            long end;
            
            // Avoid overflow for the last digit range
            if (start > Long.MAX_VALUE / 10) {
                end = Long.MAX_VALUE;
            } else {
                end = start * 10 - 1;
            }
            
            long count = Math.min(n, end) - start + 1;
            long commasPerNumber = (d - 1) / 3;
            
            ans += count * commasPerNumber;
            
            if (start > Long.MAX_VALUE / 10) break;
            start *= 10;
        }
        
        return ans;
    }
}