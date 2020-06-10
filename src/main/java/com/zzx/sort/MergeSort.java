package com.zzx.sort;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-10 19:51
 */
public class MergeSort {
    public static void main(String[] args) {
        int[] arr = {8, 4, 5, 7, 1, 3, 6, 2};
        int[] temp = new int[arr.length];
        mergeSort(arr, 0, arr.length - 1, temp);
        System.out.println(Arrays.toString(arr));
    }

    public static void mergeSort(int[] arr, int left, int right, int[] temp) {
        //只要left小于right，则让其一直递归拆分，即拆分到两个数为一组为止
        if (left < right) {
            int mid = (left + right) / 2;
            //向左递归分解
            mergeSort(arr, left, mid, temp);
            //向右递归分解
            mergeSort(arr, mid + 1, right, temp);
            //合并
            merge(arr, left, mid, right, temp);
        }

    }

    /**
     * 将两个有序数组合并
     *
     * @param arr   要排序的数组
     * @param left  左边的索引
     * @param mid   中间索引
     * @param right 右边的索引
     * @param temp  做临时存储的数组
     */
    public static void merge(int[] arr, int left, int mid, int right, int[] temp) {
        //左边有序序列的初始索引
        int leftStart = left;
        //右边有序序列的初始索引
        int rightStart = mid + 1;
        //定义一个指针指向临时存储数组的当前索引，从0开始
        int t = 0;

        //mid是左边有序序列的末尾索引，right是右边有序序列的末尾索引
        //此处的while循环指的是只要左右两边有任意一边的序列处理完毕则退出此循环
        while (leftStart <= mid && rightStart <= right) {
            //如果左边有序序列的当前元素小于等于右边序列的当前元素，则将左边序列的当前元素存入temp中，然后将leftStart和t后移一位
            if (arr[leftStart] <= arr[rightStart]) {
                temp[t] = arr[leftStart];
                t++;
                leftStart++;
            } else {
                //反之则说明右边序列的当前元素大于左边序列的当前元素，则将右边的元素存入temp数组，并且将rightStart和t后移一位
                temp[t] = arr[rightStart];
                t++;
                rightStart++;
            }
        }

        //退出了上面的while循环则说明左边或者右边有一边已经处理完毕，此时只需将另一边剩余的数据按序存入temp数组即可
        while (leftStart <= mid) {
            //如果leftStart小于等于mid，则说明左边的序列还未处理完毕，此时使用while循环将剩余数据按序插入到temp的末尾
            temp[t] = arr[leftStart];
            t++;
            leftStart++;
        }

        while (rightStart <= right) {
            //如果rightStart小于等于right，则说明右边的序列还未处理完毕，此时使用while循环将剩余数据按序插入到temp的末尾
            temp[t] = arr[rightStart];
            t++;
            rightStart++;
        }

        //将temp数组的数据拷贝到arr中
        t = 0;
        int tempLeft = left;
        while (tempLeft <= right) {
            arr[tempLeft] = temp[t];
            tempLeft++;
            t++;
        }
    }
}
