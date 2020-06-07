package com.zzx.stack;

import java.util.Scanner;
import java.util.Stack;

/**
 * @Author zzx
 * @Date 2020-06-03 19:13
 */
public class SingleLinkedListStackDemo {
    public static void main(String[] args) {
        SingleLinkedListStack stack = new SingleLinkedListStack(4);
        String key = "";
        //控制循环是否退出
        boolean loop = true;
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("show: 显示栈~~~");
            System.out.println("exit: 退出程序~~~");
            System.out.println("push: 添加数据到栈(入栈)~~~");
            System.out.println("pop: 从栈取出数据(出栈)~~~");
            System.out.println("请输入你的选择~~~");
            key = scanner.next();
            switch (key) {
                case "show":
                    stack.list();
                    break;
                case "exit":
                    scanner.close();
                    loop = false;
                    break;
                case "push":
                    System.out.println("请输入一个数");
                    int value = scanner.nextInt();
                    stack.push(value);
                    break;
                case "pop":
                    try {
                        int res = stack.pop();
                        System.out.println("出栈的数据是：" + res);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
        System.out.println("程序退出~~~");
    }
}


/**
 * 定义一个单向链表来模拟栈
 */
class SingleLinkedListStack {
    /**
     * 链表的长度
     */
    private int maxSize;

    //定义一个头节点
    private NumNode head = new NumNode(-666);

    public SingleLinkedListStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 判断是否为空
     *
     * @return true为空
     */
    public boolean isEmpty() {
        return head.next == null;
    }

    /**
     * 判断是否为满
     *
     * @return true为满
     */
    public boolean isFull() {
        NumNode temp = head.next;
        int count = 0;
        while (true) {
            if (temp == null) {
                break;
            }
            count++;
            temp = temp.next;
        }
        return count == maxSize;
    }

    /**
     * 遍历
     */
    public void list() {
        if (isEmpty()) {
            System.out.println("栈为空~~~");
            return;
        }
        //使用真正的栈来完成
        NumNode cur = head.next;
        Stack<NumNode> stack = new Stack<NumNode>();
        while (cur != null) {
            stack.push(cur);
            cur = cur.next;
        }
        while (stack.size() > 0) {
            NumNode numNode = stack.pop();
            System.out.println(numNode.num);
        }
    }

    /**
     * 新增数据
     *
     * @param value 要新增的数据
     */
    public void push(int value) {
        //判断是否栈满
        if (isFull()) {
            System.out.println("栈已满~~~");
            return;
        }
        NumNode numNode = new NumNode(value);
        NumNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            temp = temp.next;
        }
        temp.next = numNode;
    }

    /**
     * 取出数据
     *
     * @return 取出的数据
     */
    public int pop() {
        //判断是否栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空~~~");
        }
        //定义一个辅助指针
        NumNode temp = head;
        while (true) {
            //找到倒数第二个链表
            if (temp.next.next == null) {
                break;
            }
            temp = temp.next;
        }
        //将最后一个节点的数保存
        int value = temp.next.num;
        //将倒数第二个节点的next指向null，即丢弃最后一个节点
        temp.next = null;
        return value;
    }
}


/**
 * 定义一个类来当作链表的节点
 */
class NumNode {
    /**
     * 存储的数据
     */
    public int num;
    /**
     * 指向下一个节点
     */
    public NumNode next;

    public NumNode(int num) {
        this.num = num;
    }
}

