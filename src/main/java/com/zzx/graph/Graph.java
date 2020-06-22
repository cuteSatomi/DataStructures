package com.zzx.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author zzx
 * @Date 2020-06-21 11:10
 */
public class Graph {

    /**
     * 存放图的定点的list集合
     */
    private List<String> vertexList;

    /**
     * 图对应的邻结矩阵
     */
    private int[][] edges;

    /**
     * 表示边的数目
     */
    private int numOfEdges;

    /**
     * 存储当前顶点是否已经被遍历过
     */
    private boolean[] isVisited;

    public static void main(String[] args) {
        //初始化5个顶点
        String[] vertexes = {"A", "B", "C", "D", "E"};
        Graph graph = new Graph(5);
        //将5个顶点加入到集合中
        for (String vertex : vertexes) {
            graph.insertVertex(vertex);
        }
        //添加边，相连的顶点如下
        //A-B A-C B-C B-D B-E
        graph.insertEdge(0, 1, 1);
        graph.insertEdge(0, 2, 1);
        graph.insertEdge(1, 2, 1);
        graph.insertEdge(1, 3, 1);
        graph.insertEdge(1, 4, 1);

        //显示图
        graph.showGraph();
        graph.bfs(0);
    }

    /**
     * 得到当前索引所表示顶点的第一个相邻的顶点
     * 矩阵如下所示
     * A  B  C  D  E
     * A [0, 1, 1, 0, 0]
     * B [1, 0, 1, 1, 1]
     * C [1, 1, 0, 0, 0]
     * D [0, 1, 0, 0, 0]
     * E [0, 1, 0, 0, 0]
     * 如上所示，假如查找顶点A的第一个相邻顶点，此时A的横坐标即rowIndex = 0
     * 此时即对矩阵的第一行进行遍历，找到第一个值为1的纵坐标，该纵坐标即为B colIndex = 1
     *
     * @param rowIndex 当前顶点的索引
     * @return 存在则返回第一个相邻顶点的索引，不存在则返回-1
     */
    public int getFirstNeighbor(int rowIndex) {
        for (int colIndex = 0; colIndex < vertexList.size(); colIndex++) {
            if (edges[rowIndex][colIndex] == 1) {
                return colIndex;
            }
        }
        return -1;
    }

    /**
     * 返回当前顶点的下一相邻顶点
     *
     * @param rowIndex 当前顶点的横坐标
     * @param colIndex 当前顶点的纵坐标
     * @return 存在相邻节点则返回它的纵坐标，不存在则返回-1
     */
    public int getNextNeighbor(int rowIndex, int colIndex) {
        for (int col = colIndex + 1; col < vertexList.size(); col++) {
            //循环从当前顶点的下一个顶点开始，按照存储顺序，即{A,B,C,D,E}
            if (edges[rowIndex][col] == 1) {
                return col;
            }
        }
        return -1;
    }

    /**
     * 深度优先遍历
     *
     * @param index 开始遍历的顶点的索引
     */
    public void dfs(int index) {
        if (isVisited[index]) {
            //如果当前顶点已经被遍历过，则return
            return;
        }
        //没有被遍历过，则输出该顶点，并且将isVisited中该顶点对应的位置置为true
        System.out.print(vertexList.get(index) + "->");
        isVisited[index] = true;

        //得到当前节点的第一个相邻节点的纵坐标
        int w = getFirstNeighbor(index);
        //只要w存在就一直循环
        while (w != -1) {
            //当w没有被遍历过，则继续递归
            if (!isVisited[w]) {
                dfs(w);
            }
            //如果w已经被遍历过了，则寻找w的下一个相邻顶点
            w = getNextNeighbor(index, w);
        }
    }

    /**
     * 广度优先遍历
     *
     * @param index 开始遍历的顶点的索引
     */
    public void bfs(int index) {
        //初始化一个队列，因为广度优先遍历需要用到
        LinkedList<Integer> queue = new LinkedList<>();
        //假设遍历从第一个顶点开始，即A开始，首先输出A
        System.out.print(getVertexByIndex(index) + "=>");
        //将A对应的索引存入队列
        queue.addLast(index);
        //将对应的布尔值置为true
        isVisited[index] = true;

        //只要队列不为空，则循环一直执行
        while (!queue.isEmpty()) {
            //将队列中的第一个顶点对应的索引出队列
            int u = queue.removeFirst();
            //根据这个顶点得到第一个相连顶点
            int w = getFirstNeighbor(u);
            //只要w存在则循环一直执行
            while (w != -1) {
                if (!isVisited[w]) {
                    //遍历当前顶点
                    System.out.print(getVertexByIndex(w) + "=>");
                    //入队列
                    queue.addLast(w);
                    //对应的不二值置为true
                    isVisited[w] = true;
                }
                //如果已经遍历过了，则找到下一个相连的节点
                w = getNextNeighbor(u, w);
            }
        }

    }

    /**
     * 构造方法
     *
     * @param n 图的顶点个数
     */
    public Graph(int n) {
        //初始化arrayList集合
        vertexList = new ArrayList<String>(n);
        //初始化矩阵
        edges = new int[n][n];
        //初始化边的数目
        numOfEdges = 0;
        //初始化每个顶点是否遍历的boolean数组
        isVisited = new boolean[n];
    }

    /**
     * 插入图的顶点到list集合中
     *
     * @param vertex 图的顶点
     */
    public void insertVertex(String vertex) {
        vertexList.add(vertex);
    }

    /**
     * 在矩阵中表示相邻的边
     *
     * @param v1     代表顶点的下标，比如"A"和"B"相邻，"A"->0;"B"->1，则edges[0][1] = 1；edges[1][0] = 1
     * @param v2     另一个顶点的下标
     * @param weight 边的权值，1代表两个顶点相邻，0则代表不相邻
     */
    public void insertEdge(int v1, int v2, int weight) {
        edges[v1][v2] = weight;
        edges[v2][v1] = weight;
        numOfEdges++;
    }

    /**
     * 得到图中顶点的个数
     *
     * @return 顶点的个数
     */
    public int getNumOfVertex() {
        return vertexList.size();
    }

    /**
     * 得到图中边的总条数
     *
     * @return 边的条数
     */
    public int getNumOfEdges() {
        return numOfEdges;
    }

    /**
     * 根据顶点的下标得到顶点的名称
     *
     * @param index 顶点的下标
     * @return 顶点的名称
     */
    public String getVertexByIndex(int index) {
        return vertexList.get(index);
    }

    /**
     * 得到图中相应两个顶点的之间的权重，即两个顶点是否相连
     *
     * @param v1 其中一个顶点的下标
     * @param v2 另一个顶点的下标
     * @return 返回1则说明相邻，0则说明不相邻
     */
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    /**
     * 显示图，即打印矩阵
     */
    public void showGraph() {
        for (int[] edge : edges) {
            System.out.println(Arrays.toString(edge));
        }
    }
}
