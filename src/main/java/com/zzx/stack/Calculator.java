package com.zzx.stack;

/**
 * @Author zzx
 * @Date 2020-06-03 19:14
 */
public class Calculator {
    public static void main(String[] args) {
        //定义需要进行计算的表达式
        String expression = "9-7*1*1+2";
        //存放数据的栈
        ArrayStack2 numStack = new ArrayStack2(10);
        //存放操作符的栈
        ArrayStack2 operStack = new ArrayStack2(10);

        //定义一些相关的变量
        int index = 0;
        int num1 = 0;
        int num2 = 0;
        int oper = 0;
        int res = 0;
        //取得字符串中的每一个字符
        char ch = ' ';
        //用于拼接多位数的
        String keepNum = "";

        while (true) {
            ch = expression.substring(index, index + 1).charAt(0);
            //判断ch是数字还是操作符
            if (operStack.isOper(ch)) {
                //是操作符，还需要判断操作符栈中是否为空
                if (!operStack.isEmpty()) {
                    //如果不为空，则需要判断栈顶的操作符与当前的操作符优先级的问题
                    /*原本这里用的是if，这样是有问题的，比如说上面的那个式子"5-2*3+1"
                    当判断到ch='+'时，此时的'+'的优先级是小于操作数栈顶的'*'的，
                    此时操作数栈从上到下存放的数据是3，2，5；操作符栈从上到下存放的是'*'，'-'
                    此时回弹出操作数栈的最上面两个数据和操作符栈顶的操作符进行运算，得到res=3*2=6
                    这时操作数栈中的3和2就出栈了，只剩下了5，操作符栈中只剩下了'-'
                    如果按照之前的if来判断的话，就不会判断ch也就是'+'和操作符栈顶的'-'的优先级而直接存放进操作符栈了
                    这样操作数栈的数据就是1，6，5；操作符栈中的数据就是'+'和'-'
                    这样的话按照原本的逻辑运算就是1+6-5=2，所以得到了错误的结果
                    所以此处需要用while，一次运算结束后再次判断当前ch与当前栈顶的操作符优先级
                    但是此处还有一个bug，这个判断条件出现了数组越界
                    原因是operStack.peek()这个方法造成的，原因是操作符栈中的操作符可能被全部取出，而每次运算结束又没有将当前操作符存入，所以会出现该异常
                    我的解决办法是修改了peek()方法，当top=-1时即栈中不存在数据时，直接返回-1，此时operStack.priority(-1)的返回值则是-1，肯定会小于当前操作符的优先级
                    此时循环退出，将当前操作符存入栈中
                    */
                    while (operStack.priority(ch) <= operStack.priority(operStack.peek())) {
                        //如果符号栈有操作符，就进行比较,如果当前的操作符的优先级小于或者等于栈中的操作符, 就需要从数栈中 pop 出两个数
                        //再从符号栈中 pop 出一个符号，进行运算，将得到结果，入数栈，然后将当前的操作符入栈
                        num1 = numStack.pop();
                        num2 = numStack.pop();
                        oper = operStack.pop();
                        res = numStack.cal(num1, num2, oper);
                        //把运算的结果存入数栈
                        numStack.push(res);
                        //最后再将当前操作符存入操作符栈
                        //此处不能将当前操作符存入栈中，需要进行循环判断，当不满足以上条件时才会退出循环
                        //operStack.push(ch);
                    }
                    //如果当前操作符优先级高于栈顶的优先级，则直接将该运算符存入运算符栈
                    operStack.push(ch);
                } else {
                    //如果为空，则直接存入操作符栈中
                    operStack.push(ch);
                }
            } else {
                //不是操作符，则将数字存入数字栈，数字1对应的额ascii码表为49
                //numStack.push(ch - 48);
                //上面这行代码对于多位数的运算时有问题的
                //处理多位数运算时，不能发现一个数就立即入栈
                keepNum += ch;
                //如果已经是最后一个字符，则直接入栈
                if (expression.length() - 1 == index) {
                    numStack.push(Integer.parseInt(keepNum));
                } else {
                    //判断下一个字符是否还是数字，是数字就继续扫描，不是数字则入栈
                    if (operStack.isOper(expression.substring(index + 1, index + 2).charAt(0))) {
                        //后一位是操作符，则入栈
                        numStack.push(Integer.parseInt(keepNum));
                        //最后将keepNum清空，不然会影响下一个多位数的拼接
                        keepNum = "";
                    }
                }
            }
            //对当前ch做完处理后，让index++，处理下一个ch
            index++;
            //如果遍历结束则break退出
            if (index >= expression.length()) {
                break;
            }
        }

        //当表达式扫描完毕，再按顺序处理操作数栈和操作符栈中的数据
        while (true) {
            if (operStack.isEmpty()) {
                break;
            }
            num1 = numStack.pop();
            num2 = numStack.pop();
            oper = operStack.pop();
            res = numStack.cal(num1, num2, oper);
            //将最后的结果存入数栈中
            numStack.push(res);
        }
        System.out.printf("表达式 %s = %d ", expression, numStack.pop());
    }
}

