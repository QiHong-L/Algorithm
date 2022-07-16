package dynamic;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @Author: liuqihong
 * @Description: 剑指 Offer II 041. 滑动窗口的平均值
 * @Date Created in  2022-07-16 10:44
 * @Modified By:
 */
public class MovingAverage {
    int length;
    Queue<Integer> queue;
    int sum;

    public MovingAverage(int size) {
        length = size;
        queue = new LinkedList<>();
        sum = 0;
    }

    public double next(int val) {
        if (queue.size() == length) {
            sum -= queue.poll();
        }
        sum += val;
        return sum / queue.size();
    }
}
