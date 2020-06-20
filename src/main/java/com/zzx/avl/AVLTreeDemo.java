package com.zzx.avl;

/**
 * @Author zzx
 * @Date 2020-06-20 13:32
 */
public class AVLTreeDemo {
    public static void main(String[] args) {
        int[] arr = {10, 11, 7, 6, 8, 9};
        AVLTree avlTree = new AVLTree();
        for (int value : arr) {
            avlTree.add(new Node(value));
        }
        avlTree.infixOrder();
        Node root = avlTree.getRoot();
        System.out.println("这棵树的高度是：" + root.getHeight());
        System.out.println("左子树的高度是：" + root.getLeftHeight());
        System.out.println("右子树的高度是：" + root.getRightHeight());
    }
}

/**
 * 这里的node和tree类基本全部是从二叉排序树那里复制过来的，因为代码大体相同
 */
class AVLTree {
    /**
     * 二叉排序树的根节点
     */
    private Node root;

    public Node getRoot() {
        return root;
    }

    /**
     * 查找需要删除的节点
     *
     * @param value 需要删除节点的value
     * @return 没找到则返回null
     */
    public Node search(int value) {
        if (root == null) {
            return null;
        } else {
            return root.search(value);
        }
    }

    /**
     * 查找需要删除的节点的父节点
     *
     * @param value 需要删除节点的value
     * @return 没找到则返回null
     */
    public Node searchParent(int value) {
        if (root == null) {
            return null;
        } else {
            return root.searchParent(value);
        }
    }

    //编写方法:
    // 1. 返回的 以 node 为根结点的二叉排序树的最小结点的值
    // 2. 删除 node 为根结点的二叉排序树的最小结点

    /**
     * @param node 传入的结点(当做二叉排序树的根结点)
     * @return 返回的以node 为根结点的二叉排序树的最小结点的值
     */
    public int delRightTreeMin(Node node) {
        Node target = node;
        //循环的查找左子节点，就会找到最小值
        while (target.left != null) {
            target = target.left;
        }
        //这时 target 就指向了最小结点
        // 删除最小结点
        delNode(target.value);
        return target.value;
    }

    /**
     * 删除value对应的节点
     *
     * @param value 需要删除节点的value
     */
    public void delNode(int value) {
        //首先进行判断，如果二叉树为空则直接返回
        if (root == null) {
            return;
        }
        //查询得到需要删除的节点
        Node targetNode = root.search(value);
        if (targetNode == null) {
            //如果没有查询到要删除的节点，则直接返回
            return;
        }
        if (root.left == null && root.right == null) {
            //如果代码走到这里，说明targetNode不为null，而且此时root的左右子节点均为空，则说明该树中只有root一个节点，而且root节点就是需要删除的节点
            //直接将root置为null并且返回
            root = null;
            return;
        }
        //得到要删除的节点的父节点
        Node parentNode = root.searchParent(value);
        //第一种情况：要删除的节点是叶子节点
        if (targetNode.left == null && targetNode.right == null) {
            //如果要删除的节点的左右子节点均为null，则说明要删除的节点是一个叶子节点
            //再判断要删除的该叶子节点是它的父节点的左子节点还是右子节点
            if (parentNode.left != null && parentNode.left == targetNode) {
                parentNode.left = null;
            } else if (parentNode.right != null && parentNode.right == targetNode) {
                parentNode.right = null;
            }
        } else if (targetNode.left != null && targetNode.right != null) {
            //第二种情况：要删除的节点有两个子节点
            //找到要删除的节点的右子树中的最小节点
            int minVal = delRightTreeMin(targetNode.right);
            //将该最小节点的value值直接赋值给要删除的节点的value，在delRightTreeMin方法中已经删除了最小值
            targetNode.value = minVal;
        } else {
            //余下来的就是第三种情况了：要删除的节点只有一个子节点
            if (targetNode.left != null) {
                //此处对父节点进行判断是针对删除的是root节点，而root节点又只有一个子节点的情况
                if (parentNode != null) {
                    //如果当前节点的左子节点不为null，则说明它只有左子节点，此时还需判断要删除的节点是它的父节点的左还是右子节点
                    if (parentNode.left.value == value) {
                        //说明要删除的节点是它父节点的左子节点,所以此时直接将它父节点的左子节点直接指向它的右子节点
                        parentNode.left = targetNode.left;
                    } else {
                        //要删除的节点是它父节点的右子节点
                        parentNode.right = targetNode.left;
                    }
                } else {
                    //如果父节点为空，则说明此时的targetNode就是root，此时只需要将root指向它的左子节点即可
                    root = targetNode.left;
                }
            } else {
                if (parentNode != null) {
                    //左子节点为null，则说明只存在右子节点，此时同样需要判断要删除节点与其父节点的位置关系
                    if (parentNode.left.value == value) {
                        //要删除的节点是它父节点的左子节点
                        parentNode.left = targetNode.right;
                    } else {
                        //要删除的节点是它父节点的右子节点
                        parentNode.right = targetNode.right;
                    }
                } else {
                    root = targetNode.right;
                }
            }
        }
    }

