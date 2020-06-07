package com.zzx.linkedlist;

/**
 * @Author zzx
 * @Date 2020-05-31 17:35
 */
public class SingleLinkedListDemo {
    public static void main(String[] args) {
        HeroNode hero1 = new HeroNode(1, "宋江", "及时雨");
        HeroNode hero2 = new HeroNode(2, "卢俊义", "玉麒麟");
        HeroNode hero3 = new HeroNode(3, "吴用", "智多星");
        HeroNode hero4 = new HeroNode(4, "林冲", "豹子头");
//        HeroNode hero5 = new HeroNode(5, "zzx", "蜘蛛侠");

        SingleLinkedList singleLinkedList = new SingleLinkedList();
        singleLinkedList.addByOrder(hero1);
        singleLinkedList.addByOrder(hero4);
        singleLinkedList.addByOrder(hero3);
        singleLinkedList.addByOrder(hero2);

//        singleLinkedList.update(hero5);


        singleLinkedList.list();
        System.out.println();
        System.out.println(getLastIndexNode(singleLinkedList.getHead(), 3));
    }

    /**
     * 得到链表的有效长度
     *
     * @param head 链表的头
     * @return 链表的有效长度
     */
    public static int getLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int length = 0;
        //定义辅助指针
        HeroNode currentNode = head.next;
        while (currentNode != null) {
            length++;
            currentNode = currentNode.next;
        }
        return length;
    }

    /**
     * 查找但链表的倒数第k个节点
     *
     * @param head  单链表的表头
     * @param index 倒数第几个节点
     * @return 存在即返回，不存在返回null
     */
    public static HeroNode getLastIndexNode(HeroNode head, int index) {
        if (head.next == null) {
            //如果链表为空，则直接返回null
            return null;
        }
        //第一次遍历得到链表的有效长度
        int size = getLength(head);
        //需要对index进行一个校验
        if (index <= 0 || index > size) {
            return null;
        }
        //定义辅助变量
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }
}

class SingleLinkedList {
    /**
     * 初始化一个头节点，头节点是不能移动的，只是方便定位
     */
    private HeroNode head = new HeroNode(0, "", "");

    /**
     * 得到链表的头
     *
     * @return 表头
     */
    public HeroNode getHead() {
        return head;
    }

    /**
     * 将两个有序单链表合并成一个仍然有序的单链表
     * @param head1 其中一个链表的head
     * @param head2 另一个链表的head
     * @return 返回合并后的但链表的head
     */
    public static HeroNode merge2List(HeroNode head1, HeroNode head2) {
        //假如两个链表都为空，则直接返回null
        if (head1.next == null && head2.next == null) {
            return null;
        }
        //假如所给的两个链表其中一个为空，则直接返回不为空的那个作为合并的结果
        if (head1.next == null || head2.next == null) {
            return head1.next == null ? head2 : head1;
        }
        //分别定义两个cur节点来记录两个链表的当前节点
        HeroNode cur1 = head1.next;
        HeroNode cur2 = head2.next;
        //定义两个boolean变量来记录两个链表是否遍历到了末尾，默认是false
        boolean flag1 = false;
        boolean flag2 = false;
        //重新定义一个新的合并后的头节点
        HeroNode mergeHead = new HeroNode(0, "", "");
        //头节点不能移动，所以需要定义一个辅助节点
        HeroNode temp = mergeHead;
        while (cur1 != null && cur2 != null) {
            //假如cur1的编号小于cur2的编号，则直接将cur1连接到新的合并链表的末尾，并且将头节点和cur1节点后移一位
            if (cur1.no < cur2.no) {
                temp.next = cur1;
                temp = temp.next;
                cur1 = cur1.next;
            } else {
                temp.next = cur2;
                temp = temp.next;
                cur2 = cur2.next;
            }
        }
        //假如是head1遍历到了末尾，则直接将head2剩下的数据全部按序插入新链表
        if (cur1 == null) {
            while (cur2 != null) {
                temp.next = cur2;
                temp = temp.next;
                cur2 = cur2.next;
            }
        }
        if (cur2 == null) {
            while (cur1 != null) {
                temp.next = cur1;
                temp = temp.next;
                cur1 = cur1.next;
            }
        }
        return mergeHead;
    }

