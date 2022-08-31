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

}
