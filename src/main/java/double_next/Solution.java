package double_next;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-08-25 22:25
 * @Modified By:
 */
public class Solution {
    public static void main(String[] args) {

    }

    /**
     * 658. 找到 K 个最接近的元素
     * @param arr
     * @param k
     * @param x
     * @return
     */
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        // 方法一：排序
        List<Integer> list = new ArrayList<>();
        for (int num : arr) {
            list.add(num);
        }
        Collections.sort(list,(a,b) -> {
            if (Math.abs(a - x) != Math.abs(b - x)) {
                return Math.abs(a - x) - Math.abs(b - x);
            } else {
                return a - b;
            }
        });
        List<Integer> ans = list.subList(0,k);
        Collections.sort(ans);
        return ans;
    }

    /**
     * 1768. 交替合并字符串
     * @param word1
     * @param word2
     * @return
     */
    public String mergeAlternately(String word1, String word2) {
//        int length = Math.min(word1.length(),word2.length());
//        String result = "";
//        for (int i = 0; i < length; i++) {
//            result += word1.charAt(i);
//            result += word2.charAt(i);
//        }
//        if (length < word1.length()) {
//            result += word1.substring(length,word1.length());
//        } else if (length < word2.length()) {
//            result += word2.substring(length,word2.length());
//        }
//        return result;
        int n = word1.length();
        int m = word2.length();
        StringBuilder str = new StringBuilder();
        int i = 0, j = 0;
        while (i < n || j < m) {
            if (i < n) {
                str.append(word1.charAt(i));
                i++;
            }
            if (j < m) {
                str.append(word2.charAt(j));
                j++;
            }
        }
        return str.toString();
    }

}
