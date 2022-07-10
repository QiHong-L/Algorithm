package dynamic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-07-02 11:17
 * @Modified By:
 */
public class Dynamic {
    /**
     * 871. 最低加油次数
     *
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
                if (dp[j] >= stations[i][0]) {
                    dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
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
     *
     * @param arr
     * @return
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(arr);
        int i = 0, j = 1;
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

    /**
     * 736. Lisp 语法解析
     *
     * @param expression
     * @return
     */
    Map<String, Deque<Integer>> scope = new HashMap<>();
    int start = 0;

    public int evaluate(String expression) {
        return innerEvaluate(expression);
    }

    public int innerEvaluate(String expression) {
        if (expression.charAt(start) != '(') {
            if (Character.isLowerCase(expression.charAt(start))) {
                String var = parseVar(expression);
                return scope.get(var).peek();
            } else {
                return parseInt(expression);
            }
        }
        int ret;
        start++; // 移除左括号
        if (expression.charAt(start) == 'l') { //let 表达式
            start += 4;
            List<String> vars = new ArrayList<>();
            while (true) {
                if (!Character.isLowerCase(expression.charAt(start))) {
                    ret = innerEvaluate(expression);
                    break;
                }
                String var = parseVar(expression);
                if (expression.charAt(start) == ')') {
                    ret = scope.get(var).peek();
                    break;
                }
                vars.add(var);
                start++;
                int e = innerEvaluate(expression);
                scope.putIfAbsent(var, new ArrayDeque<Integer>());
                scope.get(var).push(e);
                start++;
            }
            for (String var : vars) {
                scope.get(var).pop();
            }
        } else if (expression.charAt(start) == 'a') {
            start += 4;
            int e1 = innerEvaluate(expression);
            start++;
            int e2 = innerEvaluate(expression);
            ret = e1 + e2;
        } else {
            start += 5;
            int e1 = innerEvaluate(expression);
            start++;
            int e2 = innerEvaluate(expression);
            ret = e1 * e2;
        }
        start++;
        return ret;
    }

    // 解析整数
    public int parseInt(String expression) {
        int n = expression.length();
        int ret = 0, sign = 1;
        if (expression.charAt(start) == '-') {
            sign = -1;
            start++;
        }
        while (start < n && Character.isDigit(expression.charAt(start))) {
            ret = ret * 10 + (expression.charAt(start) - '0');
            start++;
        }
        return sign * ret;
    }

    public String parseVar(String expression) { // 解析变量
        int n = expression.length();
        StringBuffer ret = new StringBuffer();
        while (start < n && expression.charAt(start) != ' ' && expression.charAt(start) != ')') {
            ret.append(expression.charAt(start));
            start++;
        }
        return ret.toString();
    }

    /**
     * 648. 单词替换
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        String[] s = sentence.split(" ");
        for (int i = 0; i < s.length; i++) {
            s[i] = isRoot(dictionary, s[i]);
        }
        String collect = Arrays.stream(s).collect(Collectors.joining(" "));
        return collect;
    }

    public String isRoot(List<String> dictionary, String str) {
        int size = Integer.MAX_VALUE;
        String result = str;
        for (String root : dictionary) {
            if (str.startsWith(root)) {
                if (size > root.length()) {
                    size = root.length();
                    result = root;
                }
            }
        }
        return result;
    }

    /**
     * 1217.玩筹码
     *
     * @param position
     * @return
     */
    public int minCostToMoveChips(int[] position) {
        int baseCount = 0, evenCount = 0;
        for (int i = 0; i < position.length; i++) {
            if (position[i] % 2 == 0) evenCount++;
            else baseCount++;
        }
        return baseCount > evenCount ? evenCount : baseCount;
    }

    /**
     * 873. 最长的斐波那契子序列的长度
     *
     * @param arr
     * @return
     */
    public int lenLongestFibSubseq(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }
        int[][] dp = new int[n][n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j > 0 && arr[j] * 2 > arr[i]; j--) {
                int k = map.getOrDefault(arr[i] - arr[j], -1);
                if (k >= 0) {
                    dp[j][i] = Math.max(dp[k][j] + 1, 3);
                }
                ans = Math.max(ans, dp[j][i]);
            }
        }
        return ans;
    }

    /**
     * 741. 摘樱桃
     * @param grid
     * @return
     */
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] f = new int[n * 2 - 1][n][n];
        for (int i = 0; i < n * 2 - 1; ++i) {
            for (int j = 0; j < n; ++j) {
                Arrays.fill(f[i][j], Integer.MIN_VALUE);
            }
        }
        f[0][0][0] = grid[0][0];
        for (int k = 1; k < n * 2 - 1; ++k) {
            for (int x1 = Math.max(k - n + 1, 0); x1 <= Math.min(k, n - 1); ++x1) {
                int y1 = k - x1;
                if (grid[x1][y1] == -1) {
                    continue;
                }
                for (int x2 = x1; x2 <= Math.min(k, n - 1); ++x2) {
                    int y2 = k - x2;
                    if (grid[x2][y2] == -1) {
                        continue;
                    }
                    int res = f[k - 1][x1][x2]; // 都往右
                    if (x1 > 0) {
                        res = Math.max(res, f[k - 1][x1 - 1][x2]); // 往下，往右
                    }
                    if (x2 > 0) {
                        res = Math.max(res, f[k - 1][x1][x2 - 1]); // 往右，往下
                    }
                    if (x1 > 0 && x2 > 0) {
                        res = Math.max(res, f[k - 1][x1 - 1][x2 - 1]); // 都往下
                    }
                    res += grid[x1][y1];
                    if (x2 != x1) { // 避免重复摘同一个樱桃
                        res += grid[x2][y2];
                    }
                    f[k][x1][x2] = res;
                }
            }
        }
        return Math.max(f[n * 2 - 2][n - 1][n - 1], 0);
    }
}
