package com.zzx.recursion;

/**
 * @Author zzx
 * @Date 2020-06-06 12:01
 */
public class MiGong {
    public static void main(String[] args) {
        //定义一个二维数组当作迷宫的地图
        int[][] map = new int[8][7];
        //用1表示墙
        //将地图的上下全部置为1
        for (int i = 0; i < 7; i++) {
            map[0][i] = 1;
            map[7][i] = 1;
        }
        //将地图的左右全部置为1
        for (int i = 0; i < 8; i++) {
            map[i][0] = 1;
            map[i][6] = 1;
        }

        //设置挡板, 1 表示
        map[3][1] = 1;
        map[3][2] = 1;

        //输出地图的情况
        System.out.println("地图的情况～～～");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }

        findWay(map,1,1);

        //输出小球走过后地图的情况地图的情况
        System.out.println();
        System.out.println("小球走过后地图的情况～～～");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                System.out.print(map[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 使用递归来让小球走出迷宫
     * 1. map 表示地图
     * 2. i,j 表示从地图的哪个位置开始出发 (1,1)
     * 3. 如果小球能到 map[6][5] 位置，则说明通路找到.
     * 4. 约定： 当 map[i][j] 为 0 表示该点没有走过 当为 1 表示墙 ； 2 表示通路可以走 ； 3 表示该点已经 走过，但是走不通
     * 5. 在走迷宫时，需要确定一个策略(方法) 下->右->上->左 , 如果该点走不通，再回溯
     *
     * @param map 迷宫地图
     * @param i   小球开始的横坐标
     * @param j   小球开始的纵坐标
     * @return 如果此路通顺则返回true，否则返回false
     */
    public static boolean findWay(int[][] map, int i, int j) {
        if (map[6][5] == 2) {
            //如果已经到达了map[6][5]位置，说明通路已经找到
            return true;
        } else {
            if (map[i][j] == 0) {
                //如果该点还未走过，直接假设该点可以走通，将其置为2
                map[i][j] = 2;
                //开始按照最先设定的策略按照下->右->上->左的顺序走迷宫
                if (findWay(map, i + 1, j)) {
                    return true;
                } else if (findWay(map, i, j + 1)) {
                    return true;
                } else if (findWay(map, i - 1, j)) {
                    return true;
                } else if (findWay(map, i, j - 1)) {
                    return true;
                }else {
                    //如果上下左右都无法走通，说明此时是死路，置为3，并且返回false
                    map[i][j] = 3;
                    return false;
                }
            }else {
                //map[i][j]!=0的情况，直接返回false
                return false;
            }
        }
    }
}
