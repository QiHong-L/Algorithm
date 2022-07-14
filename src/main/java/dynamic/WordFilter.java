package dynamic;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: liuqihong
 * @Description: 745. 前缀和后缀搜索
 * @Date Created in  2022-07-14 9:04
 * @Modified By:
 */
public class WordFilter {

    Map<String,Integer> dictionary;

    public WordFilter(String[] words) {
        dictionary = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            int m = word.length();
            for (int prefixLength = 1; prefixLength <= m; prefixLength++) {
                for (int suffixLenght = 1; suffixLenght <= m; suffixLenght++) {
                    dictionary.put(word.substring(0,prefixLength)+"#"+word.substring(m-suffixLenght),i);
                }
            }
        }
    }

    public int f(String pref, String suff) {
        return dictionary.getOrDefault(pref+"#"+suff,-1);
    }
}
