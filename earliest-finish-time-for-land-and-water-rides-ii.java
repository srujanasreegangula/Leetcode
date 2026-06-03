import java.util.*;

class Solution {
    public int earliestFinishTime(int[] ls, int[] ld, int[] ws, int[] wd) {
        long ans = Long.MAX_VALUE;

        int[][] water = new int[ws.length][2];
        for (int i = 0; i < ws.length; i++) {
            water[i] = new int[]{ws[i], wd[i]};
        }

        int[][] land = new int[ls.length][2];
        for (int i = 0; i < ls.length; i++) {
            land[i] = new int[]{ls[i], ld[i]};
        }

        ans = Math.min(ans, solve(ls, ld, water));
        ans = Math.min(ans, solve(ws, wd, land));

        return (int) ans;
    }

    private long solve(int[] start, int[] dur, int[][] other) {
        Arrays.sort(other, (a, b) -> a[0] - b[0]);

        int m = other.length;
        int[] s = new int[m];
        long[] pre = new long[m];
        long[] suf = new long[m];

        for (int i = 0; i < m; i++) s[i] = other[i][0];

        pre[0] = other[0][1];
        for (int i = 1; i < m; i++)
            pre[i] = Math.min(pre[i - 1], other[i][1]);

        suf[m - 1] = (long) other[m - 1][0] + other[m - 1][1];
        for (int i = m - 2; i >= 0; i--)
            suf[i] = Math.min(suf[i + 1],
                    (long) other[i][0] + other[i][1]);

        long res = Long.MAX_VALUE;

        for (int i = 0; i < start.length; i++) {
            long end = (long) start[i] + dur[i];

            int idx = Arrays.binarySearch(s, (int) end);
            if (idx < 0) idx = -idx - 2;
            else {
                while (idx + 1 < m && s[idx + 1] <= end) idx++;
            }

            if (idx >= 0) res = Math.min(res, end + pre[idx]);
            if (idx + 1 < m) res = Math.min(res, suf[idx + 1]);
        }

        return res;
    }
}