    /**
     * 向排序二叉树中添加节点
     *
     * @param node 要加入的节点
     */
    public void add(Node node) {
        //首先对二叉树进行判断，如果root为空，则将root直接指向node
        if (root == null) {
            root = node;
        } else {
            //root不为空，则按照节点类中的添加规则来新增节点
            root.add(node);
        }
    }

    /**
     * 排序二叉树的前序遍历
     */
    public void infixOrder() {
        if (root == null) {
            System.out.println("当前排序二叉树为空，无法遍历~~~");
            return;
        }
        root.infixOrder();
    }
}

/**
 * 定义节点类
 */
class Node {
    int value;
    Node left;
    Node right;

    /**
     * 返回以当前节点为父节点的树的高度
     *
     * @return 树的高度
     */
    public int getHeight() {
        return Math.max(this.left == null ? 0 : this.left.getHeight(), this.right == null ? 0 : this.right.getHeight()) + 1;
    }

    /**
     * 返回当前节点左子树的高度
     *
     * @return 左子树的高度
     */
    public int getLeftHeight() {
        if (this.left == null) {
            //如果当前节点没有左子节点，则直接返回0
            return 0;
        }
        //否则返回左子树的高度
        return this.left.getHeight();
    }

    /**
     * 返回当前节点右子树的高度
     *
     * @return 右子树的高度
     */
    public int getRightHeight() {
        if (this.right == null) {
            //如果当前节点没有右子节点，则直接返回0
            return 0;
        }
        //否则返回右子树的高度
        return this.right.getHeight();
    }

    /**
     * 将当前二叉树左旋
     */
    public void leftRotate() {
        /*
            看到关于左旋的一张很形象的图，其实所谓的左旋，就是以根节点的右子节点为支点，将根节点逆时针旋转
            此时根节点的右子节点就成了新的根节点，而原本的根节点就成了新的根节点的左子节点
            原来的根节点的右子节点此时指向新的根节点的左子节点，此时左旋就完成了
         */

        //首先创建一个节点保存当前根节点的value值
        Node newNode = new Node(this.value);
        //新的节点的左子节点应该指向根节点的左子节点
        newNode.left = this.left;
        //新的节点的右子节点应该指向根节点的右子节点的左子节点
        newNode.right = this.right.left;
        //将根节点的value置为它的右子节点的value
        this.value = this.right.value;
        //将根节点的左子节点指向新的节点
        this.left = newNode;
        //将根节点的右子节点指向根节点的右子节点的右子节点
        this.right = this.right.right;
    }

