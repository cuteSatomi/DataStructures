package com.zzx.tree.threadedbinarytree;

/**
 * @Author zzx
 * @Date 2020-06-14 16:48
 */
public class ThreadedBinaryTreeDemo {
    public static void main(String[] args) {
        HeroNode root = new HeroNode(1, "zzx");
        HeroNode hero1 = new HeroNode(2, "godv");
        HeroNode hero2 = new HeroNode(5, "uzi");
        HeroNode hero3 = new HeroNode(6, "huni");
        HeroNode hero4 = new HeroNode(8, "zoom");
        HeroNode hero5 = new HeroNode(9, "forever");

        //此处先手动创建
        root.setLeft(hero1);
        root.setRight(hero2);
        hero1.setLeft(hero3);
        hero1.setRight(hero4);
        hero2.setLeft(hero5);

        ThreadedBinaryTree threadedBinaryTree = new ThreadedBinaryTree();
        threadedBinaryTree.setRoot(root);
        //threadedBinaryTree.threadedNodesInfix(root);
        //threadedBinaryTree.threadedList();

        //测试，以8号节点为例，看它的前驱节点和后继节点是不是分别为2和1
        //System.out.println(hero4.getLeft());
        //System.out.println(hero4.getRight());

        threadedBinaryTree.threadedNodesPost(root);
        threadedBinaryTree.threadedListPost();
    }
}

/**
 * 定义ThreadedBinaryTree，实现线索化功能的二叉树
 */
class ThreadedBinaryTree {
    private HeroNode root;
    /**
     * 在递归进行线索化时，pre 总是保留前一个结点
     */
    private HeroNode pre = null;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    /**
     * 编写对二叉树进行中序线索化的方法
     *
     * @param node 当前需要线索化的节点
     */
    public void threadedNodesInfix(HeroNode node) {
        //如果node等于null，则无法线索化
        if (node == null) {
            return;
        }

        /*
            线索化当前节点
            假设有这样一棵完全二叉树，如下所示，对其进行中序遍历的结果是 6->2->8->1->9->5
                                                 前序遍历的结果是 1->2->6->8->5->9
                                                 后序遍历的结果是 6->8->2->9->5->1

                        1⃣
                    /      \
                   2⃣        5⃣
                /   \     /
              6⃣      8⃣  9⃣

              其实第一次会对最左侧的叶子节点即6号节点进行初始化，此时从中序遍历的结果来看，6号节点没有前驱节点，它的后继节点为2号节点
              因此只需要设置其left为null，再将其leftType置为1即可，
              而第一次线索化，node指针就是指向6号指针本身的，而且此时pre指针还是没有为其赋值的，因此pre==null
              索引为6号节点的right赋值得要等到下一次递归才能做，即当node指针指向2号节点时，此时的pre节点指向2号指针的前驱节点即6号节点
              此时只需要将pre的right置为node即可
         */
        //向左子树进行递归线索化
        threadedNodesInfix(node.getLeft());

        //处理前驱节点
        if (node.getLeft() == null) {
            //如果当前节点没有左子节点，则将其左子指针指向前驱节点即pre
            node.setLeft(pre);
            //再将当前节点的leftType置为1
            node.setLeftType(1);
        }

        //处理后继节点
        if (pre != null && pre.getRight() == null) {
            //如果上一节点的右子节点为null，则将上一节点的后继节点指向当前节点
            pre.setRight(node);
            //将上一节点的rightType置为1
            pre.setRightType(1);
        }

        //处理完当前节点时，将pre置为当前节点
        pre = node;

        //向右子树进行递归线索化
        threadedNodesInfix(node.getRight());
    }

    /**
     * 遍历线索化二叉树
     */
    public void threadedListInfix() {
        //定义辅助指针记录当前节点，从root开始向下判断
        HeroNode curNode = root;
        while (curNode != null) {
            //直到找到leftType==1则退出循环
            while (curNode.getLeftType() == 0) {
                curNode = curNode.getLeft();
            }
            //退出上述循环则说明curNode已经指向了中序遍历的第一个节点，即6号节点
            //首先输出当前节点
            System.out.println(curNode);

            //只要当前节点的rightType==1，则一直向右遍历并且输出
            while (curNode.getRightType() == 1) {
                //将当前节点指向其右线索节点
                curNode = curNode.getRight();
                //输出当前节点
                System.out.println(curNode);
            }

            //如果当前节点的rightType==0，则将其指向它的右子节点
            curNode = curNode.getRight();
        }
    }

    /**
     * 前序线索化二叉树
     *
     * @param node 需要线索化的节点
     */
    public void threadedNodesPre(HeroNode node) {
        //如果当前节点为null，则无需线索化并且返回
        if (node == null) {
            return;
        }

        //处理前驱节点
        //如果当前节点的左子节点为null，则将前一个节点设置为当前节点的左子节点，即前一个节点是当前节点的前驱节点
        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }

        //处理后继节点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }

        //将pre置为当前节点
        pre = node;

