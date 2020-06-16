package com.zzx.tree;

import java.util.Arrays;

/**
 * @Author zzx
 * @Date 2020-06-16 19:58
 */
public class HeapSort {
    public static void main(String[] args) {
        int[] arr = {4, 6, 8, 5, 9, 18, -6, -88};
        heapSort(arr);
    }

    public static void heapSort(int arr[]) {
        System.out.println("堆排序~~~");
        //由下面分析出的最后一个非叶子节点的索引的公式：index = arr.length/2 - 1
        //对其进行for循环，将数组转为大顶堆
        for (int i = arr.length / 2 - 1; i >= 0; i--) {
            adjustHeap(arr, i, arr.length);
        }

        //将堆顶元素与末尾元素交换，将最大元素"沉"到数组末端
        //重新调整结构，使其满足堆定义，然后继续交换堆顶元素与当前末尾元素，反复执行调整+交换步骤，直到整个序列有序
        int temp = 0;
        for (int j = arr.length - 1; j > 0; j--) {
            //将堆顶的数与最后一个数交换
            temp = arr[0];
            arr[0] = arr[j];
            arr[j] = temp;

            //继续对除了最后一个数之外的数组进行大顶堆转换，没转换一次length就减少一，因此这里length为j
            //而index从0开始是因为堆顶的数与数组的最后一个数互换了，需要重新从0开始判断
            adjustHeap(arr, 0, j);
        }

        System.out.println(Arrays.toString(arr));
    }


    /*
        关于寻找数组中最后一个非叶子节点的索引的公式：index = arr.length/2 - 1
        因为数组是顺序排列的，因此只需要先找到数组的最后一个元素，然后再找到最后一个元素的父节点，该父节点其实就是数组中的最后一个非叶子节点
        根据数组转换二叉树的规律，可以得到假设父节点在数组中的索引为n，则它的左子节点的索引即为2n+1，右子节点的索引为2n+2
        假设此时最后一个元素为左子节点，可以得到 arr.length - 1 = 2n + 1 => n = (arr.length - 1 - 1)/2 = arr.length/2 - 1
        假设此时最后一个元素为右子节点，可以得到:
        arr.length - 1 = 2n + 2 => n = (arr.length - 1 - 2)/2 = arr.length/2 - 3/2 = arr.length/2 - 1
        因此通过上述分析，得到数组中最后一个非叶子节点的索引即为 index = arr.length/2 - 1
    */

    /**
     * 将每一个二叉子树转(数组)换成一个大顶堆
     *
     * @param arr    需要调整的数组
     * @param index  非叶子节点在数组中的索引
     * @param length 数组中需要调整的元素个数
     */
    public static void adjustHeap(int[] arr, int index, int length) {
        //首先定义一个temp变量记录当前非叶子节点的值
        int temp = arr[index];
        //因为是非叶子节点，因此至少有左子节点，因为定义的数组转成的二叉树是从上到下从左到右分布的
        //这里使用for循环的原因是这样的，因为其实对整个二叉树进行转换的顺序是从右到左，从下到上
        //而有可能存在这样的情况，在对某一个子树进行转换的时候可能破坏了它的左或者右节点的子树的原有结构，所以需要继续向下判断
        for (int k = index * 2 + 1; k < length; k = k * 2 + 1) {
            //第一次进入循环其实这个k就是当前非叶子节点的左子节点
            //对该非叶子节点的左右子节点进行大小的判断时还有一个注意事项，该非叶子节点可能只存在左子节点，因此这里需要加一个判断
            if (k + 1 < length && arr[k] < arr[k + 1]) {
                //如果当前非叶子节点存在右子节点，而且左子节点小于右子节点，则将k指向右子节点
                k++;
            }
            //比较完了该非叶子节点的左右子节点之后，此时的k就应该指向了他们中的较大值，此时再将该非叶子节点与该值进行比较
            if (arr[index] < arr[k]) {
                //如果当前非叶子节点小于他的子节点，则将他们互换位置
                arr[index] = arr[k];
                arr[k] = temp;
                //如果发生了交换，则把k赋值给index，再使用for循环对以k为父节点的子树进行判断
                index = k;
            } else {
                //若非叶子节点大于子节点，则说明没有对该子树进行转换，即没有改变当前子树的位置，所以可以直接退出循环，因为是从下到上转换的，下面的子树肯定已经有序了
                break;
            }
        }
    }
}