package dynamic;

import java.util.*;

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

    /**
     * 736. Lisp 语法解析
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
                scope.putIfAbsent(var,new ArrayDeque<Integer>());
                scope.get(var).push(e);
                start++;
            }
            for (String var : vars) {
                scope.get(var).pop();
            }
        } else if (expression.charAt(start) == 'a') {
            start+=4;
            int e1 = innerEvaluate(expression);
            start++;
            int e2 = innerEvaluate(expression);
            ret = e1 + e2;
        } else {
            start+=5;
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
}