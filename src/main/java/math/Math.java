package math;

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
}
