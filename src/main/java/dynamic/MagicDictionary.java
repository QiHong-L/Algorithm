package dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description: 676. 实现一个魔法字典
 * @Date Created in  2022-07-11 9:18
 * @Modified By:
 */
public class MagicDictionary {

    String[] dictionary;

    public MagicDictionary() {

    }

    public void buildDict(String[] dictionary) {
        this.dictionary = dictionary;
    }

    public boolean search(String searchWord) {
        for (String word : dictionary) {
            if (word.length() != searchWord.length()) continue;
            int diff = 0;
            for (int i = 0; i < word.length(); ++i) {
                if (word.charAt(i) != searchWord.charAt(i)) {
                    ++diff;
                    if (diff > 1) break;;
                }
            }
            if (diff == 1) return true;
        }
        return false;
    }

}
