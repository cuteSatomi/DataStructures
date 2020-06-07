package com.zzx.linkedlist;

/**
 * @Author zzx
 * @Date 2020-06-02 19:15
 */
public class DoubleLinkedListDemo {
    public static void main(String[] args) {
        System.out.println("双向链表测试~~~");

        HeroNode2 hero1 = new HeroNode2(1, "宋江", "及时雨");
        HeroNode2 hero2 = new HeroNode2(2, "卢俊义", "玉麒麟");
        HeroNode2 hero3 = new HeroNode2(3, "吴用", "智多星");
        HeroNode2 hero4 = new HeroNode2(4, "林冲", "豹子头");

        //创建一个双向链表
        DoubleLinkedList doubleLinkedList = new DoubleLinkedList();
        doubleLinkedList.addByOrder(hero1);
        doubleLinkedList.addByOrder(hero4);
        doubleLinkedList.addByOrder(hero2);
        doubleLinkedList.addByOrder(hero3);

        doubleLinkedList.list();
        System.out.println();

        //修改
        HeroNode2 newHeroNode = new HeroNode2(4, "公孙胜", "入云龙");
        doubleLinkedList.update(newHeroNode);
        System.out.println("修改后的链表情况~~~");
        doubleLinkedList.list();
        System.out.println();

        //删除
        doubleLinkedList.del(4);
        System.out.println("删除后的链表情况~~");
        doubleLinkedList.list();

    }
}

class DoubleLinkedList {
    //初始化一个头结点，头结点固定不动，不存放具体的数据
    private HeroNode2 head = new HeroNode2(0, "", "");

    /**
     * 遍历链表
     */
    public void list() {
        //如果头结点的next为null，则说明链表为空
        if (head.next == null) {
            System.out.println("链表为空");
            return;
        }

        //因为头结点是固定不动的，此时我们同样需要定义一个辅助变量temp
        HeroNode2 temp = head.next;
        while (true) {
            //判断链表是否到了末尾
            if (temp == null) {
                break;
            }
            //打印当前节点的数据
            System.out.println(temp);
            //将temp指针后移
            temp = temp.next;
        }
    }

    /**
     * 新增方法
     *
     * @param heroNode 传入的英雄节点
     */
    public void add(HeroNode2 heroNode) {
        //创建一个temp节点来存放指针的位置
        HeroNode2 temp = head;
        //由于每次新增都是在链表的末尾新增数据，所以要判断当前节点是否是链表的末尾
        while (true) {
            //假如当前节点的next为null，则说明此节点是最后一个节点，break退出循环
            if (temp.next == null) {
                break;
            }
            //如果不是最后一个节点，则需要将temp指针后移
            temp = temp.next;
        }
        //此时退出循环得到的temp就是链表的末尾
        temp.next = heroNode;
        heroNode.pre = temp;
    }

    /**
     * 按照节点的编号来新增双向链表
     *
     * @param heroNode 要插入的节点
     */
    public void addByOrder(HeroNode2 heroNode) {
        HeroNode2 temp = head;
        HeroNode2 next = null;
        boolean flag = false;
        while (true) {
            //遍历到了链表的末尾，break退出循环
            if (temp.next == null) {
                break;
            }
            //假如找到了与之编号相同的节点，则break退出循环
            if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            } else if (temp.next.no > heroNode.no) {
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("准备插入的英雄的编号 %d 已存在, 新增失败\n", heroNode.no);
        } else {
            //如果不进行判断可能会出先空指针异常
            if (temp.next != null) {
                //假设遍历到了链表的末尾，此处next = temp.next = null
                next = temp.next;
                heroNode.next = temp.next;
                heroNode.pre = temp;
                temp.next = heroNode;
                //而此处因为next = null，所以next.pre可能会出现空指针异常
                next.pre = heroNode;
            } else {
                //假如遍历到了链表的末尾，则直接在temp的末尾新增节点
                temp.next = heroNode;
                heroNode.pre = temp;
            }
        }
    }

    public void update(HeroNode2 newHeroNode) {
        //判断链表是否为空
        if (head.next == null) {
            System.out.println("链表为空~~~");
            return;
        }
        //定义一个辅助变量
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            //遍历到链表的末尾，break退出循环
            if (temp == null) {
                break;
            }
            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            System.out.printf("没有找到编号为 %d 的节点，修改失败\n", newHeroNode.no);
        }
    }

    /**
     * 删除节点
     *
     * @param no 要删除节点的编号
     */
    public void del(int no) {
        if (head.next == null) {
            System.out.println("链表为空~~~");
            return;
        }
        HeroNode2 temp = head.next;
        boolean flag = false;
        while (true) {
            if (temp == null) {
                break;
            }
            //找到了要删除的节点
            if (temp.no == no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //如果找到
        if (flag) {
            temp.pre.next = temp.next;
            if (temp.next != null) {
                temp.next.pre = temp.pre;
            }
        } else {
            System.out.printf("没有找到编号为 %d 的节点，删除失败\n", no);
        }
    }
}

class HeroNode2 {
    public int no;
    public String name;
    public String nickname;
    //指向下一个HeroNode节点
    public HeroNode2 next;
    //指向前一个HeroNode节点
    public HeroNode2 pre;

    //定义构造函数
    public HeroNode2(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode2{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }


}
