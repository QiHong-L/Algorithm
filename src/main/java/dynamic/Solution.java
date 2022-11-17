package dynamic;

import java.util.*;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-11-09 21:34
 * @Modified By:
 */
public class Solution {

    /**
     * 253. 会议室 II
     *
     * @param intervals
     * @return
     */
    public int minMeetingRooms(int[][] intervals) {
        // 当 intervals 为 null 没有会议安排，直接返回 0
        if (intervals.length == 0) {
            return 0;
        }
        // 创建有限队列，将加入队列的会议室，按照结束时间排列，先结束的会议室先出
        PriorityQueue<Integer> allocator = new PriorityQueue<>(intervals.length, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1 - o2;
            }
        });
        Arrays.sort(intervals, new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                return o1[0] - o2[0];
            }
        });
        // 新增第一个会议室，存入为结束时间
        allocator.add(intervals[0][1]);
        for (int i = 1; i < intervals.length; i++) {
            // 如果当且会议在最早结束的会议室时间之后，则将会议室抛出
            if (intervals[i][0] >= allocator.peek()) {
                allocator.poll();
            }
            // 新增当前结束时间的会议室
            allocator.add(intervals[i][1]);
        }
        return allocator.size();
    }

    /**
     * 4. 寻找两个正序数组的中位数
     *
     * @param A
     * @param B
     * @return
     */
    public double findMedianSortedArrays(int[] A, int[] B) {
        int m = A.length;
        int n = B.length;
        int len = m + n;
        int left = -1, right = -1;
        int aStart = 0, bStart = 0;
        for (int i = 0; i <= len / 2; i++) {
            left = right;
            if (aStart < m && (bStart >= n || A[aStart] < B[bStart])) {
                right = A[aStart++];
            } else {
                right = B[bStart++];
            }
        }
        if ((len & 1) == 0) {
            return (left + right) / 2.0;
        } else {
            return right;
        }
    }


        public static void main(String[] args) {

    }
}
