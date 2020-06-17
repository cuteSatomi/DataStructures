package com.zzx.huffmancode;

import java.util.*;

/**
 * 霍夫曼编码压缩数据
 *
 * @Author zzx
 * @Date 2020-06-17 19:36
 */
public class HuffmanCode {
    public static void main(String[] args) {
        String str = "i like like like java do you like a java";
        byte[] bytes = str.getBytes();
        /*List<Node> nodes = getNodes(bytes);
        Node root = createHuffmanTree(nodes);
        getCodes(root, "", new StringBuilder());
        zip(bytes, huffmanCodes);*/
        byte[] huffmanZip = huffmanZip(bytes);
        System.out.println(Arrays.toString(huffmanZip));

        byte[] decode = decode(huffmanCodes, huffmanZip);
        System.out.println(new String(decode));

    }

    /*
        将压缩后的数据进行解压
            1。第一步其实就是先将转换后的字节数组重新变为"10101000101"这样的一长串
            2。根据霍夫曼编码表将这串字符串重新变为最开始的"i like like like java do you like a java"
     */

    /**
     * 将传入的字节转换为字符串，即它的补码
     *
     * @param flag 标识符如果为true则需要补齐高位，这里只让最后一个字节不需要补齐，因为最后一个字节可能本身就不足8位
     * @param b    需要转换的字节
     * @return 字节对应的二进制补码
     */
    private static String byte2itString(boolean flag, byte b) {
        //首先定义temp接收字节b
        int temp = b;
        if (flag) {
            //按位或 256 1 0000 0000 | 0000 0001 => 1 0000 0001
            //这里只要不是最后一位我们都让它补齐，如果是负数其实他是32位比如第一个字节-88对应的二进制补码是11111111111111111111111110101000
            //但是如果是正数，比如压缩后的数组中索引为7的字节77对应的补码就是1001101，只有7位，所以需要将其补齐高位
            //补齐后的结果是101001101，有9位了，因此需要截取
            temp |= 256;
        }
        //返回的是 temp 对应的二进制的补码
        String str = Integer.toBinaryString(temp);
        if (flag || temp < 0) {
            //如果不是最后一位或者当前temp为负数，则补齐之后需要截取后8位
            return str.substring(str.length() - 8);
        } else {
            //如果是最后一位，则直接返回
            return str;
        }
    }

