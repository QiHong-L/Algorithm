package dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: liuqihong
 * @Description: 731. 我的日程安排表 II
 * @Date Created in  2022-07-19 9:44
 * @Modified By:
 */
public class MyCalendarTwo {

    List<int[]> list1;
    List<int[]> list2;
    public MyCalendarTwo() {
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
    }

    public boolean book(int start, int end) {
        for (int[] arr : list2) {
            int l = arr[0],r = arr[1];
            if (l < end && r > start) {
                return false;
            }
        }
        for (int[] arr : list1) {
            int l = arr[0],r = arr[1];
            if (l < end && r > start) {
                list2.add(new int[]{Math.max(l,start),Math.min(r,end)});
            }
        }
        list1.add(new int[]{start,end});
        return true;
    }

    public static void main(String[] args) {
        MyCalendarTwo myCalendarTwo = new MyCalendarTwo();
        myCalendarTwo.book(10,20);
        myCalendarTwo.book(50,60);
        myCalendarTwo.book(10,40);
        myCalendarTwo.book(5,15);
        myCalendarTwo.book(5,10);
        myCalendarTwo.book(25,55);

    }
}
