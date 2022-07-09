package niuke;

import java.util.*;

/**
 * @Author: liuqihong
 * @Description: 称砝码
 * @Date Created in  2022-07-09 21:49
 * @Modified By:
 */
public class WeighingWeight {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        List<Integer> weight = new ArrayList<>();
        List<Integer> quantity = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            weight.add(input.nextInt());
        }
        for (int i = 0; i < n; i++) {
            quantity.add(input.nextInt());
        }
        Set<Integer> set = new HashSet<>();
        set.add(0);
        // 第一顶层循环遍历 砝码有多少个不同的重量
        for (int i = 0; i < n; i++) {
            // 用list 保存组合出来的重量，用于下面循环add不影响Set集合
            List<Integer> list = new ArrayList<>(set);
            // 遍历每个重量砝码的个数
            for (int j = 1; j <= quantity.get(i); j++){
                // 组合结果集
                for (int k = 0; k < list.size(); k++) {
                    set.add(list.get(k) + weight.get(i) * j);
                }
            }
        }
        // 最后 set 的 size 为结果
        System.out.println(set.size());
    }
}
