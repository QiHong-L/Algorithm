
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: liuqihong
 * @Description:
 * @Date Created in  2022-07-10 11:33
 * @Modified By:
 *
 * 有效的括号
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 * 有效字符串需满足：
 * 1.	左括号必须用相同类型的右括号闭合。
 * 2.	左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 * 示例 1: 输入: "()" 输出: true
 * 示例 2: 输入: "()[]{}" 输出: true
 * 示例 3: 输入: "(]" 输出: false
 * 示例 4: 输入: "([)]" 输出: false
 * 示例 5: 输入: "{[]}" 输出: true
 */
public class Main {
    public static void main(String[] args) {
        String str = "((";
        boolean test = test(str);
        System.out.println(test);
    }

    public static boolean test(String str) {
        if (str.length() % 2 != 0) {
            return false;
        }
        Map<Character,Character> map = new HashMap<>();
        map.put(')','(');
        map.put(']','[');
        map.put('}','{');
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (map.containsKey(str.charAt(i))) {
                if (stack.isEmpty() || stack.peek() != map.get(str.charAt(i))) {
                    return false;
                }
                stack.pop();
            } else {
                stack.push(str.charAt(i));
            }
        }
        return stack.isEmpty();
    }
}
