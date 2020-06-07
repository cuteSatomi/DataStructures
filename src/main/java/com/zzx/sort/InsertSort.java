package com.zzx.sort;

import java.util.Arrays;

/**
 * 插入排序
 *
 * @Author zzx
 * @Date 2020-06-07 18:10
 */
public class InsertSort {
    public static void main(String[] args) {
        /*int[] arr = {101, 34, 119, 1, -1, 89};
        System.out.println("排序前～～～");
        System.out.println(Arrays.toString(arr));
        insertSort(arr);*/

        //测试一下插入排序的速度，给出80000个数据的数组，进行测试
        //创建一个存有80000个随机数的数组
        int[] arr = new int[80000];
        //将随机数存入这个数组
        for (int i = 0; i < 80000; i++) {
            arr[i] = (int) (Math.random() * 80000000);
        }
        //记录开始排序的时间
        long startTime = System.currentTimeMillis();
        insertSort(arr);
        //记录排序完成的时间
        long endTime = System.currentTimeMillis();
        //测试完成花费了543毫秒
        System.out.println("一共花费了" + (endTime - startTime) + "毫秒");
    }

    /**
     * 插入排序
     *
     * @param arr 要排序的数组
     */
    public static void insertSort(int[] arr) {
        /*//第一轮排序
        //定义要插入的数据，因为arr[0]是不动的，将arr[1]和arr[0]比较
        int insertVal = arr[1];
        int insertIndex = 1 - 1;

        //定义insertIndex>=0是为了不越界
        //insertVal < arr[insertIndex]说明将insertVal依次和它之前的数进行比较，若是insertVal比较小，则将比他大的那个数后移一位
        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            //如果要插入的数小于它的前一个数，则将其前一个数后移一个位置
            arr[insertIndex + 1] = arr[insertIndex];
            //再将insertIndex向前移动一个位置
            insertIndex--;
        }

        //退出了while循环则说明找到了插入的位置，为insertIndex+1
        arr[insertIndex + 1] = insertVal;

        System.out.println("第一次排序～～～");
        System.out.println(Arrays.toString(arr));

        //第二轮排序
        insertVal = arr[2];
        insertIndex = 2 - 1;

        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            //如果要插入的数小于它的前一个数，则将其前一个数后移一个位置
            arr[insertIndex + 1] = arr[insertIndex];
            //再将insertIndex向前移动一个位置
            insertIndex--;
        }

        arr[insertIndex + 1] = insertVal;

        System.out.println("第二次排序～～～");
        System.out.println(Arrays.toString(arr));

        //第三轮排序
        insertVal = arr[3];
        insertIndex = 3 - 1;

        while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
            //如果要插入的数小于它的前一个数，则将其前一个数后移一个位置
            arr[insertIndex + 1] = arr[insertIndex];
            //再将insertIndex向前移动一个位置
            insertIndex--;
        }

        arr[insertIndex + 1] = insertVal;

        System.out.println("第三次排序～～～");
        System.out.println(Arrays.toString(arr));*/

        //初始化insertVal和insertIndex，在for循环中只需给他们赋值即可
        int insertVal = 0;
        int insertIndex = 0;

        //因为第一个元素默认不动，所以比较从arr[1]开始
        for (int i = 1; i < arr.length; i++) {
            //定义要插入的数据，因为arr[0]是不动的，将arr[1]和arr[0]比较
            insertVal = arr[i];
            insertIndex = i - 1;

            //定义insertIndex>=0是为了不越界
            //insertVal < arr[insertIndex]说明将insertVal依次和它之前的数进行比较，若是insertVal比较小，则将比他大的那个数后移一位
            /*
            初始数组是{101, 34, 119, 1, -1, 89}
            以第三次排序为例：
                1。第二次排序的结果是[34, 101, 119, 1, -1, 89]
                2。第三次排序开始时insertVal=arr[3]=1，insertIndex=i-1=2，arr[insertIndex]=arr[2]=119，满足条件2>=0 && 1<119，此时进入while循环
                3。进入循环将arr[2]后移一位，即arr[3]=arr[2]=119，此时的数组为[34, 101, 119, 119, -1, 89]，insertIndex=insertIndex-1=1
                4。此时insertIndex=1,arr[insertIndex]=arr[1]=101，满足循环条件1>=0 && 1<101，此时再次进入循环
                5。将arr[1]后移一位，即arr[2]=arr[1]=101,此时的数组为[34, 101, 101, 119, -1, 89]，insertIndex=insertIndex-1=0
                6。此时insertIndex=0,arr[insertIndex]=arr[0]=34，满足循环条件0>=0 && 1<34，此时再次进入循环
                7。将arr[0]后移一位，即arr[1]=arr[0]=34,此时的数组为[34, 34, 101, 119, -1, 89]，insertIndex=insertIndex-1=-1
                8。再次进行判断，-1>=0不满足，因此退出while循环
                9。继续执行下面的代码，此时的insertIndex=-1，真正要插入insertVal的位置是insertIndex+1，即数组的第一个位置arr[-1+1]=insertVal = 1
                10。本轮插入排序结束，数组为[1, 34, 101, 119, -1, 89]
             */
            while (insertIndex >= 0 && insertVal < arr[insertIndex]) {
                //如果要插入的数小于它的前一个数，则将其前一个数后移一个位置
                arr[insertIndex + 1] = arr[insertIndex];
                //再将insertIndex向前移动一个位置
                insertIndex--;
            }

            //退出了while循环则说明找到了插入的位置，为insertIndex+1
            arr[insertIndex + 1] = insertVal;

            //System.out.println("第" + i + "次排序～～～");
            //System.out.println(Arrays.toString(arr));
        }
    }
}