/**
 * 定义一个类表示栈
 */
class ArrayStack2 {
    /**
     * 栈的大小
     */
    private int maxSize;
    /**
     * 数组模拟栈，数据就放在该数组
     */
    private int[] stack;
    /**
     * top表示栈顶，初始值为-1
     */
    public int top = -1;

    public ArrayStack2(int maxSize) {
        this.maxSize = maxSize;
        //初始化stack数组
        stack = new int[this.maxSize];
    }

    /**
     * 取得但不弹出当前栈顶的数据
     *
     * @return 栈顶的数据
     */
    public int peek() {
        return top == -1 ? -1 : stack[top];
    }

    /**
     * 返回运算符的优先级
     *
     * @param oper 运算符
     * @return 数字越大优先级越高
     */
    public int priority(int oper) {
        if (oper == '*' || oper == '/') {
            return 1;
        } else if (oper == '+' || oper == '-') {
            return 0;
        } else {
            return -1;
        }
    }

    /**
     * 判断当前字符是否是运算符
     *
     * @param val 要判断的字符
     * @return true为运算符
     */
    public boolean isOper(char val) {
        return val == '+' || val == '-' || val == '*' || val == '/';
    }

    /**
     * 计算方法
     *
     * @param num1 操作数1，num1是栈顶的数据
     * @param num2 操作数2，num2是倒数第二个数据
     * @param oper 操作符
     * @return 计算的结果
     */
    public int cal(int num1, int num2, int oper) {
        int res = 0;
        switch (oper) {
            case '+':
                res = num2 + num1;
                break;
            case '-':
                res = num2 - num1;
                break;
            case '*':
                res = num2 * num1;
                break;
            case '/':
                res = num2 / num1;
                break;
            default:
                break;
        }
        return res;
    }

    /**
     * 判断是否栈满
     *
     * @return true为满
     */
    public boolean isFull() {
        return top == maxSize - 1;
    }

    /**
     * 判断是否栈空
     *
     * @return true为空
     */
    public boolean isEmpty() {
        return top == -1;
    }

    /**
     * 入栈
     *
     * @param value 要插入的数据
     */
    public void push(int value) {
        //先判断是否栈满
        if (isFull()) {
            System.out.println("栈满~~~");
            return;
        }
        //因为top初值是-1，所有先将top++，再往数组插入数据
        top++;
        stack[top] = value;
    }

    /**
     * 出栈
     *
     * @return 取出的数据
     */
    public int pop() {
        //判断是否栈空
        if (isEmpty()) {
            throw new RuntimeException("栈空~~~");
        }
        //因为下面要对top进行减一的操作，所以需要先定义一个value的值来存储当前的栈顶数据
        int value = stack[top];
        //取完值后将其置为0
        stack[top] = 0;
        top--;
        //将栈顶的数据返回
        return value;
    }

    /**
     * 遍历栈
     */
    public void list() {
        if (isEmpty()) {
            //判断栈是否为空
            System.out.println("栈空~~~");
            return;
        }
        for (int i = top; i >= 0; i--) {
            System.out.printf("stack[%d]=%d\n", i, stack[i]);
        }
    }
}
