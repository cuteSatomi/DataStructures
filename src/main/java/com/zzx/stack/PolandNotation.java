package com.zzx.stack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 使用原生的栈计算逆波兰表达式(后缀表达式)
 *
 * @Author zzx
 * @Date 2020-06-04 19:40
 */
public class PolandNotation {
    public static void main(String[] args) {
//        String suffixExpression = "3 4 + 5 * 6 -";
//        System.out.println(calculate(getListString(suffixExpression)));

        //完成将一个中缀表达式转换成后缀表达式的功能
        //1+((2+3)*4)-5     转换成     1 2 3 + 4 * + 5 -
        String expression = "1+((2+3)*4)-5";
        System.out.println(tosuffixExpressionList(toInfixExpressionList(expression)));
    }

    /**
     * 将中缀表达式的list转换成后缀表达式的list
     * 1) 初始化一个个栈和一个list集合：运算符栈 s1 和储存中间结果的list s2；
     * 2) 从左至右扫描中缀表达式；
     * 3) 遇到操作数时，将其add到 s2；
     * 4) 遇到运算符时，比较其与 s1 栈顶运算符的优先级：
     * 1.如果 s1 为空，或栈顶运算符为左括号“(”，则直接将此运算符入栈；
     * 2.否则，若优先级比栈顶运算符的高，也将运算符压入 s1；
     * 3.否则，将 s1 栈顶的运算符弹出并add到 s2 中，再次转到(4-1)与 s1 中新的栈顶运算符相比较；
     * 5) 遇到括号时：
     * (1) 如果是左括号“(”，则直接压入 s1
     * (2) 如果是右括号“)”，则依次弹出 s1 栈顶的运算符，并add到 s2，直到遇到左括号为止，此时将这一对括号丢弃
     * 6) 重复步骤 2 至 5，直到表达式的最右边
     * 7) 将 s1 中剩余的运算符依次弹出并add到 s2
     * 8) 直接输出s2的结果，即为中缀表达式对应的后缀表达式
     *
     * @param list 中缀表达式的list
     * @return 后缀表达式的list
     */
    public static List<String> tosuffixExpressionList(List<String> list) {
        //初始化一个栈s1存放操作数，一个list集合s2存放运算符
        Stack<String> s1 = new Stack<>();
        List<String> s2 = new ArrayList<>();

        for (String item : list) {
            //如果是操作数，则直接加入s2中
            if (item.matches("\\d+")) {
                s2.add(item);
            } else if (("(").equals(item)) {
                //如果item是左括号"("，则直接入s1栈
                s1.push(item);
            } else if (")".equals(item)) {
                //如果是右括号“)”，则依次弹出 s1 栈顶的运算符，并add到 s2，直到遇到左括号为止，此时将这一对括号丢弃
                while (!"(".equals(s1.peek())) {
                    //如果s1栈顶不是左括号，则将其弹出加入到s2中
                    s2.add(s1.pop());
                }
                //当while循环不满足条件退出时，说明此时的栈顶就是左括号，需要将左括号也一并弹出，从而消除掉这一对括号
                s1.pop();
            } else {
                //当又不是操作数又不是左右括号时，此时的item就应该是运算符
                //对于运算符需要判断当前item和栈顶运算符的优先级
                //因为下面while循环体内s1在不断的出栈，所以需要对s1的size进行一个判断
                while (s1.size() != 0 && Operation.getValue(s1.peek()) >= Operation.getValue(item)) {
                    //如果此时栈顶的运算符的优先级高于当前item的优先级，则需要将当前s1栈顶的运算符出栈加入到s2中
                    s2.add(s1.pop());
                }
                //此时还需要将当前的item运算符入栈
                s1.push(item);
            }
        }
        //将 s1 中剩余的运算符依次弹出并add到 s2
        while (s1.size() != 0) {
            s2.add(s1.pop());
        }
        return s2;
    }

    /**
     * 将中缀表达式字符串按序转存到list集合中
     *
     * @param expression 中缀表达式
     * @return list集合
     */
    public static List<String> toInfixExpressionList(String expression) {
        List<String> list = new ArrayList<String>();
        //定义index方便遍历字符串
        int index = 0;
        //定义String是考虑到表达式中有多位数，需要将其拼接
        String str;
        //ch则是表达式中的每一个字符
        char ch;
        do {
            //ASCII码表中0是48，9是57，不在此范围内说明是运算符，则直接加入集合中
            if ((ch = expression.charAt(index)) < 48 || (ch = expression.charAt(index)) > 57) {
                list.add(ch + "");
                index++;
            } else {
                //如果ch是一个数，此时还需要考虑是否是多位数
                str = "";
                while (index < expression.length() && (ch = expression.charAt(index)) >= 48 && (ch = expression.charAt(index)) <= 57) {
                    //将ch拼接到str中
                    str += ch;
                    index++;
                }
                list.add(str);
            }
        } while (index < expression.length());
        return list;
    }

    /**
     * 将逆波兰表达式依次存入list集合中
     *
     * @param suffixExpression 逆波兰表达式
     * @return 返回的集合
     */
    public static List<String> getListString(String suffixExpression) {
        List<String> list = new ArrayList<>();
        String[] split = suffixExpression.split(" ");
        Collections.addAll(list, split);
        return list;
    }

    /**
     * 根据传入的list集合计算逆波兰表达式的结果
     * 1)从左至右扫描，将 3 和 4 压入堆栈；
     * 2)遇到+运算符，因此弹出 4 和 3（4 为栈顶元素，3 为次顶元素），计算出 3+4 的值，得 7，再将 7 入栈；
     * 3)将 5 入栈；
     * 4)接下来是×运算符，因此弹出 5 和 7，计算出 7×5=35，将 35 入栈；
     * 5)将 6 入栈；
     * 6)最后是-运算符，计算出 35-6 的值，即 29，由此得出最终结果
     *
     * @param list 存放逆波兰表达式数据的集合
     * @return 最终的计算结果
     */

    public static int calculate(List<String> list) {
        //计算逆波兰表达式只需要初始化一个栈
        Stack<String> stack = new Stack<>();
        for (String item : list) {
            //此处使用正则表达式匹配
            if (item.matches("\\d+")) {
                //如果item是数字的话，直接入栈
                stack.push(item);
            } else {
                //如果是操作符，则将栈顶和次栈顶的操作数出栈
                int num2 = Integer.parseInt(stack.pop());
                int num1 = Integer.parseInt(stack.pop());
                //定义一个res接收计算结果
                int res = 0;
                if ("+".equals(item)) {
                    res = num1 + num2;
                } else if ("-".equals(item)) {
                    res = num1 - num2;
                } else if ("*".equals(item)) {
                    res = num1 * num2;
                } else if ("/".equals(item)) {
                    res = num1 / num2;
                } else {
                    throw new RuntimeException("操作符有误～～～");
                }
                //将计算的结果入栈
                stack.push(res + "");
            }
        }
        return Integer.parseInt(stack.pop());
    }
}

/**
 * 定义一个关于运算符的类
 */
class Operation {
    private static int ADD = 1;
    private static int SUB = 1;
    private static int MUL = 2;
    private static int DIV = 2;

    public static int getValue(String operation) {
        int result = 0;
        switch (operation) {
            case "+":
                result = ADD;
                break;
            case "-":
                result = SUB;
                break;
            case "*":
                result = MUL;
                break;
            case "/":
                result = DIV;
                break;
            default:
                System.out.println("不存在该运算符～～～");
                break;
        }
        return result;
    }
}
