package com.zzx.tree;

/**
 * @Author zzx
 * @Date 2020-06-13 16:48
 */
public class BinaryTreeDemo {
    public static void main(String[] args) {
        //先需要创建一颗二叉树
        BinaryTree binaryTree = new BinaryTree();
        //创建需要的结点
        HeroNode root = new HeroNode(1, "宋江");
        HeroNode node2 = new HeroNode(2, "吴用");
        HeroNode node3 = new HeroNode(3, "卢俊义");
        HeroNode node4 = new HeroNode(4, "林冲");
        HeroNode node5 = new HeroNode(5, "关胜");

        //先手动创建该二叉树
        root.setLeft(node2);
        root.setRight(node3);
        node3.setRight(node4);
        node3.setLeft(node5);
        binaryTree.setRoot(root);

        /*//测试
        System.out.println("前序遍历"); // 1,2,3,5,4
        binaryTree.preOrder();

        //测试
        System.out.println("中序遍历");
        binaryTree.infixOrder(); // 2,1,5,3,4

        //测试
        System.out.println("后序遍历");
        binaryTree.postOrder(); // 2,5,4,3,1*/

        HeroNode resNode = binaryTree.postOrderSearch(5);
        if (resNode != null) {
            System.out.printf("找到了，信息为 no=%d name=%s", resNode.getNo(), resNode.getName());
        } else {
            System.out.printf("没有找到 no = %d 的英雄", 5);
        }
    }
}

class BinaryTree {
    private HeroNode root;

    public void setRoot(HeroNode root) {
        this.root = root;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        if (this.root != null) {
            this.root.preOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.root != null) {
            this.root.infixOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        if (this.root != null) {
            this.root.postOrder();
        } else {
            System.out.println("二叉树为空，无法遍历");
        }
    }

    /**
     * 前序查找
     */
    public HeroNode preOrderSearch(int no) {
        if (this.root != null) {
            return this.root.preOrderSearch(no);
        } else {
            return null;
        }
    }

    /**
     * 中序查找
     */
    public HeroNode infixOrderSearch(int no) {
        if (this.root != null) {
            return this.root.infixOrderSearch(no);
        } else {
            return null;
        }
    }

    /**
     * 后序查找
     */
    public HeroNode postOrderSearch(int no) {
        if (this.root != null) {
            return this.root.postOrderSearch(no);
        } else {
            return null;
        }
    }

    /**
     * 删除节点
     *
     * @param no 要删除的节点的no
     */
    public void delNode(int no) {
        if (root != null) {
            //如果只有一个 root 结点, 这里立即判断 root 是不是就是要删除结点
            if (root.getNo() == no) {
                root = null;
            } else {
                //递归删除
                root.delNode(no);
            }
        } else {
            System.out.println("空树，不能删除～～～");
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

    /**
     * 前序遍历，即先输出父节点，再输出左节点，最后是右节点
     */
    public void preOrder() {
        //先输出父节点
        System.out.println(this);
        //判断是否有左节点，有则递归向左前序遍历
        if (this.left != null) {
            this.left.preOrder();
        }
        //同理，向右递归前序遍历
        if (this.right != null) {
            this.right.preOrder();
        }
    }

    /**
     * 中序遍历，即先输出左节点，再输出父节点，最后输出右节点
     */
    public void infixOrder() {
        //若左节点不为空，则向左递归中序遍历
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        //向右递归
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    /**
     * 后序遍历，即先输出左节点，再输出右节点，最后输出父节点
     */
    public void postOrder() {
        if (this.left != null) {
            this.left.postOrder();
        }
        if (this.right != null) {
            this.right.postOrder();
        }
        System.out.println(this);
    }

    /**
     * 前序查找，根据id查找
     *
     * @param no 需要查找的id
     * @return 如果找到则返回，没有找到则返回null
     */
    public HeroNode preOrderSearch(int no) {
        System.out.println("进入前序查找～～～");
        //首先判断当前节点的no是否和所需要查找的no相等
        if (this.no == no) {
            return this;
        }
        //如果当前节点的no和所需查找的no不想等，则判断当前节点是否存在左子节点，如果存在则向左递归查找
        //此处还需要定义一个HeroNode帮助判断是否找到
        HeroNode resNode = null;
        if (this.left != null) {
            //将结果左递归的结果赋值给resNode
            resNode = this.left.preOrderSearch(no);
        }
        //判断左递归过程中是否找到了需要查找的节点，如果找到则直接返回
        if (resNode != null) {
            return resNode;
        }
        //如果左递归过程中没有找到所需查找的节点，则先判断是否当前节点是否存在右子节点，再对其进行右递归查找
        if (this.right != null) {
            resNode = this.right.preOrderSearch(no);
        }
        //将最后的结果resNode返回即可
        return resNode;
    }

    /**
     * 中序查找
     *
     * @param no 需要查找的id
     * @return 找到则返回，否则返回null
     */
    public HeroNode infixOrderSearch(int no) {
        //首先判断当前节点是否存在左子节点，存在则进行左递归查询
        //同样定义一个resNode来接受结果
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.infixOrderSearch(no);
        }
        //如果左遍历过程中已经找到了需要查找的节点，则直接返回
        if (resNode != null) {
            return resNode;
        }
        System.out.println("进入中序查找～～～");
        //否则，判断当前节点的no是否等于要查找的no，等于则直接返回
        if (no == this.no) {
            return this;
        }
        //如果当前节点不是想要查找的节点，则判断当前节点是否存在右子节点，如果存在则向右递归
        if (this.right != null) {
            resNode = this.right.infixOrderSearch(no);
        }
        //最后直接返回结果
        return resNode;
    }

    /**
     * 后序查找
     *
     * @param no 需要查找的id
     * @return 找到则返回，否则返回null
     */
    public HeroNode postOrderSearch(int no) {
        //首先判断当前节点是否存在左子节点，存在则左递归
        HeroNode resNode = null;
        if (this.left != null) {
            resNode = this.left.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        //如果左遍历过程中没有找到，则判断是否可以进行右递归
        if (this.right != null) {
            resNode = this.right.postOrderSearch(no);
        }
        if (resNode != null) {
            return resNode;
        }
        System.out.println("进入后序查找～～～");
        //如果左右遍历都没有找到，则判断是否和当前节点的no相等
        if (this.no == no) {
            return this;
        }
        return null;
    }

    /**
     * 根据id删除相应的节点
     * 此处的策略是如果要删除的节点是叶子节点，则直接删除，如果要删除的节点是非叶子节点，则直接把该子树删除
     *
     * @param no
     */
    public void delNode(int no) {
        //只能判断左子节点或者右子节点是否是需要删除的节点，因为无法自身删除
        //首先判断当前节点的左子节点的no是否等于要查找的no
        if (this.left != null && this.left.no == no) {
            //如果找到，则直接将当前节点的左子节点置为null，然后return
            this.left = null;
            return;
        }
        //如果左子节点不是要找的那个节点，则判断是否是右子节点
        if (this.right != null && this.right.no == no) {
            //如果找到，则直接将当前节点的右子节点置为null，然后return
            this.right = null;
            return;
        }
        //如果当前节点的左右子节点都不是要找的那个节点，则分别向左和向右递归
        if (this.left != null) {
            this.left.delNode(no);
        }
        if (this.right != null) {
            this.right.delNode(no);
        }
    }
}
