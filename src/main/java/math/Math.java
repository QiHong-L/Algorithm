package math;

import java.util.ArrayList;
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

}
