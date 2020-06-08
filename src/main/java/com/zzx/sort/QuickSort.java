package com.zzx.sort;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-08 19:47
 */
public class QuickSort {
    public static void main(String[] args) {
        /*int[] arr = {-9, 89, 0, 23, -567, 70};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));*/

        //测试一下快速排序的速度，给出80000个数据的数组，进行测试
        //创建一个存有80000个随机数的数组
        int[] arr = new int[800000];
        //将随机数存入这个数组
        for (int i = 0; i < 800000; i++) {
            arr[i] = (int) (Math.random() * 80000000);
        }
        //记录开始排序的时间
        long startTime = System.currentTimeMillis();
        quickSort(arr, 0, arr.length - 1);
        //记录排序完成的时间
        long endTime = System.currentTimeMillis();
        //测试完成8万数据花费了25毫秒，80万数据花费116毫秒
        System.out.println("一共花费了" + (endTime - startTime) + "毫秒");
    }

    /**
     * 快速排序
     *
     * @param arr   需要排序的数组
     * @param left  最左边的索引
     * @param right 最右边的索引
     */
    public static void quickSort(int[] arr, int left, int right) {
        //左下标准
        int l = left;
        //右下标
        int r = right;
        //定义辅助变量temp
        int temp = 0;
        //定义中轴值
        int pivot = arr[(left + right) / 2];
        //当左下标小于右下标的时候则一直循环
        while (l < r) {
            //左下标从0开始扫描，直到找到大于等于中轴值pivot的数，才退出此while循环
            while (arr[l] < pivot) {
                l += 1;
            }
            //右下标从arr.length-1开始扫描，直到找到小于等于中轴值pivot的数，才退出此while循环
            while (arr[r] > pivot) {
                r -= 1;
            }
            //当左右下标相等时，意味着左右两边已经分别排好序了，即左边都是比中轴值小的数，右边都是比中轴值大的数
            if (l >= r) {
                break;
            }
            //当执行到这步时，说明l!=r，程序没有退出循环，而且此时的左下标索引对应的数是比中间值大的，右下标索引对应的数是比中间值小的
            // 因此需要利用中间变量temp互换这两个元素
            temp = arr[l];
            arr[l] = arr[r];
            arr[r] = temp;

            /*
                下午想了好久都没明白这里的两个if语句的真正含义，老师的解释对我产生了很大的误导哈哈哈。回家吃完饭想了一会就明白了
                其实这里的两个if最主要的目的就是防止数组中存在和中轴值pivot相等的数从而让程序陷入互换死循环
                防止以后忘记，这里做一下具体的分析，假设初始数组是{-9, 89, 0, 23, 0, 70}方便下面的演示
                1。l=left=0；r=right=arr.length-1=5；pivot=arr[2]=0；
                2。当退出35行的while循环后 arr[l]=arr[1]=89>=pivot=0，所以 l=1；
                3。当退出39行的while循环后 arr[r]=arr[4]=0<=pivot=0,所以 r=4；
                4。再对arr[1]和arr[4]进行互换，此时arr[1]=0，arr[4]=89，此时的数组为：{-9, 0, 0, 23, 89, 70}
                5。此时l还是小于r的，所以不会退出最外层的while循环，程序继续执行
                6。当退出35行的while循环后 arr[l]=arr[1]=0>=pivot=0，所以 l=1；
                7。当退出39行的while循环后 arr[r]=arr[2]=0<=pivot=0,所以 r=2；
                8。此时再将arr[1]和arr[2]进行互换，由于他们都是0，而且满足l<r，
                因为l永远等于1，r永远等于2，而arr[1]==arr[2]，这样就进入了反复的相互调换，程序就出现了死循环
                将上面的数组替换成{-9, 0, 0, 23, 89, 70}，并且注释掉下面两个if测试，确实是出现了死循环
             */
            if (arr[l] == pivot) {
                r -= 1;
            }
            if (arr[r] == pivot) {
                l += 1;
            }
        }
            /*
                这里老师说的是避免栈溢出，这也导致我想了好久哈哈哈哈
                其实这里的判断是还有一方面是为了进行下面的左右两边的递归。
                这里的判断是这样的：
                    当l==r时，其实已经对中轴值的左右两边做好排序了，即中轴值左边都是小于中轴值的数，中轴值的右边都是大于中轴值的数
                    此时中轴值已经无需再移动了，因为确定了左边的都是小于它的数，右边都是大于他的数，
                    此时只需要再对left开始到中轴值左边一位的数结束  和  中轴值右边一位的数开始到right结束这两个范围进行排序即可
                    而此时将r左移一位其实就是将中轴值左边的那部分再进行递归排序，即范围是[left,r]对于上述数组例子来说其实就是[0,1]
                    将l右移一位其实就是将中轴值右边的那部分再进行递归排序，即范围是[l,right]对于上述数组例子来说其实就是[3,5]
             */
        if (l == r) {
            r -= 1;
            l += 1;
        }
        //向左递归
        if (left < r) {
            quickSort(arr, left, r);
        }
        //向右递归
        if (right > l) {
            quickSort(arr, l, right);
        }
    }
}
