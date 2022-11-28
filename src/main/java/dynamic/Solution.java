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

    /**
     * 792. 匹配子序列的单词数
     *
     * @param s 给定字符串
     * @param words 需要匹配的子串
     * @return 返回子序列的个数
     */
    public int numMatchingSubseq(String s, String[] words) {
        // 定义一个 List 26位数组，用于保存 s 中每个字符的 i 下标
        List<Integer>[] pos = new List[26];
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<>();
        }
        for (int i = 0; i < s.length(); i++) {
            pos[s.charAt(i) - 'a'].add(i);
        }
        // 定义 res 保存 words 的长度，当不匹配就 res--; 这样 res 就是结果 直接返回
        int res = words.length;
        for (int i = 0; i < words.length; i++) {
            // 如果 words 里面的子串大于了 s 的长度，那么必定不满足，所以 --res,然后进入下一个子序列判断
            if (words[i].length() > s.length()) {
                --res;
                continue;
            }
            int p = -1;
            for (int j = 0; j < words[i].length(); j++) {
                // pos[words[i].charAt(j) - 'a'].get(pos[words[i].charAt(j) - 'a'].size() - 1) <= p 里面最大的下标如果小于单前 p 那就说明不满足
                if (pos[words[i].charAt(j) - 'a'].isEmpty() || pos[words[i].charAt(j) - 'a'].get(pos[words[i].charAt(j) - 'a'].size() - 1) <= p) {
                    --res;
                    break;
                }
                p = binarySearch(pos[words[i].charAt(j) - 'a'],p);
            }

        }
        return res;
    }

    /**
     * 二分查找匹配的字符
     *
     * @param list
     * @param target
     * @return
     */
    public int binarySearch(List<Integer> list, int target) {
        int left = 0, right = list.size() - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (list.get(mid) > target) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return list.get(left);
    }


    /**
     * 795. 区间子数组个数
     *
     * @param nums
     * @param left
     * @param right
     * @return
     */
    public static int numSubarrayBoundedMax(int[] nums, int left, int right) {
        // 使用 last1 表示遍历的右边的，使用 last2 表示左边的下标
        int res = 0, last1 = -1, last2 = -1;
        // 一次遍历 nums
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
                last1 = i;
            } else if (nums[i] > right) {
                last2 = i;
                last1 = -1;
            }
            if (last1 != -1) {
                // 连续的长度应该是 n! ，如果连续的子数组长度为 3 那么子数据就有，1+2+3 = 6
                res += last1 - last2;
            }
        }
        return res;
    }

    /**
     * 809. 情感丰富的文字
     *
     * @param s 扩张单词
     * @param words 可扩张单词组
     * @return
     */
    public int expressiveWords(String s, String[] words) {
        int count = 0;
        for (String word : words) {
            if (expand(s,word)) {
                count++;
            }
        }
        return count;
    }

    /**
     * 判断 s 是否是 t 的扩展数字
     * @param s
     * @param t
     * @return
     */
    public boolean expand(String s, String t) {
        int i = 0, j = 0;
        // 双指针循环开始遍历
        while (i < s.length() && j < t.length()) {
            if (s.charAt(i) != t.charAt(j)) {
               return false;
            }
            char ch = s.charAt(i);
            int counti = 0;
            // 统计 s 中重复字符的数量
            while (i < s.length() && s.charAt(i) == ch) {
                i++;
                counti++;
            }
            int countj = 0;
            // 统计 t 中重复字符的数量
            while (j < t.length() && t.charAt(j) == ch) {
                j++;
                countj++;
            }
            // 当 s 中重复字符的数量还小于 t 中重复字符的数量，肯定不满足
            if (counti < countj) {
                return false;
            }
            // 当两边相同字符不相等，并且扩散字符重复的数字 < 3 那么肯定不满足
            if (counti != countj && counti < 3) {
                return false;
            }
        }

        return i == s.length() && j == t.length();
    }

    /**
     * 1752. 检查数组是否经排序和轮转得到
     *
     * @param nums
     * @return
     */
    public boolean check(int[] nums) {
        int[] temp = nums.clone();
        Arrays.sort(temp);
        int index = -1;
        for (int i = 0; i < temp.length; i++) {
            if (temp[i] == nums[0]) {
                index = i;
                break;
            }
        }
        for (int i = 0; i < temp.length; i++) {
            if (nums[i] != temp[(i + index) % temp.length]) {
                return false;
            }
        }
        return true;

    }

    /**
     * 813. 最大平均值和的分组
     *
     * @param nums
     * @param k
     * @return
     */
    public double largestSumOfAverages(int[] nums, int k) {
        int n = nums.length;
        double[] prefix = new double[n+1];
        // 循环遍历，使用 prefix 数组保存前缀和
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + nums[i];
        }
        double[][] dp = new double[n+1][k+1];
        for (int i = 1; i <= n; i++) {
            dp[i][1] = prefix[i] / i;
        }
        for (int j = 2; j <= k; j++) {
            for (int i = j; i <= n; i++) {
                for (int x = j -1; x < i; x++) {
                    dp[i][j] = Math.max(dp[i][j], dp[x][j-1] + (prefix[i] - prefix[x]) / (i - x));
                }
            }
        }
        return dp[n][k];
    }

        public static void main(String[] args) {
        int[] nums = {2,1,4,3};
        numSubarrayBoundedMax(nums,2,3);
    }
}
