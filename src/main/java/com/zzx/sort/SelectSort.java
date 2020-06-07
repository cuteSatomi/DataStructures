package com.zzx.sort;

import java.util.Arrays;

/**
 * 选择排序，时间复杂度是O(n^2)
 *
 * @Author zzx
 * @Date 2020-06-07 16:52
 */
public class SelectSort {
    public static void main(String[] args) {
        //测试一下选择排序的速度，给出80000个数据的数组，进行测试
        //创建一个存有80000个随机数的数组
        int[] arr = new int[80000];
        //将随机数存入这个数组
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000000);
        }
        //记录开始排序的时间
        long startTime = System.currentTimeMillis();
        selectSort(arr);
        //记录排序完成的时间
        long endTime = System.currentTimeMillis();
        //测试完成花费了2601毫秒
        System.out.println("一共花费了" + (endTime - startTime) + "毫秒");

        /*int[] arr = {101, 34, 119, 1};

        System.out.println("原始数组～～～");
        System.out.println(Arrays.toString(arr));

        selectSort(arr);

        System.out.println("排序后～～～");
        System.out.println(Arrays.toString(arr));*/

        /*//第一轮排序
        //先假设第一个元素是数组中的最小值
        int min = arr[0];
        //第一个元素的索引
        int minIndex = 0;

        //因为已经假设了第一个元素是最小值，所以只需将第一个元素和第二个元素开始相比，所以此处的i从1开始
        for (int i = 0 + 1; i < arr.length; i++) {
            if (min > arr[i]) {
                //本轮比较中存在比我们一开始假定的最小值min还要小的数，则记录该数值以及其索引
                min = arr[i];
                minIndex = i;
            }
        }
        //for循环结束，即表示第一轮比较结束了，此时的min存放的就是真正的最小值
        //以上述数组{101, 34, 119, 1}为例，此时的min就是1，minIndex为3
        //即将arr[0]和arr[minIndex]互换
        arr[minIndex] = arr[0];
        arr[0] = min;

        System.out.println("第一轮排序后～～～");
        System.out.println(Arrays.toString(arr));

        //第二轮
        min = arr[1];
        minIndex = 1;

        for (int i = 1 + 1; i < arr.length; i++) {
            if (min > arr[i]) {
                //本轮比较中存在比我们一开始假定的最小值min还要小的数，则记录该数值以及其索引
                min = arr[i];
                minIndex = i;
            }
        }
        //此处发现第二轮排序一开始假定的min=arr[1]其实是正确的，即本轮排序中的最小值就是arr[1]
        //因此此处的交换就没有意义了，相当于arr[1] = arr[1];
        //因此此处可以加一个if条件进行判断优化
        if(minIndex!=1) {
            //只有minIndex不等于1的时候，即本轮循环中出现出现了比预设的最小值还要小的数据时，才对他们进行互换
            arr[minIndex] = arr[1];
            arr[1] = min;
        }

        System.out.println("第二轮排序后～～～");
        System.out.println(Arrays.toString(arr));

        //第三轮
        min = arr[2];
        minIndex = 2;

        for (int i = 2 + 1; i < arr.length; i++) {
            if (min > arr[i]) {
                //本轮比较中存在比我们一开始假定的最小值min还要小的数，则记录该数值以及其索引
                min = arr[i];
                minIndex = i;
            }
        }
        arr[minIndex] = arr[2];
        arr[2] = min;

        System.out.println("第三轮排序后～～～");
        System.out.println(Arrays.toString(arr));*/

    }

    /**
     * 选择排序
     *
     * @param arr 需要进行排序的数组
     */
    public static void selectSort(int[] arr) {
        //外层for循环表示需要进行arr.length-1次的排序
        for (int i = 0; i < arr.length - 1; i++) {
            //先假设arr[i]就是本次排序的最小值，则i就是本次排序最小值的索引
            int min = arr[i];
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (min > arr[j]) {
                    //如果某次排序中min大于arr数组的某一个元素
                    //此时说明min不是最小值，需要将此时的arr[j]设置为最小值
                    min = arr[j];
                    //保存该比min值小的数值所在的下标
                    minIndex = j;
                }
            }
            //进行完一次排序之后对min和minIndex进行判断
            if (minIndex != i) {
                //如果minIndex不等于i，则说明该次排序中存在比刚开始假设的min还要小的数值
                //将第i+1个元素与minIndex+1个元素进行互换
                arr[minIndex] = arr[i];
                //将arr[i]的值置为最小值min
                arr[i] = min;
            }
        }
    }
}
