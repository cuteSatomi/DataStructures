package com.zzx.recursion;

/**
 * @Author zzx
 * @Date 2020-06-06 15:23
 */
public class Queen8 {

    /**
     * 定义一个变量表示一共有8颗棋子
     */
    int max = 8;
    /**
     * 虽然棋盘应该是二维数组，但是其实此处只需要定义一个一位数组就足够了
     * 一位数组的索引代表棋子在棋盘的所在行，索引所对应的数值代表棋子在棋盘的所在列
     */
    int[] array = new int[max];
    static int count = 0;
    public static void main(String[] args) {
        Queen8 queen8 = new Queen8();
        queen8.check(0);
        System.out.println(count);
    }

    /**
     * 摆放第n个皇后
     *
     * @param n 第n个皇后
     */
    public void check(int n) {
        if (n == max) {
            //当n = 8的时候，其实此时已经放置结束了，因为数组的索引是从0开始的，即八个皇后的位置都已经摆放完成且没有冲突
            //打印数组中的数据，并且return返回
            count++;
            print();
            return;
        }

        //依次放入皇后，判断是否冲突
        for (int i = 0; i < max; i++) {
            //把第n个皇后从i=0即第一列开始尝试放置
            array[n] = i;
            //对于将要放置的第n个皇后进行判断，看是否和之前的皇后有冲突
            if (judge(n)) {
                //如果没有冲突，则放置下一个皇后，此时开始递归
                //如果judge()的结果是false，则不会进入该if语句，程序会回到40行的for循环，再把皇后放在后一个位置进行尝试，以此类推
                //假设array[2]=0不与前面的两个皇后冲突，此时会执行check(2+1)，假设此时对array[3]=i进行的for 循环最后judge()的判断结果全部为false
                //此时程序会回溯到check(2)，对42行的代码array[2]进行下一轮的赋值，即array[2]=1
                check(n + 1);
            }
        }
    }

    /**
     * 判断当放置第n个皇后时，是否与前面已经摆放的那些皇后有冲突，即是否在同一列或者统一斜线上
     * 这里不考虑是否在同一行，因为行记录对应这一维数组的索引，索引是一次递增的，所以不存在行冲突的问题
     *
     * @param n 当前要放置第n个皇后
     * @return 如果冲突返回false
     */
    public boolean judge(int n) {
        //由于只需要与第n个皇后之前已经放置的皇后做判断，所以此处i的条件只需满足i<n
        for (int i = 0; i < n; i++) {
            /*
            对这里的判断条件做简单说明，对于不需要判断行冲突已在上面作出说明
            1. array[i] == array[n]这个条件判断一维数组的值是否相等，即第i个皇后和第n个皇后是否在同一列
            2. Math.abs(n - i)其实表示索引差值的绝对值，由于数组中的索引代表棋盘的所在行，这个式子的结果是第n个皇后和第i个皇后的行的差值
            3. Math.abs(array[n] - array[i])代表的是列差值的绝对值，因为数组存放的数据其实对应棋盘中的列
            4. 而这个行和列的差值的绝对值相等说明了斜率等于1或者-1，即说明第i个皇后和第n个皇后在同一斜线上
             */
            if (array[i] == array[n] || Math.abs(n - i) == Math.abs(array[n] - array[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 打印一维数组(即八皇后问题最终的解法)
     */
    public void print() {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "\t");
        }
        System.out.println();
    }
}
