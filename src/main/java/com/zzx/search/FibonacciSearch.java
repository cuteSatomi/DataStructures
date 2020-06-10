package com.zzx.search;

import java.util.Arrays;

/**
 * 斐波那契查找
 *
 * @Author zzx
 * @Date 2020-06-10 21:56
 */
public class FibonacciSearch {
    public static void main(String[] args) {
        int[] arr = {1, 8, 10, 89, 1000, 1234};
        System.out.println(fibonacciSearch(arr, 1));
    }

    /**
     * 斐波那契查找利用了斐波那契数列
     * 斐波那契数列的特性如下 F[k] = F[k-1] + F[k-2]，可以转换成 F[k] - 1 = (F[k-1] -1) + (F[k-2] - 1) + 1
     * <p>
     * |<======================  F[k] - 1  ========================>|
     * _____________________________________________________________
     * | low |                          | mid |              | high |
     * ⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻⁻
     * |<========= F[k-1] -1 =========>|     |<==== F[k-2] - 1 ===>|
     * <p>
     * 即此时的mid = low + F[k-1] -1，如果arr[mid]!=findVal，则继续向前或者向后查找
     * 这里需要注意的一点是但顺序表长度 n 不一定刚好等于 F[k]-1，所以需要将原来的顺序表长度 n 增加至 F[k]-1。
     * 这里的 k 值只要能使得 F[k]-1 恰好大于或等于 n 即可，顺序表长度增加后，为了保证数组依然是有序的，新增的位置（从 n+1 到 F[k]-1 位置），都赋为 n 位置的值即可。
     *
     * @param arr     在此数组中查找
     * @param findVal 需要查找的数
     * @return 找到则返回索引，未找到则返回-1
     */
    public static int fibonacciSearch(int[] arr, int findVal) {
        //最左侧索引
        int low = 0;
        //最右侧索引
        int high = arr.length - 1;
        //斐波那契数列的当前下标
        int k = 0;
        //存放mid的值
        int mid = 0;
        //获取到斐波那契数列
        int[] f = fib();

        //首先获取到斐波那契数列的下标
        while (f[k] - 1 < arr.length) {
            //只要当前数组的长度大于f[k] - 1，则让k后移一位继续判断
            k++;
        }

        //当退出上面的while循环时，说明f[k]-1 >= arr.length
        //此时不管F[k]-1是否等于arr.length，我们都以f[k]-1的大小构建一个新的数组，如果f[k]-1大于了原数组的长度，多出来的部分用0补齐
        int[] temp = Arrays.copyOf(arr, f[k] - 1);

        //为了让数组依然有序，将多出来的部分的重新赋值为数组中的最大值，即arr[high]
        for (int i = high + 1; i < temp.length; i++) {
            temp[i] = arr[high];
        }

        // 使用 while 来循环处理，找到findVal
        //此处只要low <= high，就可以一直找
        while (low <= high) {
            //根据上面推导的公式得到mid = low + F[k-1] -1
            mid = low + f[k - 1] - 1;
            if (temp[mid] > findVal) {
                //说明要查找的数在mid的左侧，将high设置为mid-1的位置
                high = mid - 1;
                //因为 F[k] = F[k-1] + F[k-2] ，此时从左侧继续查找，即 F[k-1] = F[k-2] + F[k-3]
                //将k = k - 1代入前面的式子即可得到后面的式子，即得到k-=1；
                k--;
            } else if (temp[mid] < findVal) {
                low = mid + 1;
                //因为 F[k] = F[k-1] + F[k-2] ，此时从右侧继续查找，即 F[k-2] = F[k-3] + F[k-4]
                //将k = k - 2代入前面的式子即可得到后面的式子，即得到k-=2；
                k -= 2;
            } else {
                //说明找到了要找的数
                //这里的if和else暂时没搞清楚，好困啊顶不住了先睡
                if (mid <= high) {
                    return mid;
                } else {
                    return high;
                }
            }
        }
        //没有查询到，返回-1
        return -1;
    }

    /**
     * 首先使用非递归的方式得到一串长度为20的斐波那契数列
     *
     * @return 斐波那契数列
     */
    public static int[] fib() {
        int[] f = new int[20];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < f.length; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }
        return f;
    }
}
