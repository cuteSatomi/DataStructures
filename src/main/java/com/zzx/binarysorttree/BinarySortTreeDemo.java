package com.zzx.binarysorttree;

/**
 * @Author zzx
 * @Date 2020-06-18 19:22
 */
public class BinarySortTreeDemo {
    public static void main(String[] args) {
        int[] arr = {7, 3, 10, 12, 5, 1, 9, 2};
        BinarySortTree binarySortTree = new BinarySortTree();
        for (int value : arr) {
            binarySortTree.add(new Node(value));
        }
        System.out.println("中序遍历二叉排序树~~~");
        binarySortTree.infixOrder();
        binarySortTree.delNode(10);
        System.out.println("删除节点后中序遍历二叉排序树~~~");
        binarySortTree.infixOrder();
    }
}

/**
 * 定义二叉排序树类
 */
class BinarySortTree {
    /**
     * 二叉排序树的根节点
     */
    private Node root;

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
