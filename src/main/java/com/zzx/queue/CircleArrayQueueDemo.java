package com.zzx.queue;

import java.util.Scanner;

/**
 * @Author zzx
 * @Date 2020-05-24 20:22
 */
public class CircleArrayQueueDemo {
    public static void main(String[] args) {
        CircleArrayQueue arrayQueue = new CircleArrayQueue(4);
        //接受用户输入
        char key = ' ';
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        //输出一个菜单
        while (loop) {
            System.out.println("s(show): 显示队列");
            System.out.println("a(add): 添加数据到队w列");
            System.out.println("g(get): 从队列取出数据");
            System.out.println("h(head): 查看队列头的数据");
            System.out.println("e(exit): 退出程序");
            //接受一个字符
            key = scanner.next().charAt(0);
            switch (key) {
                case 's':
                    arrayQueue.showQueue();
                    break;
                case 'a':
                    System.out.println("输入一个数");
                    int value = scanner.nextInt();
                    arrayQueue.addQueue(value);
                    break;
                case 'g':
                    try {
                        int result = arrayQueue.getQueue();
                        System.out.printf("取出的数据是%d\n", result);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 'h':
                    try {
                        int result = arrayQueue.headQueue();
                        System.out.printf("队列头的数据是%d\n", result);
                    } catch (Exception e) {
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

class CircleArrayQueue {
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
    public CircleArrayQueue(int arrMaxSize) {
        this.maxSize = arrMaxSize;
        this.arr = new int[maxSize];
    }

    /**
     * 判断队列是否已满
     *
     * @return 满了则返回true，否则返回false
     */
    public boolean isFull() {
        return (rear + 1) % maxSize == front;
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
        //因为这里rear和front的初始值都为0，所以直接插入数据即可
        arr[rear] = n;
        //因为是环形队列考虑首位相接，所以此处取模
        rear = (rear + 1) % maxSize;
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
        //先取得当前front的值保存，再将front后移
        int value = arr[front];
        front = (front + 1) % maxSize;
        return value;
    }

    /**
     * 显示队列中的所有数据
     */
    public void showQueue() {
        if (isEmpty()) {
            System.out.println("队列为空，没有数据需要显示～～～");
            return;
        }
        /*
        此处只需要遍历有效数据就足够了，由于循环队列中可能并不是所有的空间都存有数据
        因此新增了一个size()方法来获取循环队列中的有效数据个数
        而输入中的i对maxSize取余是为了防止出现数组下标越界，因为初始化数组的时候给的长度是4，索引最大值为3
        */
        for (int i = front; i < front + this.size(); i++) {
            System.out.printf("arr[%d]=%d\n", i % maxSize, arr[i % maxSize]);
        }
    }

    /**
     * 的到当前队列有效值的个数
     *
     * @return 有效值个数
     */
    public int size() {
        return (rear + maxSize - front) % maxSize;
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
        return arr[front];
    }
}