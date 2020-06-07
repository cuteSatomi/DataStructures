package com.zzx.sort;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-07 20:46
 */
public class ShellSort {
    public static void main(String[] args) {
        /*int[] arr = {8, 9, 1, 7, 2, 3, 5, 4, 6, 0};
        shellSort2(arr);*/
        //测试一下希尔排序交换法的速度，给出80000个数据的数组，进行测试
        //创建一个存有80000个随机数的数组
        int[] arr = new int[80000];
        //将随机数存入这个数组
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000000);
        }
        //记录开始排序的时间
        long startTime = System.currentTimeMillis();
        //交换式的希尔排序
        //shellSort(arr);
        //插入式的希尔排序
        shellSort2(arr);
        //记录排序完成的时间
        long endTime = System.currentTimeMillis();
        //交换式希尔排序测试完成花费了5461毫秒
        //插入式希尔排序测试完成花费了18毫秒
        System.out.println("一共花费了" + (endTime - startTime) + "毫秒");
    }

    /**
     * 交换式希尔排序
     *
     * @param arr 需要排序的数组
     */
    public static void shellSort(int[] arr) {
        /*//定义一个辅助变量
        int temp = 0;
        //第一次排序，将10个数字分为5组
        for (int i = 5; i < arr.length; i++) {
            for (int j = i - 5; j >= 0; j -= 5) {
                if (arr[j] > arr[j + 5]) {
                    temp = arr[j];
                    arr[j] = arr[j + 5];
                    arr[j + 5] = temp;
                }
            }
        }
        System.out.println("第一次希尔排序后的结果～～～");
        System.out.println(Arrays.toString(arr));

        //第二次排序，将10个数字分为2组
        //这里内层的for循环其实就是冒泡，只是不连续的冒泡，执行步骤如下
        *//*
            1。i=2，j=0，arr[0]和arr[2]进行比较，j-2=-2<0，break;
            2。i=3，j=1，arr[1]和arr[3]进行比较，j-2=-1<0，break;
            3。i=4，j=2，arr[2]和arr[4]进行比较，j-2=0=0，arr[0]和arr[2]进行比较，j-2=-2<0，break;
            4。i=5，j=3，arr[3]和arr[5]进行比较，j-2=1>=0，arr[1]和arr[3]进行比较，j-2=-1<0，break;
            5。i=6，j=4，arr[4]和arr[6]进行比较，j-2=2>=0，arr[2]和arr[4]进行比较，j-2=0=0，arr[0]和arr[2]进行比较，j-2=-2<0，break;
            6。i=7，j=5，arr[5]和arr[7]进行比较，j-2=3>=0，arr[3]和arr[5]进行比较，j-2=1>=0，arr[1]和arr[3]进行比较，j-2=-1<0，break;
            7。i=8，j=6，arr[6]和arr[8]进行比较，j-2=4>=0，arr[4]和arr[6]进行比较，j-2=2>=0，arr[2]和arr[4]进行比较，j-2=0=0，arr[0]和arr[2]进行比较，j-2=-2<0，break;
            8。i=9，j=7，arr[7]和arr[9]进行比较，j-2=5>=0，arr[5]和arr[7]进行比较，j-2=3>=0，arr[3]和arr[5]进行比较，j-2=1>0，arr[1]和arr[3]进行比较，j-2=-1<0，break;
            我们假设索引为0,2,4,6,8的分为一组，索引为1,3,5,7,9的分为另一组
            可以得到第1，3，5，7这四步是第一组的一个完整的冒泡排序
            第2，4，6，8这四步是第二组的一个完整的冒泡排序
         *//*
        for (int i = 2; i < arr.length; i++) {
            for (int j = i - 2; j >= 0; j -= 2) {
                if (arr[j] > arr[j + 2]) {
                    temp = arr[j];
                    arr[j] = arr[j + 2];
                    arr[j + 2] = temp;
                }
            }
        }
        System.out.println("第二次希尔排序后的结果～～～");
        System.out.println(Arrays.toString(arr));

        //第三次排序，将10个数字分为1组，本质就是倒过来的冒泡排序
         for (int i = 1; i < arr.length; i++) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        System.out.println("第三次希尔排序后的结果～～～");
        System.out.println(Arrays.toString(arr));*/

        //交换法
        int temp = 0;
        int count = 0;
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                //遍历数组中的所有元素(共gap组，每组有arr.length/gap个元素)，步长gap
                for (int j = i - gap; j >= 0; j -= gap) {
                    //如果当前元素大于加上步长后的那个元素，则交换
                    if (arr[j] > arr[j + gap]) {
                        temp = arr[j];
                        arr[j] = arr[j + gap];
                        arr[j + gap] = temp;
                    }
                }
            }
            //System.out.println("第" + (++count) + "次排序后的结果～～～");
            //System.out.println(Arrays.toString(arr));
        }
    }

    /**
     * 插入式希尔排序
     *
     * @param arr 需要排序的数组
     */
    public static void shellSort2(int[] arr) {
        for (int gap = arr.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < arr.length; i++) {
                int j = i;
                int temp = arr[j];
                //此处的j-gap>=0是为了后面的arr[j-gap]不会产生越界
                while (j - gap >= 0 && temp < arr[j - gap]) {
                    arr[j] = arr[j - gap];
                    j -= gap;
                }
                arr[j] = temp;
            }
        }
    }
}