    /**
     * 添加节点到单向链表，直接添加到链表的末尾
     *
     * @param heroNode 要加入的节点
     */
    public void add(HeroNode heroNode) {
        //因为head节点是不能移动的，所以需要定义一个temp临时存储head
        HeroNode temp = head;
        while (true) {
            if (temp.next == null) {
                break;
            }
            //如果temp没有在链表的末尾，则将其后移一个位置
            temp = temp.next;
        }
        //当退出了while循环则说明找到了链表的末尾
        temp.next = heroNode;
    }

    /**
     * 这种添加方式在添加时，根据排名将英雄添加到指定位置
     * (如果要添加的排名已存在，则添加失败，并给出提示)
     *
     * @param heroNode 要添加的节点
     */
    public void addByOrder(HeroNode heroNode) {
        //头节点不能移动，此时仍然需要一个辅助指针来帮助找到添加的位置
        HeroNode temp = head;
        //定义一个boolean变量来记录要添加的编号是否存在，默认为false
        boolean flag = false;
        while (true) {
            //如果已经找到了链表的末尾则退出循环，直接添加要添加的节点
            if (temp.next == null) {
                break;
            }
            //如果temp的next节点的no大于要添加的节点的no，则说明找到了要添加的位置，即temp和temp.next之间
            if (temp.next.no > heroNode.no) {
                break;
            }
            //如果找到一个节点与要添加的节点的编号相同，则将flag置为true，break退出循环
            else if (temp.next.no == heroNode.no) {
                flag = true;
                break;
            }
            //将temp后移一位
            temp = temp.next;
        }
        if (flag) {
            System.out.printf("准备插入的英雄编号 %d 已存在，插入失败\n", heroNode.no);
        }
        //插入到链表中，即temp的后面
        else {
            heroNode.next = temp.next;
            temp.next = heroNode;
        }
    }

    /**
     * 修改节点的信息，根据no来修改
     *
     * @param newHeroNode 要修改成的节点
     */
    public void update(HeroNode newHeroNode) {
        if (head.next == null) {
            System.out.println("链表为空～～～");
            return;
        }

        //定义一个辅助节点
        HeroNode temp = head.next;
        boolean flag = false;
        while (true) {
            //表示找到了链表的末尾,遍历完成
            if (temp == null) {
                break;
            }
            //表示找到了要修改的节点
            if (temp.no == newHeroNode.no) {
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据flag判断是否找到
        if (flag) {
            temp.name = newHeroNode.name;
            temp.nickname = newHeroNode.nickname;
        } else {
            System.out.printf("没有找到编号为 %d 的节点，更新失败\n", newHeroNode.no);
        }
    }

    /**
     * 根据编号no来删除节点
     *
     * @param no 要删除节点的编号
     */
    public void delete(int no) {
        if (head.next == null) {
            System.out.println("链表为空～～～");
        }
        //定义一个辅助节点
        HeroNode temp = head;
        boolean flag = false;

        while (true) {
            //表示到了链表的末尾，遍历结束
            if (temp.next == null) {
                break;
            }
            //表示找到了要删除的节点
            if (temp.next.no == no) {
                flag = true;
                break;
            }
            //将temp指针后移
            temp = temp.next;
        }
        if (flag) {
            //将其前后节点相连
            temp.next = temp.next.next;
        } else {
            System.out.printf("没有找到编号为 %d 的节点，删除失败\n", no);
        }
    }

    /**
     * 遍历显示链表
     */
    public void list() {
        if (head.next == null) {
            System.out.println("链表为空～～～");
            return;
        }
        //同样需要定义临时变量存储head的位置，此处因为不需要显示头节点的信息，所以直接取head.next
        HeroNode temp = head.next;
        while (true) {
            //判断当前temp是否是链表末尾
            if (temp == null) {
                break;
            }
            //打印节点的信息
            System.out.println(temp);
            //将temp后移一位
            temp = temp.next;
        }
    }
}

/**
 * 定义HeroNode，每一个HeroNode就是一个节点
 */
class HeroNode {
    /**
     * 英雄编号
     */
    public int no;
    /**
     * 英雄名字
     */
    public String name;
    /**
     * 英雄绰号
     */
    public String nickname;
    /**
     * next指向下一个节点
     */
    public HeroNode next;


    public HeroNode(int no, String name, String nickname) {
        this.no = no;
        this.name = name;
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
