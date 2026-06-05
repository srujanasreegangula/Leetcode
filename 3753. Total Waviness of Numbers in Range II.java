class Solution {

    static class Node {
        long cnt;
        long sum;

        Node(long c, long s) {
            cnt = c;
            sum = s;
        }
    }

    private char[] digits;
    private Node[][][][] memo;

    public long totalWaviness(long num1, long num2) {
        return solve(num2) - solve(num1 - 1);
    }

    private long solve(long x) {
        if (x <= 0) return 0;

        digits = Long.toString(x).toCharArray();
        int n = digits.length;

        memo = new Node[n + 1][11][11][17];

        return dfs(0, 10, 10, 0, true).sum;
    }

    private Node dfs(int pos, int prev2, int prev1, int len, boolean tight) {
        if (pos == digits.length) {
            return new Node(1, 0);
        }

        if (!tight && memo[pos][prev2][prev1][len] != null) {
            return memo[pos][prev2][prev1][len];
        }

        int limit = tight ? digits[pos] - '0' : 9;

        long totalCnt = 0;
        long totalSum = 0;

        for (int d = 0; d <= limit; d++) {
            boolean ntight = tight && (d == limit);

            if (len == 0 && d == 0) {
                Node nxt = dfs(pos + 1, 10, 10, 0, ntight);
                totalCnt += nxt.cnt;
                totalSum += nxt.sum;
            } else {
                int add = 0;

                if (len >= 2) {
                    if ((prev1 > prev2 && prev1 > d) ||
                        (prev1 < prev2 && prev1 < d)) {
                        add = 1;
                    }
                }

                int nPrev2, nPrev1;

                if (len == 0) {
                    nPrev2 = 10;
                    nPrev1 = d;
                } else if (len == 1) {
                    nPrev2 = prev1;
                    nPrev1 = d;
                } else {
                    nPrev2 = prev1;
                    nPrev1 = d;
                }

                Node nxt = dfs(
                        pos + 1,
                        nPrev2,
                        nPrev1,
                        Math.min(16, len + 1),
                        ntight
                );

                totalCnt += nxt.cnt;
                totalSum += nxt.sum + (long) add * nxt.cnt;
            }
        }

        Node res = new Node(totalCnt, totalSum);

        if (!tight) {
            memo[pos][prev2][prev1][len] = res;
        }

        return res;
    }
}
