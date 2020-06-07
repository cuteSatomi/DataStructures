package com.zzx.linkedlist;

/**
 * @Author zzx
 * @Date 2020-06-02 19:16
 */
public class Josephu {
    public static void main(String[] args) {
        CircleSingleLinkedList circleSingleLinkedList = new CircleSingleLinkedList();
        circleSingleLinkedList.addBoy(125);
        circleSingleLinkedList.showBoys();
        circleSingleLinkedList.countBoy(10, 20, 125);
    }
}

/**
 * 创建一个环形链表来存储Boy节点
 */
class CircleSingleLinkedList {
    /**
     * 定义一个头节点，默认值为null
     */
    private Boy first = null;

    /**
     * 新增节点方法
     *
     * @param nums 环形链表的长度
     */
    public void addBoy(int nums) {
        //先对nums做一个简单的校验
        if (nums < 0) {
            System.out.println("您输入的数据有误，请重新输入～～～");
            return;
        }

        //定义一个curBoy节点来帮助插入数据的同时形成环形链表
        Boy curBoy = null;
        for (int i = 1; i <= nums; i++) {
            //当插入第一个数据时，需要进行一些处理，即让他自己指向自己，形成一个自闭合的环形链表
            Boy boy = new Boy(i);
            if (i == 1) {
                first = boy;
                first.setNext(first);
                curBoy = first;
            } else {
                //当插入第二个节点时，直接将其插入到first节点的后面，此时的curBoy节点其实就是first
                curBoy.setNext(boy);
                //再将插入的boy节点的next指向first节点
                boy.setNext(first);
                //再将curBoy节点指向boy
                curBoy = boy;
            }
        }
    }

    /**
     * 遍历当前的环形链表
     */
    public void showBoys() {
        //判断当前链表是否为空，由于最开始给first赋了初值null，而如果没有假如任何的节点，此时的first应该仍然为null
        if (first == null) {
            System.out.println("当前链表为空～～～");
            return;
        }
        //first头节点不能动，所以仍需要定义一个辅助变量
        Boy curBoy = first;
        while (curBoy.getNext() != first) {
            System.out.printf("这是第 %d 个小孩\n", curBoy.getNo());
            curBoy = curBoy.getNext();
        }
        System.out.printf("这是第 %d 个小孩\n", curBoy.getNo());
    }

    /**
     * 根据用户输入的参数计算小孩出圈的顺序
     *
     * @param startNo  表示从第几个小孩开始数
     * @param countNum 表示数几下会有一个小孩出圈
     * @param nums     表示圈中初始有几个小孩
     */
    public void countBoy(int startNo, int countNum, int nums) {
        //对用户输入的数据进行一个简单的校验
        if (first == null || startNo < 1 || startNo > nums) {
            System.out.println("您输入的参数有误，请重新输入～～～");
            return;
        }
        //定义一个辅助指针pre，指向first指针的上一个位置
        //最初first指针是在链表的额头部，因此此时pre应该是指向链表的末尾
        Boy pre = first;
        while (true) {
            //假如pre的下一个节点是first，则说明此时pre已经遍历到链表的末尾了，break退出
            if (pre.getNext() == first) {
                break;
            }
            pre = pre.getNext();
        }
        //将first指针移动到开始数的位置
        for (int i = 0; i < startNo - 1; i++) {
            first = first.getNext();
            pre = pre.getNext();
        }

        //当first节点与pre节点相同时，则说明链表中只剩下一个节点，此时循环结束
        while (first != pre) {
            for (int i = 0; i < countNum - 1; i++) {
                first = first.getNext();
                pre = pre.getNext();
            }
            System.out.printf("第 %d 个小孩出圈\n", first.getNo());
            //将pre指针的next指向出圈的小孩的下一个节点
            pre.setNext(first.getNext());
            //再将first节点后移
            first = first.getNext();
        }
        System.out.printf("第 %d 个小孩出圈", first.getNo());
    }
}

/**
 * 创建一个Boy类当作节点
 */
class Boy {
    /**
     * 编号
     */
    private int no;
    /**
     * 指向下一个节点
     */
    private Boy next;

    public Boy(int no) {
        this.no = no;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Boy getNext() {
        return next;
    }

    public void setNext(Boy next) {
        this.next = next;
    }
}


