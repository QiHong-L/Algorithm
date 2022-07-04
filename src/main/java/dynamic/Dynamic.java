package dynamic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-07-02 11:17
 * @Modified By:
 */
public class Dynamic {
    /**
     * 871. 最低加油次数
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if(dp[j] >= stations[i][0]) {
                    dp[j+1] = Math.max(dp[j+1],dp[j] + stations[i][1]);
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 1200. 最小绝对差
     * @param arr
     * @return
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(arr);
        int i = 0,j = 1;
        int num = Integer.MAX_VALUE;
        while (j < arr.length) {
            int abs = Math.abs(arr[j] - arr[i]);
            if (abs <= num) {
                if (abs < num) result.clear();
                List<Integer> list = new ArrayList<>();
                list.add(arr[i]);
                list.add(arr[j]);
                result.add(list);
                num = abs;
            }
            i++;
            j++;
        }
        return result;
    }
}