    /*
        此处需要判断的原因如下
        假设此时处理到了6号节点，那么此时的pre=2，而经过判断6.left==null，所以设置6号节点的左子节点即前驱节点为2，即6.left=2
        此时代码继续向下运行，pre此时指向2号节点，而2.right!=null，所以代码继续向下运行，此时会将pre置为当前节点，即pre=6
        如果代码执行到这里没有对其进行判断，那么此处的递归是这样的threadedNodesPre(2)，
        而2.left!=null，代码向下运行，因为pre=6，6.right==null，所以给6号节点的右子节点进行赋值，即6.right=2，
        此时代码继续向下运行，pre=2，此时代码再走到这里进行递归，threadedNodesPre(6);
        此时就会造成死循环，栈溢出
        所以此处加一个判断，只有当前节点的左子节点不是前驱节点的时候，即当前节点指向的是自己真正的子树的时候，才让其进行递归
     */
        if (node.getLeftType() == 0) {
            threadedNodesPre(node.getLeft());
        }
        if (node.getRightType() == 0) {
            threadedNodesPre(node.getRight());
        }
    }

    /**
     * 前序线索化二叉树的遍历
     */
    public void threadedListPre() {
        //定义一个辅助指针，首先指向root
        HeroNode curNode = root;
        //只要curNode不等于空就让它一直遍历
        while (curNode != null) {
            //直到找到某个节点的左子节点是他的前驱节点为止
            while (curNode.getLeftType() == 0) {
                System.out.println(curNode);
                curNode = curNode.getLeft();
            }

            //先打印出当前节点，当前节点的左子节点是他的前驱节点，即前序线索化之前，当前节点的左子节点为null
            System.out.println(curNode);
        /*
            直接将当前节点指向当前节点的right，因为是前序遍历，遍历的顺序是 父节点->左子节点->右子节点
            因为当前节点的左子节点是前驱节点，可以不用理会，而此时的右子节点不论是指向后继节点还是指向真正的子树
            对遍历的结果都没有影响，所以只需要将当前节点指向它的right即可
         */

            curNode = curNode.getRight();
        }
    }

    /**
     * 后序线索化二叉树
     *
     * @param node 需要序列化的节点
     */
    public void threadedNodesPost(HeroNode node) {
        //如果当前节点为null，则无需线索化并且返回
        if (node == null) {
            return;
        }

        if (node.getLeftType() == 0) {
            threadedNodesPost(node.getLeft());
        }
        if (node.getLeftType() == 0) {
            threadedNodesPost(node.getRight());
        }

        if (node.getLeft() == null) {
            node.setLeft(pre);
            node.setLeftType(1);
        }

        //处理后继节点
        if (pre != null && pre.getRight() == null) {
            pre.setRight(node);
            pre.setRightType(1);
        }

        pre = node;
    }

    /**
     * 后序线索化二叉树的遍历
     */
    public void threadedListPost() {
        HeroNode curNode = root;
        while (curNode != null) {
            while (curNode.getLeftType() == 0) {
                curNode = curNode.getLeft();
            }
            while (curNode.getRightType() == 1) {
                System.out.println(curNode);
                curNode = curNode.getRight();
            }
            /*
                我此时的想法是
                当代码走到这里的时候说明当前节点的所有子节点已经遍历完毕，由于是后序遍历，所以接下来的遍历目标就是
                当前节点的父节点的右子节点，即当前节点的兄弟节点(假如存在的话)，所以需要将指针指向当前节点的兄弟节点
                然后再在兄弟节点中找到最左侧的叶子节点
                这是一个没有解决的点，存储的时候没有考虑父亲节点，还有一个点就是没有考虑到循环退出的条件，我目前分析
                当当前节点指向root的时候，循环就应该退出了

                下面的代码是山寨的
             */
            System.out.println(curNode);

            //如果当前节点等于root节点则退出
            if(curNode==root){
                return;
            }
            //由于前面没有定义父节点，因此这里我就按照我的思路将其设置为父亲节点的右子节点
            //如果当前节点不等于root节点，则将其设置为当前节点的兄弟节点
            curNode = root.getRight();
        }
    }
}

class HeroNode {
    /**
     * 当前节点的编号，即英雄编号
     */
    private int no;
    /**
     * 英雄的名字
     */
    private String name;
    /**
     * 当前节点的左节点
     */
    private HeroNode left;
    /**
     * 当前节点的右节点
     */
    private HeroNode right;

    /**
     * 如果 leftType == 0 表示指向的是左子树, 如果 1 则表示指向前驱结点
     */
    private int leftType;

    /**
     * 如果 rightType == 0 表示指向是右子树, 如果 1 表示指向后继结点
     */
    private int rightType;

    public int getLeftType() {
        return leftType;
    }

    public void setLeftType(int leftType) {
        this.leftType = leftType;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public HeroNode(int no, String name) {
        this.no = no;
        this.name = name;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HeroNode getLeft() {
        return left;
    }

    public void setLeft(HeroNode left) {
        this.left = left;
    }

    public HeroNode getRight() {
        return right;
    }

    public void setRight(HeroNode right) {
        this.right = right;
    }

    @Override
    public String toString() {
        return "HeroNode{" +
                "no=" + no +
                ", name='" + name + '\'' +
                '}';
    }
}