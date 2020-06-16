package com.zzx.huffmantree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Author zzx
 * @Date 2020-06-16 19:59
 */
public class HuffmanTree {
    public static void main(String[] args) {
        int[] arr = {13, 7, 8, 3, 29, 6, 1};
        Node root = createHuffmanTree(arr);
        preOrder(root);
    }

    /**
     * 创建霍夫曼树
     *
     * @param arr 要转成霍夫曼树的数组
     * @return 返回霍夫曼树的root节点
     */
    public static Node createHuffmanTree(int[] arr) {
        //首先按照数组中的数值创建Node节点，并且存入ArrayList中
        List<Node> nodes = new ArrayList<>();
        for (int value : arr) {
            nodes.add(new Node(value));
        }

        //循环处理list集合中的node，因为每次处理都移除权值最小的两个node，再将他们的父节点即以他们权值的和新创建的node加入到list中
        //因此循环退出的条件就是list集合的size==1
        while (nodes.size() > 1) {
            //每次处理前都对集合进行排序
            Collections.sort(nodes);

            //取出当前集合中权值最小的node作为要构建的树的左子节点
            Node leftNode = nodes.get(0);
            //取出当前集合中权值第二小的node作为要构建的树的右子节点
            Node rightNode = nodes.get(1);
            //父节点的权值就是左右子节点的权值之和
            Node parentNode = new Node(leftNode.value + rightNode.value);
            //再将父节点的左右指针分别指向leftNode和rightNode
            parentNode.leftNode = leftNode;
            parentNode.rightNode = rightNode;
            //最后将这两个子节点从集合中取出，将父节点添加金集合
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);
        }
        return nodes.get(0);
    }

    /**
     * 前序遍历
     *
     * @param root 开始遍历的节点，即根节点
     */
    public static void preOrder(Node root) {
        if (root != null) {
            root.preOrder();
        } else {
            System.out.println("当前数为空，无法遍历~~~");
        }
    }
}


/**
 * 创建节点类，因为需要排序，所以直接使其实现Comparable接口，方便使用Collections工具类对其进行排序
 */
class Node implements Comparable<Node> {
    /**
     * 节点的权值
     */
    int value;

    /**
     * 左子节点
     */
    Node leftNode;
    /**
     * 右子节点
     */
    Node rightNode;

    public Node(int value) {
        this.value = value;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        //首先打印当前节点
        System.out.println(this);
        //若左子节点不为空，则向左遍历
        if (this.leftNode != null) {
            this.leftNode.preOrder();
        }
        //若右子节点不为空，则向右遍历
        if (this.rightNode != null) {
            this.rightNode.preOrder();
        }
    }

    @Override
    public String toString() {
        return "Node[value=" + value + "]";
    }

    @Override
    public int compareTo(Node o) {
        return this.value - o.value;
    }
}