    /**
     * 将当前二叉树右旋
     */
    public void rightRotate() {
        //首先创建一个新的节点保存当前根节点的value值
        Node newNode = new Node(this.value);
        //将新的节点的左子节点指向根节点的左子节点的右子节点
        newNode.left = this.left.right;
        //将新节点的右子节点指向根节点的右子节点
        newNode.right = this.right;
        //将根节点的value置为它的左子节点的value
        this.value = this.left.value;
        //将根节点的左子节点指向它的左子节点的左子节点
        this.left = this.left.left;
        //将根节点的右子节点指向新的节点
        this.right = newNode;
    }

    /**
     * 查找需要删除的节点
     *
     * @param value 要删除节点的value
     * @return 如果找到则返回，未找到则返回null
     */
    public Node search(int value) {
        //如果当前节点的value就等于要查找的value，说明已经找到了需要删除的节点，直接将当前节点返回
        if (this.value == value) {
            return this;
        } else if (this.value > value) {
            //如果当前节点的value大于需要查找的，则准备向当前节点的左子节点继续查找
            if (this.left == null) {
                //如果当前节点没有左子节点，则直接返回null
                return null;
            }
            //如果当前节点存在左子节点，则向左递归
            return this.left.search(value);
        } else {
            //如果当前节点的value不大于需要查找的，则准备向当前节点的右子节点继续查找
            if (this.right == null) {
                //如果当前节点没有右子节点，则直接返回null
                return null;
            }
            //向右递归
            return this.right.search(value);
        }
    }

    /**
     * 查找需要删除的节点的父节点
     *
     * @param value 需要删除节点的value
     * @return 找到了则返回相对应的node，没找到则返回null
     */
    public Node searchParent(int value) {
        if ((this.left != null && this.left.value == value)
                || (this.right != null && this.right.value == value)) {
            //如果当前节点有其中一个子节点的value与要查找的value相同，就说明找到了，返回this
            return this;
        } else if (this.left != null && value < this.value) {
            //如果当前节点的value大于要查找的value，则继续向左查找
            return this.left.searchParent(value);
        } else if (value >= this.value && this.right != null) {
            //向右子树递归查找
            return this.right.searchParent(value);
        } else {
            return null;
        }
    }

    /**
     * 添加节点的
     *
     * @param node 需要添加的节点
     */
    public void add(Node node) {
        //如果传入的node为null，则直接return
        if (node == null) {
            return;
        }

        if (node.value < this.value) {
            //如果传入的节点小于当前节点，而且当前节点的左子节点为null时直接将要加入的节点挂到当前节点的左子节点上
            if (this.left == null) {
                this.left = node;
            } else {
                //如果当前节点的左子节点不为null，则继续向左递归
                this.left.add(node);
            }
        } else {
            //如果传入的节点大于当前节点，而且当前节点的右子节点为null时直接将要加入的节点挂到当前节点的右子节点上
            if (this.right == null) {
                this.right = node;
            } else {
                //如果当前节点的左子节点不为null，则继续向左递归
                this.right.add(node);
            }
        }

        //添加完一个节点之后判断两边的子树高度的绝对值，看是否需要左旋
        if (this.getRightHeight() - this.getLeftHeight() > 1) {
            //如果它的右子树的左子树的高度大于它的右子树的右子树的高度
            if (this.right != null && this.right.getLeftHeight() > this.right.getRightHeight()) {
                //先对右子树进行右旋
                this.right.rightRotate();
            }
            //如果右子树的高度比左子树的高度高1以上，则旋转
            this.leftRotate();
            return;
        }
        if (this.getLeftHeight() - this.getRightHeight() > 1) {
            if (this.left != null && this.left.getRightHeight() > this.left.getLeftHeight()) {
                //先对右子树进行右旋
                this.left.leftRotate();
            }
            this.rightRotate();
        }
    }

    /**
     * 中序遍历
     */
    public void infixOrder() {
        if (this.left != null) {
            this.left.infixOrder();
        }
        System.out.println(this);
        if (this.right != null) {
            this.right.infixOrder();
        }
    }

    public Node(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Node{" +
                "value=" + value +
                '}';
    }
}