    /**
     * 成对压缩数据的解码
     *
     * @param huffmanCodes 赫夫曼编码表 map
     * @param huffmanBytes 使用赫夫曼编码表压缩后的字节数组
     * @return 返回原始字符串对应的字节数组
     */
    public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //定义一个stringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            //当遍历到字节数组的最后一个字节的时候将flag置为true
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byte2itString(!flag, huffmanBytes[i]));
        }
        //此时stringBuilder中就存放了"10101000101"这样的一长串二进制了
        //接下来创建一个map，因为需要反向查询，所以将原来的huffmanCodes集合中的key和value反转存入
        Map<String, Byte> map = new HashMap<>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }

        //对stringBuilder进行遍历扫描，将其通过反向的霍夫曼编码表转换得到字节
        //定义List集合存放字节
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length();) {
            //通过end不停的扫描stringBuilder中的内容，初始化为i+1
            int end = i + 1;
            String key;
            while (true) {
                key = stringBuilder.substring(i, end);
                if (map.containsKey(key)) {
                    //如果map中包含该key，则将该key对应的value加入到list集合中
                    list.add(map.get(key));
                    //将end赋值给i，下一次扫描从end开始
                    i = end;
                    //退出本次循环
                    break;
                }
                //如果当前map不存在截取的key，则将end后移继续扫描
                end++;
            }
        }
        //for循环结束后，此时list集合中的元素其实就是初始的字符串转成的字节数组
        //此时需要再次将该list集合转为字节数组
        //新建一个字节数组，大小即为list集合的size
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }


    /*
        使用霍夫曼编码压缩数据的步骤
          1。首先将所给字符串转为字节数组，然后统计各个字节出现的次数，可以将结果存入map集合中，map的key就是Byte，map的value就是出现的次数count
          2。统计完成之后则需要以字节出现的次数为权值，字节本身的数据作为data变量来创建node节点，并将所有node节点存入list集合中
          3。将list集合中的所有node构建成一棵霍夫曼树
          4。得到霍夫曼树中所有字节的路径，规定向左为0，向右边为1
          5。得到霍夫曼编码以后，将字节数组中的每一个字节使用霍夫曼编码替代，并且将其拼接成一整个二进制构成的字符串
    */

    /**
     * 根据所给字符串获取其中各字节以及其出现的次数，以字节和出现次数为参数创建node并且存入list集合中
     *
     * @param bytes 需要转换的字节数组
     * @return 存放 根据字符串中各字节以及其出现次数创建的node
     */
    public static List<Node> getNodes(byte[] bytes) {
        //首先创建一个arrayList用来存放创建的node
        List<Node> nodes = new ArrayList<>();

        //创建一个map集合，字节数组中字节以及字节出现次数的情况先存入map集合中
        Map<Byte, Integer> map = new HashMap<>();
        //对字节数组进行变量，统计各个字节的出现次数情况
        for (byte byt : bytes) {
            //拿到每一个字节时，首先对其进行判断
            Integer count = map.get(byt);
            if (count == null) {
                //如果map集合中该Byte对应的value为null，则说明该字节是第一次出现，直接将其存入map集合中
                map.put(byt, 1);
            } else {
                //如果已经存在，则将count+1重新存入map集合中
                map.put(byt, ++count);
            }
        }
        //for循环结束后，map集合中就应该存放了字节数组中所有字节以及其出现次数了

        //再分别以map中的key作为data，value作为weight来创建node，并且将node存入list集合中
        //获取到map集合中所有的key
        Set<Byte> byteKey = map.keySet();
        //再对所有的key进行遍历
        for (Byte data : byteKey) {
            //分别根据每一个key获取对应的value，来创建node，并且存入list
            nodes.add(new Node(data, map.get(data)));
        }
        return nodes;
    }

    /**
     * 将list集合中存在的所有的node节点构建成一棵霍夫曼树
     * 这个方法和霍夫曼树类中的方法基本一摸一样，因此就不做详细的注释了
     *
     * @param nodes 存放着所有node节点的list集合
     * @return 返回霍夫曼树的根节点
     */
    public static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() > 1) {
            Collections.sort(nodes);

            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);
            Node parentNode = new Node(null, leftNode.weight + rightNode.weight);

            parentNode.left = leftNode;
            parentNode.right = rightNode;

            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parentNode);
        }
        return nodes.get(0);
    }

    /**
     * 创建一个map集合用来存放霍夫曼编码，map的key即为Byte字节本身，map的value即为Byte字节在霍夫曼树中的路径
     */
    static Map<Byte, String> huffmanCodes = new HashMap<>();

    /**
     * 根据每个传入的node获取它的路径，存入huffmanCodes集合中
     *
     * @param node          传入的节点
     * @param path          节点在霍夫曼树中的位置，向左为0，向右为1
     * @param stringBuilder 用于拼接字节的路径
     */
    public static void getCodes(Node node, String path, StringBuilder stringBuilder) {
        //要获取整棵霍夫曼树中所有叶子节点的路径，需要用到递归，因此每次进来这个方法的时候，我们都将上一个节点的路径获取，来拼接当前节点的路径
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        stringBuilder2.append(path);

        if (node.data == null) {
            //对当前节点进行判断，由创建霍夫曼树的规则可知，所有的非叶子节点的data都是null
            //如果进到这个if语句中，说明当前节点是一个非叶子节点，需要继续向下走，此时使用递归
            getCodes(node.left, "0", stringBuilder2);
            getCodes(node.right, "1", stringBuilder2);
        } else {
            //如果是叶子节点，则将它的路径存入集合中
            huffmanCodes.put(node.data, stringBuilder2.toString());
        }
    }

    /**
     * 将所给的字符串的字节数组根据霍夫曼编码压缩成一个新的字节数组
     *
     * @param bytes        需要压缩的字符串的字节数组
     * @param huffmanCodes 霍夫曼编码表
     * @return 压缩完成的新的字节数组
     */
    public static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        //由于需要频繁拼接字符串，所以这里定义了一个stringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println(stringBuilder);
        //上面的for循环结束之后，此时StringBuilder中存放的就是通过霍夫曼编码表转换成的"10101000101111111100..."这样的一串字符串
        //接下来还需要将字符串重新转为字节数组，首先需要得到要创建的字节数组的长度，因为1byte = 8bit
        //定义一个len表示新的字节数组的长度
        int len;
        if (stringBuilder.length() % 8 == 0) {
            //如果该字符串的长度刚好是8的整数倍，则字节数组的长度len就等于字符串长度除以8
            len = stringBuilder.length() / 8;
        } else {
            //如果不是整数倍，则len = stringBuilder.length() / 8 + 1;
            len = stringBuilder.length() / 8 + 1;
        }
        //根据求得的长度新建一个字节数组
        byte[] huffmanCodeBytes = new byte[len];

        //记录是第几个字节
        int index = 0;
        //对stringBuilder以8为长度进行切割
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strBytes;
            if (i + 8 > stringBuilder.length()) {
                //这里需要一个判断，如果最后一次分割的时候长度小于8，则从i开始一直截取到末尾
                strBytes = stringBuilder.substring(i);
            } else {
                strBytes = stringBuilder.substring(i, i + 8);
            }
            //将每一个截取的字节存入字节数组中，由于strByte是String类型的，所以保存的时候还需要做些许转换
            //二进制转为十进制，拿第一个举例，strByte = 10101000，由于最高位符号位为1，所以这个数是负数
            //负数的补码到原码的转换 => 符号位保持不变，先按位取反得到11010111，再加1，得到11011000，得到该十进制是-88
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strBytes, 2);
            index++;
        }
        return huffmanCodeBytes;
    }

    /**
     * 对上述压缩的方法进行封装，使调用变得简洁明了
     *
     * @param bytes 需要压缩的字符串的字节数组
     * @return 压缩后的字节数组
     */
    public static byte[] huffmanZip(byte[] bytes) {
        //首先根据所给字节数组创建list集合存放数组中所有字节及其出现次数
        List<Node> nodes = getNodes(bytes);
        //根据list集合中存放的所有node节点来创建一棵霍夫曼树，返回它的根节点
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        //根据byte数据在霍夫曼树中的路径获得霍夫曼编码表
        getCodes(huffmanTreeRoot, "", new StringBuilder());
        //再根据霍夫曼编码表对初始的字节数组进行压缩得到新的字节数组直接返回
        return zip(bytes, huffmanCodes);
    }
}

/**
 * 创建Node类，用来构建霍夫曼树
 */
class Node implements Comparable<Node> {
    /**
     * 用来存放字节数据
     */
    Byte data;

    /**
     * 权值，即字节出现的次数
     */
    int weight;

    /**
     * 左子节点
     */
    Node left;

    /**
     * 右子节点
     */
    Node right;

    public Node(Byte data, int weight) {
        this.data = data;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data=" + data +
                ", weight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Node o) {
        return this.weight - o.weight;
    }
}



