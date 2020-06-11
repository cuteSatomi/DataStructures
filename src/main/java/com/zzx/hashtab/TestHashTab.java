package com.zzx.hashtab;

import java.util.Scanner;

/**
 * @Author zzx
 * @Date 2020-06-11 21:29
 */
public class TestHashTab {
    public static void main(String[] args) {
        //初始化一个长度为6的哈希表
        HashTab hashTab = new HashTab(6);

        //做一个菜单，根据用户的输入执行相应的操作
        String key = "";
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("add:  添加雇员");
            System.out.println("list: 显示雇员");
            System.out.println("find:  查找雇员");
            System.out.println("del:  删除雇员");
            System.out.println("exit: 退出系统");

            key = scanner.next();
            switch (key) {
                case "add":
                    System.out.println("请输入id:");
                    int id = scanner.nextInt();
                    System.out.println("请输入名字:");
                    String name = scanner.next();
                    hashTab.add(new Emp(id, name));
                    break;
                case "list":
                    hashTab.list();
                    break;
                case "find":
                    System.out.println("请输入要查找的id:");
                    id = scanner.nextInt();
                    hashTab.findEmpById(id);
                    break;
                case "del":
                    System.out.println("请输入要删除的id:");
                    id = scanner.nextInt();
                    hashTab.del(id);
                    break;
                case "exit":
                    scanner.close();
                    System.exit(0);
                default:
                    break;
            }
        }
    }
}

