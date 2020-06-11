package com.zzx.hashtab;

/**
 * 创建HashTab，其实所谓的哈希表，就是数组加上链表，此处的HashTab其实就是包裹在最外层的数组
 * 只不过该数组存放的都是链表，即EmpLinkedList，所以首先得在这个类中初始化一个存放数据类型时EmpLinkedList的数组，数组中存放的是各个链表的表头
 *
 * @Author zzx
 * @Date 2020-06-11 21:28
 */
public class HashTab {
    /**
     * 存放链表的数组
     */
    private EmpLinkedList[] empLinkedListArr;

    /**
     * 该数组中有多少条链表
     */
    private int size;

    public HashTab(int size) {
        this.size = size;
        //在构造函数中初始化empLinkedListArr，但是此时只是给该数组做了一个初始化
        empLinkedListArr = new EmpLinkedList[size];
        //此处需要对数组中的所有元素都进行一个初始化，防止空指针异常
        for (int i = 0; i < size; i++) {
            empLinkedListArr[i] = new EmpLinkedList();
        }
    }

    /**
     * 添加雇员的方法，其实此处调用的还是EmpLinkedList中的add方法
     *
     * @param emp 要添加的雇员
     */
    public void add(Emp emp) {
        //根据定义的取模函数得到应该把该雇员加到哪条链表中
        int empLinkedListNO = hashFun(emp.id);
        empLinkedListArr[empLinkedListNO].add(emp);
    }

    /**
     * 遍历哈希表中的所有雇员信息
     */
    public void list() {
        for (int i = 0; i < size; i++) {
            empLinkedListArr[i].list(i);
        }
    }

    /**
     * 根据id查找雇员
     *
     * @param id 所给的id
     */
    public void findEmpById(int id) {
        //根据定义的取模函数求得应该去哪条链表中查找该雇员
        int empLinkedListNO = hashFun(id);
        Emp emp = empLinkedListArr[empLinkedListNO].findEmpById(id);
        if (emp == null) {
            //如果返回的emp为null，则说明不存在该id对应的雇员信息
            System.out.println("不存在id为 " + id + " 的雇员");
        } else {
            //找到则输出该雇员信息
            System.out.printf("在第%d 条链表中找到 雇员 id=%d\n", (empLinkedListNO + 1), id);
        }
    }

    /**
     * 根据id删除雇员
     *
     * @param id 雇员的id
     */
    public void del(int id) {
        int empLinkedListNO = hashFun(id);
        empLinkedListArr[empLinkedListNO].del(id);
    }

    /**
     * 编写散列函数, 使用一个简单取模法，根据所给id返回相应的余数
     *
     * @param id 给出的id值
     * @return 余数
     */
    public int hashFun(int id) {
        return id % size;
    }

}
