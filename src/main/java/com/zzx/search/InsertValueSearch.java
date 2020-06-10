package com.zzx.search;

/**
 * @Author zzx
 * @Date 2020-06-10 21:46
 */
public class InsertValueSearch {
    public static void main(String[] args) {
        int arr[] = {1, 8, 10, 89, 1000, 1000, 1234};
        int index = insertValueSearch(arr, 0, arr.length - 1, 1234);
        System.out.println("index = " + index);
    }

    /**
     * 差值查找，对于数据量较大，关键字分布比较均匀的查找表来说，采用插值查找, 速度较快
     * 但是对于关键字分布不均匀的情况下，该方法不一定比二分查找要好
     *
     * @param arr     在此数组中查找数据
     * @param left    左边索引
     * @param right   右边索引
     * @param findVal 需要查找的元素
     * @return 找到了则返回相应的下标，没找到就返回-1
     */
    public static int insertValueSearch(int[] arr, int left, int right, int findVal) {
//由于是有序的数组，当要查找的值大于数组的最大值或者小于数组的最小值，则直接返回-1
        //由于left和right一直在移动，所以当left>right时，则说明数组中找不到该数
        if (left > right || arr[0] > findVal || arr[arr.length - 1] < findVal) {
            return -1;
        }
        /*
            求出mid，值得注意的是，此处的mid是一个自适应值，而不是简单的(left+right)/2
            mid = (left+right)/2 = 1/2*(right-left)+left => mid = (findVal-arr[left])/(arr[right]-arr[left])*(right-left)+left
            上述式子先将 mid = (left+right)/2转换成1/2*(right-left)+left，原来二分查找的mid是1/2的left加上right
            此处将1/2转换成需要查找的元素与arr[left]的差值和arr[right]与arr[left]的差值的比例
         */
        int mid = (findVal - arr[left]) / (arr[right] - arr[left]) * (right - left) + left;
        int midVal = arr[mid];
        if (midVal < findVal) {
            return insertValueSearch(arr, mid + 1, right, findVal);
        } else if (midVal > findVal) {
            return insertValueSearch(arr, left, mid - 1, findVal);
        } else {
            return mid;
        }
    }
}
