package com.zzx.tree;

/**
 * @Author zzx
 * @Date 2020-06-14 11:54
 */
public class ArrayBinaryTreeDemo {
    public static void main(String[] args) {
        int[] arr = {1, 2, 5, 6, 8, 9};
        ArrayBinaryTree arrayBinaryTree = new ArrayBinaryTree(arr);
        System.out.println("前序遍历的结果～～～");
        arrayBinaryTree.preOrder(0);
        System.out.println();
        System.out.println("中序遍历的结果～～～");
        arrayBinaryTree.infixOrder(0);
        System.out.println();
        System.out.println("后序遍历的结果～～～");
        arrayBinaryTree.postOrder(0);
    }
}

/**
 * 使用ArrayBinaryTree来实现二叉树的遍历
 */
class ArrayBinaryTree {
    /**
     * 存储树的节点数据的数组
     */
    private int[] arr;

    public ArrayBinaryTree(int[] arr) {
        this.arr = arr;
    }

    /**
     * 将给定的数组按照二叉树的前序遍历方式遍历得到结果
     *
     * @param index 数组中的索引
     */
    public void preOrder(int index) {
        /*
                         1⃣ (0)
                      /     \
                (1) 2⃣        3⃣ (2)
                 /   \     /   \
           (3) 4⃣  (4) 5⃣  6⃣ (5)   7⃣ (6)

           如上图所示，数组的内容是{1,2,3,4,5,6,7},二叉树中方框内部的数字是数组中的数据，括号内的数字是数组的下标
           顺序二叉树通常只考虑完全二叉树，而且观察上图可以得到顺序存储二叉树的三个特点，下述中的n即为数组的下标，也是二叉树的元素顺序
            1。第 n 个元素的左子节点为 2 * n + 1
            2。第 n 个元素的右子节点为 2 * n + 2
            3。第 n 个元素的父节点为 (n-1) / 2
         */
        //通过上述分析，开始代码，首先先判断当前数组是否为空，为空则给出提示信息并且返回
        if (arr == null || arr.length == 0) {
            System.out.println("数组为空，不能按照二叉树的前序遍历～～～");
            return;
        }
        //首先输出当前的节点数据
        System.out.print(arr[index] + "\t");
        //再判断当前元素是否有左子节点，向左开始递归
        if ((index * 2 + 1) < arr.length) {
            preOrder(index * 2 + 1);
        }
        //再判断当前元素是否有右边子节点，向右开始递归
        if ((index * 2 + 2) < arr.length) {
            preOrder(index * 2 + 2);
        }
    }

    /**
     * 将给定的数组按照二叉树的中序遍历方式遍历得到结果
     *
     * @param index 数组的索引
     */
    public void infixOrder(int index) {
        //向左递归
        if ((index * 2 + 1) < arr.length) {
            infixOrder(index * 2 + 1);
        }
        //输出当前元素
        System.out.print(arr[index] + "\t");
        if ((index * 2 + 2) < arr.length) {
            infixOrder(index * 2 + 2);
        }
    }

    /**
     * 将给定的数组按照二叉树的后序遍历方式遍历得到结果
     *
     * @param index 数组的索引
     */
    public void postOrder(int index) {
        if ((index * 2 + 1) < arr.length) {
            postOrder(index * 2 + 1);
        }
        if ((index * 2 + 2) < arr.length) {
            postOrder(index * 2 + 2);
        }
        System.out.print(arr[index] + "\t");
    }
}
