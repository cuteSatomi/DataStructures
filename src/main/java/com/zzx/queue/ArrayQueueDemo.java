package com.zzx.queue;

import java.util.Scanner;

/**
 * @Author zzx
 * @Date 2020-05-24 19:12
 */
public class ArrayQueueDemo {
    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue(3);
        //接受用户输入
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("a(add): 添加数据到队列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            System.out.println("e(exit): 退出程序");
            //接受一个字符
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case'a':
                    System.out.println("输入一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try{
                        int result = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n",result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try{
                        int result = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n",result);
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'e':
                    scanner.close();
                    loop = false;
                    break;
                default:
            }
        }
        System.out.println("程序退出～～～");
    }
}

/**
 * 使用数组模拟队列--编写一个ArrayQueue类
 */
class ArrayQueue {
    /**
     * 数组的最大容量
     */
    private int maxSize;
    /**
     * 队列头
     */
    private int front;
    /**
     * 队列尾
     */
    private int rear;
    /**
     * 该数组用于存放数据，模拟队列
     */
    private int[] arr;

    /**
     * 创建队列的构造器
     *
     * @param arrMaxSize 队列的最大容量
     */
    public ArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        this.arr = new int[maxSize];
        //指向队列头部
        this.front = -1;
        //指向队列尾部
        this.rear = -1;
    }

    /**
     * 判断队列是否已满
     *
     * @return 满了则返回true，否则返回false
     */
    public boolean isFull() {
        return rear == maxSize - 1;
    }

    /**
     * 判断队列是否为空
     *
     * @return 为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return rear == front;
    }

    /**
     * 添加数据到队列
     *
     * @param n 添加的数据
     */
    public void addQueue(int n) {
        if (isFull()) {
            System.out.println("队列已满，无法再插入数据～～～");
            return;
        }
        //让rear后移
        rear++;
        arr[rear] = n;
    }

    /**
     * 得到队列中的数据
     *
     * @return 队列的数据
     */
    public int getQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空，无法取出数据～～～");
        }
        //让front后移
        front++;
        return arr[front];
    }

    /**
     * 显示队列中的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据需要显示～～～");
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("arr[%d]=%d\n", i, arr[i]);
        }
    }

    /**
     * 获取队列的头数据
     *
     * @return 队列的头数据
     */
    public int headQueue() {
        if (isEmpty()) {
            throw new RuntimeException("队列为空～～～");
        }
        return arr[front + 1];
    }
}


