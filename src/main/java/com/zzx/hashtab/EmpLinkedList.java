package com.zzx.hashtab;

/**
 * 该类表示雇员链表
 *
 * @Author zzx
 * @Date 2020-06-11 21:27
 */
public class EmpLinkedList {
    /**
     * 雇员链表存储的都是雇员
     * 此处不像之气的链表，head不为null，head存放该链表的第一个雇员信息
     * head的初始值为null
     */
    private Emp head;

    /**
     * 添加雇员到链表中
     *
     * @param emp 要添加的雇员
     */
    public void add(Emp emp) {
        //如果当前链表还没有数据，则直接将emp保存到head上，然后return返回即可
        if (head == null) {
            head = emp;
            return;
        }
        //如果该链表中已经存有雇员信息了，则将要新增的雇员添加到该链表的末尾
        //此处定义一个辅助变量首先指向head
        Emp curEmp = head;
        while (curEmp.next != null) {
            curEmp = curEmp.next;
        }
        //退出上面的循环则说明curEmp.next == null，即说明已经到了链表的末尾，curEmp就是链表的额最后一个节点
        //所以只需要将emp添加到curEmp.next即可
        curEmp.next = emp;
    }

    /**
     * 遍历链表中的雇员信息
     *
     * @param no 传入链表的编号方便遍历
     */
    public void list(int no) {
        //如果头节点为null，则说明该链表是没有任何数据的，直接返回即可
        if (head == null) {
            System.out.println("第" + (no + 1) + "条链表为空");
            return;
        }
        //遍历链表同样需要一个辅助遍历来帮助判断是否已经到了链表的末尾
        Emp curEmp = head;
        System.out.print("第 " + (no + 1) + " 条链表的信息为");
        while (curEmp.next != null) {
            System.out.printf("=>id=%d,name=%s\t", curEmp.id, curEmp.name);
            curEmp = curEmp.next;
        }
        //这里假设一个链表中只有head节点有数据，所以curEmp.next==null就会退出上述while循环，这样就不会打印出head的雇员信息，所以这里需要将curEmp的数据打印出来
        System.out.printf("=>id=%d,name=%s\t", curEmp.id, curEmp.name);
        //每打印完一条链表的所有数据后，则换行
        System.out.println();
    }

    /**
     * 在哈希表中根据id查找雇员
     *
     * @param id 要查找的id
     * @return 查找到了就返回emp，不存在该id对应的雇员则返回null
     */
    public Emp findEmpById(int id) {
        //如果该id对应的链表为空，则直接返回null
        if (head == null) {
            System.out.println("该id对应的链表为空~~~");
            return null;
        }
        //查找需要遍历链表，同样需要辅助指针
        Emp curEmp = head;
        while (true) {
            //如果当前雇员的id等于要查找的id，则说明找到，直接break退出循环
            if (curEmp.id == id) {
                break;
            }
            //如果查找到链表的末尾还没有找到，则直接返回null
            if (curEmp.next == null) {
                return null;
            }
            //让curEmp后移一位
            curEmp = curEmp.next;
        }
        //最后返回curEmp
        return curEmp;
    }

    /**
     * 根据id删除链表中的雇员信息
     *
     * @param id 要删除的雇员的id
     */
    public void del(int id) {
        //如果该条链表为空，则给出提示信息并直接返回
        if (head == null) {
            System.out.println("不存在id为 " + id + " 的雇员");
            return;
        }
        //想要删除雇员也需要遍历链表，所以同样需要一个辅助节点
        Emp curEmp = head;
        //定义一个辅助节点，指向当前节点的前一个节点，如果当前是第一个节点，则辅助节点为null
        Emp preEmp = null;
        //定义一个标记来判断是否找到该id对应的雇员
        boolean flag = false;
        while (true) {
            //遍历到了链表的末尾则break退出
            if (curEmp == null) {
                break;
            }
            //当前节点的下一节点的id与所找id相等，则说明找到，把flag置为true，并且break退出
            if (curEmp.id == id) {
                flag = true;
                break;
            }
            //使用preEmp来记录当前的节点位置
            preEmp = curEmp;
            //将curEmp后移一位
            curEmp = curEmp.next;
        }

        if (flag) {
            /*
                flag为true，说明找到，此时分两种情况来考虑
                    1。找到的雇员是链表的头节点
                    2。找到的雇员不是链表的头节点
             */
            if (curEmp == head) {
                /*
                    如果找到的雇员是当前链表的头节点，此时又得分两种情况来考虑
                        1。该链表的长度为1，即找到的节点就是该链表唯一的节点
                        2。该链表的长度不为1
                 */
                if (curEmp.next == null) {
                    //如果当前节点的下一节点为null，而当前节点又为头节点，说明该链表的长度为1
                    //所以此时只需要将head置为null即可
                    head = null;
                } else {
                    //如果当前链表的长度大于1，则只需要将head节点的next指向curEmp的next即可
                    head = curEmp.next;
                }
            }else {
                //如果找到的雇员不是头节点，则说明该链表长度一定大于1，所以无需考虑空指针问题，直接将当前节点的上一节点的next指向当前节点的下一节点即可
                preEmp.next = curEmp.next;
            }
            System.out.println("id为" + id + "的雇员已成功删除");
        } else {
            System.out.println("不存在id为 " + id + " 的雇员");
        }
    }
}

