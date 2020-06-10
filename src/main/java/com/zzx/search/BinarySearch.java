package com.zzx.search;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-10 20:35
 */
public class BinarySearch {
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1000, 1234};
        System.out.println(binarySearch(arr, 0, arr.length, 1000));

    }

    /**
     * 二分查找，二分查找的前提是所给数组是有序的
     *
     * @param arr   在此数组中查找
     * @param left  左边的索引
     * @param right 右边的索引
     * @param value 需要查找的值
     * @return 找到了则返回相应的下标，未找到则返回-1
     */
    public static int binarySearch(int[] arr, int left, int right, int value) {
        //由于是有序的数组，当要查找的值大于数组的最大值或者小于数组的最小值，则直接返回-1
        //由于left和right一直在移动，所以当left>right时，则说明数组中找不到该数
        if (left > right || arr[0] > value || arr[arr.length - 1] < value) {
            return -1;
        }
        //计算得到中间索引
        int mid = (left + right) / 2;
        //由于数组是有序的，若中间值小于要找的数，则说明该数在中间值的右边
        if (arr[mid] < value) {
            //所以此时需要向右递归
            return binarySearch(arr, mid + 1, right, value);
        } else if (arr[mid] > value) {
            //如果中间值大于要找的数，则说明该数在左边，则向左递归
            return binarySearch(arr, left, mid - 1, value);
        } else {
            return mid;
        }
    }
}
