class Solution {
    public int minimumCost(int[] cost) {
        Arrays.sort(cost);
        int total = 0;
        int n = cost.length;
        for (int i = n - 1;i >= 0; i -= 3) {
            total += cost[i];
            if (i - 1 >= 0) {
                total += cost[i - 1];
            }
        }
        return total;
        
    }
}
