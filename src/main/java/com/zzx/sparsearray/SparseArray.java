package com.zzx.sparsearray;


import java.io.*;

public class SparseArray {
    public static void main(String[] args) throws IOException {
        //创建一个原始的二维数组11 * 11
        //0： 表示没有棋子，1 表示黑子，2 表示蓝子
        int chessArr1[][] = new int[11][11];
        chessArr1[1][2] = 1;
        chessArr1[2][3] = 2;
        //输出原始的二维数组
        System.out.println("原始的二维数组是：");
        for (int[] rows : chessArr1) {
            for (int item : rows) {
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }

        //将二维数组转为稀疏数组
        //1。先遍历二维数组，得到所有非0数据个数
        int sum = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    sum++;
                }
            }
        }
        System.out.println("二维数组中非零数据个数为：" + sum);

        //2。得到二维数组非零元素个数之后就可以创建稀疏数组了
        int sparseArray[][] = new int[sum + 1][3];
        //给稀疏数组赋值
        sparseArray[0][0] = 11;
        sparseArray[0][1] = 11;
        sparseArray[0][2] = sum;
        //遍历二维数组，将非零数据放入稀疏数组中
        //定义count用于记录第几个非零数据
        int count = 0;
        for (int i = 0; i < chessArr1.length; i++) {
            for (int j = 0; j < chessArr1[i].length; j++) {
                if (chessArr1[i][j] != 0) {
                    count++;
                    sparseArray[count][0] = i;
                    sparseArray[count][1] = j;
                    sparseArray[count][2] = chessArr1[i][j];
                }
            }
        }
        //输出稀疏数组
        System.out.println();
        System.out.println("转换后的稀疏数组是：");
        for (int i = 0; i < sparseArray.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArray[i][0], sparseArray[i][1], sparseArray[i][2]);
        }
        System.out.println();
        //将稀疏数组存入磁盘中
        System.out.println("正在写入稀疏数组数据到磁盘>>>>>>>>>>>>>>>>>>>");
        FileWriter fw = new FileWriter(new File("/Users/hds0m3zzx/resource/others/notes/DataStructuresNotes/map.data"));
        for (int i = 0; i < sparseArray.length; i++) {
            for (int j = 0; j < sparseArray[i].length; j++) {
                fw.write(sparseArray[i][j] + "\t");
            }
            fw.write("\n");
        }
        fw.close();
        System.out.println("数据写入成功<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        System.out.println();

        /*//3。将稀疏数组恢复成原始的二维数组
        //根据稀疏数组第一行的数据创建二维数组
        int chessArr2[][] = new int[sparseArray[0][0]][sparseArray[0][1]];
        for (int i = 1; i < sparseArray.length; i++) {
            //读取非零数据存储的位置并且赋值给二维数组
            chessArr2[sparseArray[i][0]][sparseArray[i][1]] = sparseArray[i][2];
        }
        //输出恢复后的原始的二维数组
        System.out.println("恢复后的的二维数组是：");
        for (int[] rows : chessArr2) {
            for (int item : rows) {
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }*/

        //从磁盘读取map.data文件并且恢复原始二维数组
        System.out.println("正在从磁盘读取稀疏数组数据>>>>>>>>>>>>>>>>>>>");
        BufferedReader br = new BufferedReader(new FileReader("/Users/hds0m3zzx/resource/others/notes/DataStructuresNotes/map.data"));
        String line = null;
        int sparseArray2[][] = null;
        int row = 0;
        while ((line = br.readLine()) != null) {
            //先读取第一行数据来初始化稀疏数组
            String[] temp = line.split("\t");
            //得到第一行的最后一个数据，即整个二维数组的非零数据个数，从而确定稀疏数组的总行数
            int rows = Integer.parseInt(temp[temp.length - 1]);
            sparseArray2 = new int[rows + 1][3];
            for (int i = 0; i < temp.length; i++) {
                sparseArray2[row][i] = Integer.parseInt(temp[i]);
            }
            row++;
            break;
        }
        //读取文件中的数据来对稀疏数组进行赋值
        while ((line = br.readLine()) != null) {
            String[] temp = line.split("\t");
            for (int i = 0; i < temp.length; i++) {
                sparseArray2[row][i] = Integer.parseInt(temp[i]);
            }
            row++;
        }
        //读取完毕，关闭输入流
        System.out.println("数据读取成功<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        br.close();

        System.out.println();
        System.out.println("从文件读取的稀疏数组是：");
        for (int i = 0; i < sparseArray2.length; i++) {
            System.out.printf("%d\t%d\t%d\t\n", sparseArray2[i][0], sparseArray2[i][1], sparseArray2[i][2]);
        }

        //3。将稀疏数组恢复成原始的二维数组
        //根据稀疏数组第一行的数据创建二维数组
        int chessArr3[][] = new int[sparseArray2[0][0]][sparseArray2[0][1]];
        for (int i = 1; i < sparseArray2.length; i++) {
            //读取非零数据存储的位置并且赋值给二维数组
            chessArr3[sparseArray2[i][0]][sparseArray2[i][1]] = sparseArray2[i][2];
        }
        System.out.println();

        //输出恢复后的原始的二维数组
        System.out.println("恢复后的的二维数组是：");
        for (int[] rows : chessArr3) {
            for (int item : rows) {
                System.out.printf("%d\t", item);
            }
            System.out.println();
        }
    }
}
