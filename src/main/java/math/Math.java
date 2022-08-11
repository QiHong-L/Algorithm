package math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-06-30 9:04
 * @Modified By:
 */
public class Math {

    private static final int MOD = 1000000007;

    /**
     * 1175. 质数排列
     *  1 到 n 的数设计排列方案，使得所有的「质数」都应该被放在「质数索引」（索引从 1 开始）上
     * @param n
     * @return
     */
    public int numPrimeArrangements(int n) {
        int numPrime = 0;
        for (int i = 1; i <= n; i++) {
            if (isPrime(i)) {
                numPrime++;
            }
        }
        return (int) (factorial(numPrime) * factorial(n - numPrime) % MOD);
    }

    public boolean isPrime(int n) {
        if (n == 1) {
            return false;
        }
        for (int i = 2; i * i  <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public long factorial(int n) {
        long res = 1;
        for (int i = 1; i <= n; i++) {
            res *= i;
            res %= MOD;
        }
        return res;
    }


    static final int ADDITION = -1;
    static final int SUBTRACTION = -2;
    static final int MULTIPLICATION = -3;

    /**
     * 241. 为运算表达式设计优先级
     * @param expression
     * @return
     */
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> ops = new ArrayList<Integer>();
        for (int i = 0; i < expression.length();) {
            if (!Character.isDigit(expression.charAt(i))) {
                if (expression.charAt(i) == '+') {
                    ops.add(ADDITION);
                } else if (expression.charAt(i) == '-') {
                    ops.add(SUBTRACTION);
                } else {
                    ops.add(MULTIPLICATION);
                }
                i++;
            } else {
                int t = 0;
                while (i < expression.length() && Character.isDigit(expression.charAt(i))) {
                    t = t * 10 + expression.charAt(i) - '0';
                    i++;
                }
                ops.add(t);
            }
        }
        List<Integer>[][] dp = new List[ops.size()][ops.size()];
        for (int i = 0; i < ops.size(); i++) {
            for (int j = 0; j < ops.size(); j++) {
                dp[i][j] = new ArrayList<Integer>();
            }
        }
        for (int i = 0; i < ops.size(); i += 2) {
            dp[i][i].add(ops.get(i));
        }
        for (int i = 3; i <= ops.size(); i++) {
            for (int j = 0; j + i <= ops.size(); j += 2) {
                int l = j;
                int r = j + i - 1;
                for (int k = j + 1; k < r; k += 2) {
                    List<Integer> left = dp[l][k - 1];
                    List<Integer> right = dp[k + 1][r];
                    for (int num1 : left) {
                        for (int num2 : right) {
                            if (ops.get(k) == ADDITION) {
                                dp[l][r].add(num1 + num2);
                            } else if (ops.get(k) == SUBTRACTION) {
                                dp[l][r].add(num1 - num2);
                            } else if (ops.get(k) == MULTIPLICATION) {
                                dp[l][r].add(num1 * num2);
                            }
                        }
                    }
                }
            }
        }
        return dp[0][ops.size() - 1];
    }

    /**
     *31. 下一个排列
     * @param nums
     */
    public void nextPermutation(int[] nums) {
//        for (int i = nums.length - 1; i >= 0; i--) {
//            for (int j = nums.length - 1; j > i; j--) {
//                if (nums[i] < nums[j]) {
//                    swap(nums,i,j);
//                    Arrays.sort(nums,i+1,nums.length);
//                    return;
//                }
//            }
//        }
//        Arrays.sort(nums);
        // 更低时间复杂度的解决办法
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i+1]) {
            i--;
        }
        if (i >= 0) {
            int j = nums.length - 1;
            while (j >= 0 && nums[i] >= nums[j]) {
                j--;
            }
            swap(nums,i,j);
        }
        reverse(nums,i+1);
    }

    private void swap (int[] nums,int i ,int j) {
        int numAtI = nums[i];
        nums[i] = nums[j];
        nums[j] = numAtI;
    }

    public void reverse(int[] nums, int start) {
        int left = start,right = nums.length-1;
        while (left < right) {
            swap(nums,left,right);
            left++;
            right--;
        }
    }

    /**
     * 556. 下一个更大元素 III
     * @param n
     * @return
     */
    public int nextGreaterElement(int n) {
        char[] nums = Integer.toString(n).toCharArray();
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i+1]) {
            i--;
        }
        if (i < 0) {
            return -1;
        }
        int j = nums.length - 1;
        while (j >= 0 && nums[i] >= nums[j]) {
            j--;
        }
        swap(nums,i,j);
        reverse(nums, i + 1);
        long ans = Long.parseLong(new String(nums));
        return ans > Integer.MAX_VALUE ? -1 : (int) ans;
    }

    private void swap (char[] nums,int i ,int j) {
        char numAtI = nums[i];
        nums[i] = nums[j];
        nums[j] = numAtI;
    }

    public void reverse(char[] nums, int start) {
        int left = start,right = nums.length-1;
        while (left < right) {
            swap(nums,left,right);
            left++;
            right--;
        }
    }


    /**
     * 593. 有效的正方形
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @return
     */
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        if (Arrays.equals(p1, p2)) {
            return false;
        }
        if (help(p1, p2, p3, p4)) {
            return true;
        }
        if (Arrays.equals(p1, p3)) {
            return false;
        }
        if (help(p1, p3, p2, p4)) {
            return true;
        }
        if (Arrays.equals(p1, p4)) {
            return false;
        }
        if (help(p1, p4, p2, p3)) {
            return true;
        }
        return false;
    }

    public boolean help(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[] v1 = {p1[0] - p2[0], p1[1] - p2[1]};
        int[] v2 = {p3[0] - p4[0], p3[1] - p4[1]};
        if (checkMidPoint(p1, p2, p3, p4) && checkLength(v1, v2) && calCos(v1, v2)) {
            return true;
        }
        return false;
    }

    public boolean checkLength(int[] v1, int[] v2) {
        return (v1[0] * v1[0] + v1[1] * v1[1]) == (v2[0] * v2[0] + v2[1] * v2[1]);
    }

    public boolean checkMidPoint(int[] p1, int[] p2, int[] p3, int[] p4) {
        return (p1[0] + p2[0]) == (p3[0] + p4[0]) && (p1[1] + p2[1]) == (p3[1] + p4[1]);
    }

    public boolean calCos(int[] v1, int[] v2) {
        return (v1[0] * v2[0] + v1[1] * v2[1]) == 0;
    }

    /**
     * 有序队列
     * @param s
     * @param k
     * @return
     */
    public String orderlyQueue(String s, int k) {
        if (k == 1) {
            String smallest = s;
            StringBuilder sb = new StringBuilder(s);
            int n = s.length();
            for (int i = 1; i < n; i++) {
                char c = sb.charAt(0);
                sb.deleteCharAt(0);
                sb.append(c);
                if (sb.toString().compareTo(smallest) < 0) {
                    smallest = sb.toString();
                }
            }
            return smallest;
        } else {
            char[] arr = s.toCharArray();
            Arrays.sort(arr);
            return new String(arr);
        }
    }

}
