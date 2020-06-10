package com.zzx.sort;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-10 19:53
 */
public class RadixSort {
    public static void main(String[] args) {
        int arr[] = {53, 3, 542, 748, 14, 214};
        radixSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 基数排序
     *
     * @param arr 需要排序的数组
     */
    public static void radixSort(int[] arr) {
        //第一轮排序
        //定义一个10列的二维数组，因为所有自然整数%10，最后的结果是0-9
        //如何决定二维数组的行？这里假设一个极端的例子，假如所给数组中所有的元素个位数字都相等
        //这就意味着第一轮排序后，初始数组中的所有元素都会被放入同一列中，所以这里给二维数组的行定义为arr.length
        int[][] bucket = new int[10][arr.length];
        //此时需要再定义一个一维数组，来记录每一列中实际存放的数据的个数，这里一维数组的长度即与二维数组的列数相当
        int[] bucketElementCounts = new int[10];

        //找出所给需要排序的数组中的最大值，计算得到它的位数，从而判断循环次数
        int max = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
        }
        int maxLength = (max + "").length();

        for (int i = 0, n = 1; i < maxLength; i++, n *= 10) {
            //首先遍历得到所给初始数组的所有元素
            for (int j = 0; j < arr.length; j++) {
                //此处是分别取得所有元素个位、十位、百位。。。上的值，将其存入二维数组中
                int digitOfElement = arr[j] / n % 10;
                //个位数为0则存入bucket[0][]中，个位数为1则存入bucket[1][]中，以此类推。。。
                //此处的bucketElementCounts[digitOfElement]第一次为0，因为初始值是0
                bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
                //再将刚刚存入二维数组的数在一维数组的相应位置记录下来
                bucketElementCounts[digitOfElement]++;
            }

            //上述将初始数组存入桶(二维数组)的操作结束后，再将其从桶中按序取出，存回到arr中
            int index = 0;
            //遍历存放桶中数据个数的一维数组
            for (int k = 0; k < bucketElementCounts.length; k++) {
                //如果bucketElementCounts[k]==0，则说明该列中没有数据，则无需处理
                if (bucketElementCounts[k] != 0) {
                    //如果该列存在数据，则取出放回到arr中
                    for (int l = 0; l < bucketElementCounts[k]; l++) {
                        arr[index++] = bucket[k][l];
                    }
                }
                //每列处理完毕后，需要将bucketElementCounts[k]重新置为0
                bucketElementCounts[k] = 0;
            }
        }

        /*//首先遍历得到所给初始数组的所有元素
        for (int j = 0; j < arr.length; j++) {
            //此处是取得所有元素个位数上的值，将其存入二维数组中
            int digitOfElement = arr[j] % 10;
            //个位数为0则存入bucket[0][]中，个位数为1则存入bucket[1][]中，以此类推。。。
            //此处的bucketElementCounts[digitOfElement]第一次为0，因为初始值是0
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            //再将刚刚存入二维数组的数在一维数组的相应位置记录下来
            bucketElementCounts[digitOfElement]++;
        }

        //上述将初始数组存入桶(二维数组)的操作结束后，再将其从桶中按序取出，存回到arr中
        int index = 0;
        //遍历存放桶中数据个数的一维数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果bucketElementCounts[k]==0，则说明该列中没有数据，则无需处理
            if (bucketElementCounts[k] != 0) {
                //如果该列存在数据，则取出放回到arr中
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    arr[index++] = bucket[k][l];
                }
            }
            //每列处理完毕后，需要将bucketElementCounts[k]重新置为0
            bucketElementCounts[k] = 0;
        }

        //第二轮排序
        //首先遍历得到所给初始数组的所有元素
        for (int j = 0; j < arr.length; j++) {
            //此处是取得所有元素个位数上的值，将其存入二维数组中
            int digitOfElement = arr[j]/10 % 10;
            //个位数为0则存入bucket[0][]中，个位数为1则存入bucket[1][]中，以此类推。。。
            //此处的bucketElementCounts[digitOfElement]第一次为0，因为初始值是0
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            //再将刚刚存入二维数组的数在一维数组的相应位置记录下来
            bucketElementCounts[digitOfElement]++;
        }

        //上述将初始数组存入桶(二维数组)的操作结束后，再将其从桶中按序取出，存回到arr中
        index = 0;
        //遍历存放桶中数据个数的一维数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果bucketElementCounts[k]==0，则说明该列中没有数据，则无需处理
            if (bucketElementCounts[k] != 0) {
                //如果该列存在数据，则取出放回到arr中
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    arr[index++] = bucket[k][l];
                }
            }
            //每列处理完毕后，需要将bucketElementCounts[k]重新置为0
            bucketElementCounts[k] = 0;
        }

        //第三轮排序
        //首先遍历得到所给初始数组的所有元素
        for (int j = 0; j < arr.length; j++) {
            //此处是取得所有元素个位数上的值，将其存入二维数组中
            int digitOfElement = arr[j]/10/10 % 10;
            //个位数为0则存入bucket[0][]中，个位数为1则存入bucket[1][]中，以此类推。。。
            //此处的bucketElementCounts[digitOfElement]第一次为0，因为初始值是0
            bucket[digitOfElement][bucketElementCounts[digitOfElement]] = arr[j];
            //再将刚刚存入二维数组的数在一维数组的相应位置记录下来
            bucketElementCounts[digitOfElement]++;
        }

        //上述将初始数组存入桶(二维数组)的操作结束后，再将其从桶中按序取出，存回到arr中
        index = 0;
        //遍历存放桶中数据个数的一维数组
        for (int k = 0; k < bucketElementCounts.length; k++) {
            //如果bucketElementCounts[k]==0，则说明该列中没有数据，则无需处理
            if (bucketElementCounts[k] != 0) {
                //如果该列存在数据，则取出放回到arr中
                for (int l = 0; l < bucketElementCounts[k]; l++) {
                    arr[index++] = bucket[k][l];
                }
            }
            //每列处理完毕后，需要将bucketElementCounts[k]重新置为0
            bucketElementCounts[k] = 0;
        }*/
    }
}

