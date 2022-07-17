package dynamic;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-07-02 11:17
 * @Modified By:
 */
public class Dynamic {
    /**
     * 871. 最低加油次数
     *
     * @param target
     * @param startFuel
     * @param stations
     * @return
     */
    public int minRefuelStops(int target, int startFuel, int[][] stations) {
        int n = stations.length;
        long[] dp = new long[n + 1];
        dp[0] = startFuel;
        for (int i = 0; i < n; i++) {
            for (int j = i; j >= 0; j--) {
                if (dp[j] >= stations[i][0]) {
                    dp[j + 1] = Math.max(dp[j + 1], dp[j] + stations[i][1]);
                }
            }
        }
        for (int i = 0; i <= n; i++) {
            if (dp[i] >= target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 1200. 最小绝对差
     *
     * @param arr
     * @return
     */
    public List<List<Integer>> minimumAbsDifference(int[] arr) {
        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(arr);
        int i = 0, j = 1;
        int num = Integer.MAX_VALUE;
        while (j < arr.length) {
            int abs = Math.abs(arr[j] - arr[i]);
            if (abs <= num) {
                if (abs < num) result.clear();
                List<Integer> list = new ArrayList<>();
                list.add(arr[i]);
                list.add(arr[j]);
                result.add(list);
                num = abs;
            }
            i++;
            j++;
        }
        return result;
    }

    /**
     * 736. Lisp 语法解析
     *
     * @param expression
     * @return
     */
    Map<String, Deque<Integer>> scope = new HashMap<>();
    int start = 0;

    public int evaluate(String expression) {
        return innerEvaluate(expression);
    }

    public int innerEvaluate(String expression) {
        if (expression.charAt(start) != '(') {
            if (Character.isLowerCase(expression.charAt(start))) {
                String var = parseVar(expression);
                return scope.get(var).peek();
            } else {
                return parseInt(expression);
            }
        }
        int ret;
        start++; // 移除左括号
        if (expression.charAt(start) == 'l') { //let 表达式
            start += 4;
            List<String> vars = new ArrayList<>();
            while (true) {
                if (!Character.isLowerCase(expression.charAt(start))) {
                    ret = innerEvaluate(expression);
                    break;
                }
                String var = parseVar(expression);
                if (expression.charAt(start) == ')') {
                    ret = scope.get(var).peek();
                    break;
                }
                vars.add(var);
                start++;
                int e = innerEvaluate(expression);
                scope.putIfAbsent(var, new ArrayDeque<Integer>());
                scope.get(var).push(e);
                start++;
            }
            for (String var : vars) {
                scope.get(var).pop();
            }
        } else if (expression.charAt(start) == 'a') {
            start += 4;
            int e1 = innerEvaluate(expression);
            start++;
            int e2 = innerEvaluate(expression);
            ret = e1 + e2;
        } else {
            start += 5;
            int e1 = innerEvaluate(expression);
            start++;
            int e2 = innerEvaluate(expression);
            ret = e1 * e2;
        }
        start++;
        return ret;
    }

    // 解析整数
    public int parseInt(String expression) {
        int n = expression.length();
        int ret = 0, sign = 1;
        if (expression.charAt(start) == '-') {
            sign = -1;
            start++;
        }
        while (start < n && Character.isDigit(expression.charAt(start))) {
            ret = ret * 10 + (expression.charAt(start) - '0');
            start++;
        }
        return sign * ret;
    }

    public String parseVar(String expression) { // 解析变量
        int n = expression.length();
        StringBuffer ret = new StringBuffer();
        while (start < n && expression.charAt(start) != ' ' && expression.charAt(start) != ')') {
            ret.append(expression.charAt(start));
            start++;
        }
        return ret.toString();
    }

    /**
     * 648. 单词替换
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public String replaceWords(List<String> dictionary, String sentence) {
        String[] s = sentence.split(" ");
        for (int i = 0; i < s.length; i++) {
            s[i] = isRoot(dictionary, s[i]);
        }
        String collect = Arrays.stream(s).collect(Collectors.joining(" "));
        return collect;
    }

    public String isRoot(List<String> dictionary, String str) {
        int size = Integer.MAX_VALUE;
        String result = str;
        for (String root : dictionary) {
            if (str.startsWith(root)) {
                if (size > root.length()) {
                    size = root.length();
                    result = root;
                }
            }
        }
        return result;
    }

    /**
     * 1217.玩筹码
     *
     * @param position
     * @return
     */
    public int minCostToMoveChips(int[] position) {
        int baseCount = 0, evenCount = 0;
        for (int i = 0; i < position.length; i++) {
            if (position[i] % 2 == 0) evenCount++;
            else baseCount++;
        }
        return baseCount > evenCount ? evenCount : baseCount;
    }

    /**
     * 873. 最长的斐波那契子序列的长度
     *
     * @param arr
     * @return
     */
    public int lenLongestFibSubseq(int[] arr) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            map.put(arr[i], i);
        }
        int[][] dp = new int[n][n];
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i - 1; j > 0 && arr[j] * 2 > arr[i]; j--) {
                int k = map.getOrDefault(arr[i] - arr[j], -1);
                if (k >= 0) {
                    dp[j][i] = Math.max(dp[k][j] + 1, 3);
                }
                ans = Math.max(ans, dp[j][i]);
            }
        }
        return ans;
    }

    /**
     * 741. 摘樱桃
     *
     * @param grid
     * @return
     */
    public int cherryPickup(int[][] grid) {
        int n = grid.length;
        int[][][] f = new int[n * 2 - 1][n][n];
        for (int i = 0; i < n * 2 - 1; ++i) {
            for (int j = 0; j < n; ++j) {
                Arrays.fill(f[i][j], Integer.MIN_VALUE);
            }
        }
        f[0][0][0] = grid[0][0];
        for (int k = 1; k < n * 2 - 1; ++k) {
            for (int x1 = Math.max(k - n + 1, 0); x1 <= Math.min(k, n - 1); ++x1) {
                int y1 = k - x1;
                if (grid[x1][y1] == -1) {
                    continue;
                }
                for (int x2 = x1; x2 <= Math.min(k, n - 1); ++x2) {
                    int y2 = k - x2;
                    if (grid[x2][y2] == -1) {
                        continue;
                    }
                    int res = f[k - 1][x1][x2]; // 都往右
                    if (x1 > 0) {
                        res = Math.max(res, f[k - 1][x1 - 1][x2]); // 往下，往右
                    }
                    if (x2 > 0) {
                        res = Math.max(res, f[k - 1][x1][x2 - 1]); // 往右，往下
                    }
                    if (x1 > 0 && x2 > 0) {
                        res = Math.max(res, f[k - 1][x1 - 1][x2 - 1]); // 都往下
                    }
                    res += grid[x1][y1];
                    if (x2 != x1) { // 避免重复摘同一个樱桃
                        res += grid[x2][y2];
                    }
                    f[k][x1][x2] = res;
                }
            }
        }
        return Math.max(f[n * 2 - 2][n - 1][n - 1], 0);
    }

    /**
     * 1252. 奇数值单元格的数目
     * @param m
     * @param n
     * @param indices
     * @return
     */
    public int oddCells(int m, int n, int[][] indices) {
        int[] rows = new int[m];
        int[] cols = new int[n];
        for (int[] index : indices) {
            rows[index[0]]++;
            cols[index[1]]++;
        }
        int oddx = 0, oddy = 0;
        for (int i = 0; i < m; i++) {
            if ((rows[i] & 1) != 0) {
                oddx++;
            }
        }
        for (int i = 0; i < n; i++) {
            if ((rows[i] & 1) != 0) {
                oddy++;
            }
        }
        return oddx * (n - oddy) + (m - oddx) * oddy;
    }

    /**
     * 行星碰撞
     * @param asteroids
     * @return
     */
    public static int[] asteroidCollision(int[] asteroids) {
//        Stack<Integer> stack = new Stack<>();
//        for (int i = 0; i < asteroids.length; i++) {
//            if (!stack.isEmpty() && (stack.peek() > 0 && asteroids[i] < 0)) {
//                int num = asteroids[i];
//                while (!stack.isEmpty()) {
//                    Integer pop = stack.pop();
//                    if (pop + num > 0) {
//                        stack.push(pop);
//                        break;
//                    }
//                    else if (pop + num == 0) {
//                        break;
//                    } else {
//                        if (stack.isEmpty() || stack.peek() < 0){
//                            stack.push(num);
//                            break;
//                        }
//                    }
//                }
//            } else {
//                stack.push(asteroids[i]);
//            }
//        }
//        int size = stack.size();
//        int[] result = new int[size];
//        for (int i = size - 1; i >= 0; i--) {
//            result[i] = stack.pop();
//        }
//        return result;

        // 官方题解版
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int aster : asteroids) {
            boolean alive = true;
            while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0) {
                alive = stack.peek() < -aster; // aster 是否存在
                if (stack.peek() <= -aster) {  // 栈顶行星爆炸
                    stack.pop();
                }
            }
            if (alive) {
                stack.push(aster);
            }
        }
        int size = stack.size();
        int[] ans = new int[size];
        for (int i = size - 1; i >= 0; i--) {
            ans[i] = stack.pop();
        }
        return ans;
    }

    class Node {
        public boolean val;
        public boolean isLeaf;
        public Node topLeft;
        public Node topRight;
        public Node bottomLeft;
        public Node bottomRight;

        public Node() {}

        public Node(boolean _val,boolean _isLeaf,Node _topLeft,Node _topRight,Node _bottomLeft,Node _bottomRight) {
            val = _val;
            isLeaf = _isLeaf;
            topLeft = _topLeft;
            topRight = _topRight;
            bottomLeft = _bottomLeft;
            bottomRight = _bottomRight;
        }
    }

    /**
     * 558. 四叉树交集
     * @param quadTree1
     * @param quadTree2
     * @return
     */
    public Node intersect(Node quadTree1, Node quadTree2) {
        if (quadTree1.isLeaf) {
            if (quadTree1.val) {
                return new Node(true,true,null,null,null,null);
            }
            return quadTree2;
        }
        if (quadTree2.isLeaf) {
            if (quadTree2.val) {
                return new Node(true,true,null,null,null,null);
            }
            return quadTree1;
        }
        Node topLeft = intersect(quadTree1.topLeft,quadTree2.topLeft);
        Node topRight = intersect(quadTree1.topRight,quadTree2.topRight);
        Node bottomLeft = intersect(quadTree1.bottomLeft,quadTree2.bottomLeft);
        Node bottomRight = intersect(quadTree1.bottomRight,quadTree2.bottomRight);
        //四个子节点都是叶子结点并且值相同
        if (topLeft.isLeaf && topRight.isLeaf && bottomLeft.isLeaf && bottomRight.isLeaf
                && topLeft.val == topRight.val && topLeft.val == bottomLeft.val && topLeft.val == bottomRight.val) {
            return new Node(topLeft.val, true, null, null, null, null);
        }
        return new Node(false, false, topLeft, topRight, bottomLeft, bottomRight);
    }

    /**
     * 11. 盛最多水的容器
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        // 方法一 暴力求解 （超出时间预算）
//        int sum = Integer.MIN_VALUE;
//        for (int i = 0; i < height.length - 1; i++) {
//            for (int j = i + 1; j < height.length; j++) {
//                if (height[i] < height[j]) {
//                    sum = Math.max(height[i] * (j-i),sum);
//                } else {
//                    sum = Math.max(height[j] *(j-i),sum);
//                }
//            }
//        }
//        return sum;
        // 方法二 双指针
//        int maxarea = 0,left = 0,right = height.length-1;
//        while (left < right) {
//            maxarea = Math.max(Math.min(height[left],height[right]) * (right - left),maxarea);
//            if (height[left] < height[right]) {
//                left++;
//            } else {
//                right--;
//            }
//        }
//        return maxarea;
        // 方法三 利用三元表达式
        int i = 0, j = height.length - 1, res = 0;
        while(i < j) {
            res = height[i] < height[j] ?
                    Math.max(res, (j - i) * height[i++]):
                    Math.max(res, (j - i) * height[j--]);
        }
        return res;
    }

    /**
     * 20. 有效的括号
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        if (s.length() % 2 != 0) return false;
        Map<Character,Character> map = new HashMap<>();
        map.put(')','(');
        map.put('}','{');
        map.put('[',']');
        Deque<Character> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                if (queue.isEmpty() || queue.peek() != map.get(s.charAt(i))) {
                    return false;
                }
                queue.pop();
            } else {
                queue.push(s.charAt(i));
            }
        }
        return queue.isEmpty();
    }

    /**
     * 565. 数组嵌套
     * @param nums
     * @return
     */
    public int arrayNesting(int[] nums) {
        // 方法一 暴力破解法，超出时间限制
//        int result = Integer.MIN_VALUE;
//        for (int i = 0; i < nums.length; i++) {
//            result = Math.max(result,arrayDfs(new HashSet<>(),nums,i));
//        }
//        return result;
        // 方法二 图，找到节点个数最大的环 时间复杂度 O(N) 空间复杂度 O(N)
//        int res = 0,n = nums.length;
//        boolean[] flag = new boolean[n];
//        for (int i = 0; i < n; i++) {
//            int m = 0;
//            while (!flag[i]) {
//                flag[i] = true;
//                i = nums[i];
//                m++;
//            }
//            res = Math.max(res,m);
//        }
//        return res;
        // 方法三 优化 原地标记
        int res = 0,n = nums.length;
        for (int i = 0; i < n; i++) {
            int m = 0;
            while (nums[i] < n) {
                int num = nums[i];
                nums[i] = n;
                i = num;
                m++;
            }
            res = Math.max(res,m);
        }
        return res;

    }

    public int arrayDfs(HashSet<Integer> set,int[] nums,int i) {
        if (set.contains(nums[i])) {
            return set.size();
        }
        set.add(nums[i]);
        return arrayDfs(set,nums,nums[i]);
    }

    public static void main(String[] args) {
        asteroidCollision(new int[]{-2,-2,1,-2});
    }
}
