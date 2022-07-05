package dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description: 729.我的日程安排表
 * @Date Created in  2022-07-05 9:12
 * @Modified By:
 */
public class MyCalendar {

    List<Integer> startList;
    List<Integer> endList;

    public MyCalendar() {
        startList = new ArrayList<>();
        endList = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int i = 0; i < startList.size(); i++) {
            if (start < endList.get(i) && startList.get(i) < end) {
                return false;
            }
        }
        startList.add(start);
        endList.add(end);
        return true;
    }
}
