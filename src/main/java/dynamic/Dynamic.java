package dynamic;

import sun.awt.image.ImageWatched;

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

    static int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    /**
     * 749. 隔离病毒
     * @param isInfected
     * @return
     */
    public int containVirus(int[][] isInfected) {
        int m = isInfected.length, n = isInfected[0].length;
        int ans = 0;
        while (true) {
            List<Set<Integer>> neighbors = new ArrayList<Set<Integer>>();
            List<Integer> firewalls = new ArrayList<Integer>();
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] == 1) {
                        Queue<int[]> queue = new ArrayDeque<int[]>();
                        queue.offer(new int[]{i, j});
                        Set<Integer> neighbor = new HashSet<Integer>();
                        int firewall = 0, idx = neighbors.size() + 1;
                        isInfected[i][j] = -idx;

                        while (!queue.isEmpty()) {
                            int[] arr = queue.poll();
                            int x = arr[0], y = arr[1];
                            for (int d = 0; d < 4; ++d) { // 找到病毒位置，广度收缩查找周边病毒 通过 上，下，左，右扩散
                                int nx = x + dirs[d][0], ny = y + dirs[d][1];
                                if (nx >= 0 && nx < m && ny >= 0 && ny < n) {
                                    if (isInfected[nx][ny] == 1) { // 找到病毒
                                        queue.offer(new int[]{nx, ny}); // 添加到 queue 队列
                                        isInfected[nx][ny] = -idx; // 访问过的病毒 设置为 -idx；
                                    } else if (isInfected[nx][ny] == 0) {  // 当前区域不是病毒
                                        ++firewall; // 添加防火墙
                                        neighbor.add(getHash(nx, ny));
                                    }
                                }
                            }
                        }
                        neighbors.add(neighbor);
                        firewalls.add(firewall); // 防火墙数量添加到 list
                    }
                }
            }

            if (neighbors.isEmpty()) {
                break;
            }

            int idx = 0;
            for (int i = 1; i < neighbors.size(); ++i) {
                if (neighbors.get(i).size() > neighbors.get(idx).size()) {
                    idx = i;
                }
            }
            ans += firewalls.get(idx);
            for (int i = 0; i < m; ++i) {
                for (int j = 0; j < n; ++j) {
                    if (isInfected[i][j] < 0) {
                        if (isInfected[i][j] != -idx - 1) {
                            isInfected[i][j] = 1;
                        } else {
                            isInfected[i][j] = 2;
                        }
                    }
                }
            }
            for (int i = 0; i < neighbors.size(); ++i) {
                if (i != idx) {
                    for (int val : neighbors.get(i)) {
                        int x = val >> 16, y = val & ((1 << 16) - 1);
                        isInfected[x][y] = 1;
                    }
                }
            }
            if (neighbors.size() == 1) {
                break;
            }
        }
        return ans;
    }

    public int getHash(int x, int y) {
        return (x << 16) ^ y;
    }

    /**
     * 1260. 二维网格迁移
     * @param grid
     * @param k
     * @return
     */
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            List<Integer> list = new ArrayList<>();
            for (int j = 0; j < m; j++) {
                list.add(0);
            }
            result.add(list);
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int index = (i * m + j + k) % (m * n);
                result.get(index / m).set(index % m,grid[i][j]);
            }
        }
        return result;
    }

    /**
     * c(动态规划解法)
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        int len = s.length();
        if (len < 2) return s;
        int maxLen = 1; // 最大长度
        int begin = 0; // 最大回文串开始下标，可以利用 begin + maxLen 计算出右边下标
        boolean[][] dp = new boolean[len][len]; // 默认都是 false
        // 对角线都为 true 因为单个字符就是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 左边界遍历
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i -1;
                if (j >= len) {
                    break;
                }

                if (s.charAt(i) != s.charAt(j)) {
                    dp[i][j] = false; // 当前 左边i 和 右边j 对应的字符不相等，就将当前位置置为 false
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true; // 如果相等且长度<= 3 那便不用判断里面的子串是否是回文，如 a b a，不用判断 b 单个字符肯定是回文串
                    } else {
                        // 长度超过了3 便需要参考内部子串是否是回文了
                        dp[i][j] = dp[i+1][j-1];
                    }
                }

                if (dp[i][j] && j - i + 1 > maxLen) {
                    begin = i;
                    maxLen = j - i + 1;
                }
            }
        }
        return s.substring(begin,begin+maxLen);

    }

    /**
     * 5.最长回文子串(方法二：中心扩展算法)
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        int start = 0, end = 0;
        for (int i = 0; i < s.length(); i++) {
            // 基数中心扩散
            int len1 = expandAroundCenter(s,i,i);
            // 偶数中心扩散
            int len2 = expandAroundCenter(s,i,i+1);
            int len = Math.max(len1,len2);
            if (len > end - start) {
                // 通过中心值 i 和 len 长度，求start，end，假设 i = 3,len = 5;
                // start = 3 - (5 - 1) /2 = 1;
                // end = 3 + 5 / 2 = 5;
                start = i - (len -1) / 2;
                end = i + len / 2;
            }
        }
        return s.substring(start,end+1);
    }

    /**
     * 中心扩散求回文的长度
     * @param s
     * @param left
     * @param right
     * @return
     */
    public int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        return right - left -1;
    }


    class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode() {}
      TreeNode(int val) { this.val = val; }
      TreeNode(int val, TreeNode left, TreeNode right) {
          this.val = val;
          this.left = left;
          this.right = right;
      }
    }

    /**
     * 814. 二叉树剪枝 （递归思想）
     * @param root
     * @return
     * 思路：先从底部 往上删除，如果父节点是 0 左右节点也是 0 ，我们会先删除 左右节点，然后删除父节点
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) return root; // 如果遍历到最后了直接返回
        root.left = pruneTree(root.left); // 查找左子树
        root.right = pruneTree(root.right); // 查找柚子树
        if (root.val == 0 && root.left == null && root.right == null) {
            // 如果当前节点为 0 ， 并且 左子树 和 右子树 都为null，那就删除当前节点
            return null;
        }
        return root;
    }

    /**
     * 设置交集大小至少为2
     * @param intervals
     * @return
     */
    public int intersectionSizeTwo(int[][] intervals) {
        int n = intervals.length;
        int res = 0;
        int m = 2;
        Arrays.sort(intervals,(a,b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        List<Integer>[] temp = new List[n];
        for (int i = 0; i < n; i++) {
            temp[i] = new ArrayList<>();
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = intervals[i][0],k = temp[i].size(); k < m; j++,k++) {
                res++;
                help(intervals,temp,i-1,j);
            }
        }
        return res;

    }

    public void help(int[][] intervals, List<Integer>[] temp, int pos, int num) {
        for (int i = pos; i >= 0; i--) {
            if (intervals[i][1] < num) {
                break;
            }
            temp[i].add(num);
        }
    }

    /**
     * 剑指 Offer II 115. 重建序列
     * @param nums
     * @param sequences
     * @return
     */
    public boolean sequenceReconstruction(int[] nums, int[][] sequences) {
        int n = nums.length;
        int[] indegrees = new int[n + 1];
        Set<Integer>[] graph = new Set[n + 1];
        for (int i = 1; i <= n; i++) {
            graph[i] = new HashSet<Integer>();
        }
        for (int[] sequence : sequences) {
            int size = sequence.length;
            for (int i = 1; i < size; i++) {
                int prev = sequence[i - 1], next = sequence[i];
                if (graph[prev].add(next)) {
                    indegrees[next]++;
                }
            }
        }
        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 1; i <= n; i++) {
            if (indegrees[i] == 0) {
                queue.offer(i);
            }
        }
        while (!queue.isEmpty()) {
            if (queue.size() > 1) {
                return false;
            }
            int num = queue.poll();
            Set<Integer> set = graph[num];
            for (int next : set) {
                indegrees[next]--;
                if (indegrees[next] == 0) {
                    queue.offer(next);
                }
            }
        }
        return true;
    }

    /**
     * 1184. 公交站间的距离
     * @param distance
     * @param start
     * @param destination
     * @return
     */
    public int distanceBetweenBusStops(int[] distance, int start, int destination) {
        if (start > destination) { // 大小比较交换，保证 start < desitination
            int temp = start;
            start = destination;
            destination = temp;
        }
        int n = distance.length;
        int sum1=0,sum2=0;
        for (int i = 0; i < n; i++) {
            if (i >= start && i < destination) { // start <= i < destination 表示是正序的路径
                sum1 += distance[i];
            }else { // 逆序路径
                sum2 += distance[i];
            }
        }
        return Math.min(sum1,sum2);
    }


    /**
     * 45. 跳跃游戏 II
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        // 解法一 反向查找出发位置，次解法时间复杂度比较高，为 O(n^2)
//        int n = nums.length - 1; // 拿到数组长度
//        int steps = 0; // 定义计算步数变量
//        while (n > 0) {
//            for (int i = 0; i < n; i++) {
//                // 从前往后找能够到达 n 的下标，贪心算法选择最左边能到达的，所以从左向右遍历，找到退出即可
//                if (i + nums[i] >= n) {
//                    n = i;
//                    steps++;
//                    break;
//                }
//            }
//        }
//        return steps;
        /**
         * 解法二，正向查找可达到的最大位置，时间复杂度优化 O(n)
         * 此解法使用贪心算法，从左往右遍历，找到能跳到最远的位置即可
         */
        int length = nums.length;
        int end = 0;
        int steps = 0;
        int maxPosition = 0;
        for (int i = 0; i < length - 1; i++) {
            maxPosition = Math.max(maxPosition,i+nums[i]);
            if (i == end) {
                end = maxPosition;
                steps++;
            }
        }
        return steps;
    }


    /**
     * 55. 跳跃游戏
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        int reach = 0,n = nums.length;
        for (int i = 0; i < n; i++) {
            if (i > reach) {
                return false;
            }
            reach = Math.max(i + nums[i],reach);
        }
        return true;
    }

    /**
     * 1190. 反转每对括号间的子串
     * @param s
     * @return
     *
     * (u(love)i)
     */
    public String reverseParentheses(String s) {
        Deque<String> stack = new LinkedList<>();
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                stack.push(str.toString());
                str.setLength(0);
            } else if (ch == ')') {
                str.reverse();
                str.insert(0,stack.pop());
            } else {
                str.append(ch);
            }
        }
        return str.toString();
    }


        public static void main(String[] args) {
        asteroidCollision(new int[]{-2,-2,1,-2});
    }
}
