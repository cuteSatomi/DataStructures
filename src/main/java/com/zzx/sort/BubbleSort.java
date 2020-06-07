package com.zzx.sort;

import java.util.Arrays;
import java.util.Random;

/**
 * 冒泡排序，时间复杂度O(n^2)
 *
 * @Author zzx
 * @Date 2020-06-07 15:03
 */
public class BubbleSort {
    public static void main(String[] args) {
        //原始未排序的数组
        //int[] arr = {3, 9, -1, 10, -2};
        //int[] arr = {1, 2, 4, 3, 5, 6};

        /*
        //第一趟排序，会将最大的数排在最后一位
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第一趟排序后的数组～～～");
        System.out.println(Arrays.toString(arr));

        //第二趟排序，会将第二大的数排在倒数第二位
        for (int i = 0; i < arr.length - 1 - 1; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第二趟排序后的数组～～～");
        System.out.println(Arrays.toString(arr));

        //第三趟排序，会将第二大的数排在倒数第三位
        for (int i = 0; i < arr.length - 1 - 2; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第三趟排序后的数组～～～");
        System.out.println(Arrays.toString(arr));

        //第四趟排序，会将第二大的数排在倒数第四位，有五个数字，其实第四次排序就是最后一次排序了
        for (int i = 0; i < arr.length - 1 - 3; i++) {
            if (arr[i] > arr[i + 1]) {
                int temp = arr[i];
                arr[i] = arr[i + 1];
                arr[i + 1] = temp;
            }
        }
        System.out.println("第四趟排序后的数组～～～");
        System.out.println(Arrays.toString(arr));
    */
        /*for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            System.out.println("第" + (i + 1) + "趟排序后的数组～～～");
            System.out.println(Arrays.toString(arr));
        }*/

        /*System.out.println("排序前～～～");
        System.out.println(Arrays.toString(arr));
        bubbleSort(arr);
        System.out.println("排序后～～～");
        System.out.println(Arrays.toString(arr));*/

        //测试一下冒泡排序的速度，给出80000个数据的数组，进行测试
        //创建一个存有80000个随机数的数组
        int[] arr = new int[80000];
        //将随机数存入这个数组
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000000);
        }
        //记录开始排序的时间
        long startTime = System.currentTimeMillis();
        bubbleSort(arr);
        //记录排序完成的时间
        long endTime = System.currentTimeMillis();
        //测试完成花费了9486毫秒
        System.out.println("一共花费了" + (endTime - startTime) + "毫秒");
    }

    /**
     * 将冒泡排序封装成一个方法
     *
     * @param arr 要排序的数组
     */
    public static void bubbleSort(int[] arr) {
        //对冒泡排序进行优化
        //定义一个标识位，默认是false
        boolean flag = false;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    //加入进入到这个if语句中，则说明某一次判断有进行顺序的交换，此时将flag置为true
                    flag = true;
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
            //System.out.println("第" + (i + 1) + "趟排序后的数组～～～");
            //System.out.println(Arrays.toString(arr));

            //内层for循环结束时，对flag进行判断
            if (!flag) {
                //即flag为false，即在此次排序过程中没有任何元素进行位置交换，此时数组就已经是有序了
                break;
            } else {
                //重置flag，进行下一次判断
                flag = false;
            }
        }
    }
}
