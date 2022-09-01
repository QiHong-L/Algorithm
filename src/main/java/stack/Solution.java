package stack;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-08-31 21:53
 * @Modified By:
 */
public class Solution {

    /**
     * 946. 验证栈序列
     * @param pushed
     * @param popped
     * @return
     */
    public static boolean validateStackSequences(int[] pushed, int[] popped) {
        /**
         * 少一次边界判断，算法复杂度较高
         */
//        Stack<Integer> stack = new Stack<>();
//        int index = 0;
//        for (int i = 0; i < pushed.length; i++) {
//            stack.push(pushed[i]);
//            while (!stack.isEmpty()) {
//                if (stack.peek() == popped[index]) {
//                    stack.pop();
//                    index++;
//                } else {
//                    break;
//                }
//            }
//        }
//        return stack.isEmpty();
        /**
         * 官方解法
         */
        Stack<Integer> stack = new Stack<>();
        for (int i = 0, j = 0; i < pushed.length; i++) {
            stack.push(pushed[i]);
            while (!stack.isEmpty() && stack.peek() == popped[j]) {
                j++;
                stack.pop();
            }
        }
        return stack.isEmpty();
    }

    /**
     * 1475. 商品折扣后的最终价格
     * @param prices
     * @return
     */
    public int[] finalPrices(int[] prices) {
        int[] ans = new int[prices.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = prices.length - 1; i > 0 ; i--) {
            while (!stack.isEmpty() && stack.peek() > prices[i]) {
                stack.pop();
            }
            ans[i] = stack.isEmpty() ? prices[i] : prices[i] - stack.peek();
            stack.push(prices[i]);
        }
        return ans;
    }

    public static void main(String[] args) {
        int[] pushed = {1,2,3,4,5};
        int[] popped = {4,5,3,2,1};
        boolean b = validateStackSequences(pushed, popped);
    }
}
