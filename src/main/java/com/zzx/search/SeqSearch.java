package com.zzx.search;

/**
 * @Author zzx
 * @Date 2020-06-10 20:33
 */
public class SeqSearch {
    public static void main(String[] args) {
        // 没有顺序的数组
        int arr[] = {1, 9, 11, -1, 34, 89};
        int index = seqSearch(arr, 11);

        if (index == -1) {
            System.out.println("没有找到");
        } else {
            System.out.println("找到，下标为=" + index);
        }
    }

    /**
     * 线性查找
     *
     * @param arr   在此数组中查找
     * @param value 需要查找的值
     * @return 如果找到value则返回相应的下标，没找到就返回-1
     */
    public static int seqSearch(int[] arr, int value) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == value) {
                return i;
            }
        }
        return -1;
    }
}
