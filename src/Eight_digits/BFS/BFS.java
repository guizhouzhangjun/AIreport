package Eight_digits.BFS;

import java.util.*;

/*
 * 此类为BFS算法类
 */
public class BFS {
    /// 初始状态 START_STATE {7 ,2, 4 }, {5, 0, 6}, { 8, 3, 1 }   /{1 ,3, 4 }, {8, 0, 5}, { 6, 2, 7 }
    public static final int[][] START_STATE = {  {7 ,2, 4 }, {5, 0, 6}, { 8, 3, 1 } };
    // 定义目标状态 Goal_State
    public static final int[][] GOAL_STATE = {  { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
    // 0 交换的方向  顺序依次为左、上、右、下
    public static final int[][] DIRECTIONS = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };
    //开始进入主方法，即程序入口
    public static void main(String[] args) {
        Runtime r = Runtime.getRuntime();
        r.gc();
        // 主方法开始运行的时间
        long startTime = System.currentTimeMillis();
        // 主方法开始时的剩余内存
        long startMem = r.freeMemory();

        //***********主方法开始**********
        // 利用LinkedList创建队列，先进先出
        Queue<NodeBFS> queue = new LinkedList<NodeBFS>();
        // 已经到达过的状态 HashSet 无序且唯一
        Set<String> visited = new HashSet<String>();

        // 定义初始状态节点，新建的对象根据节点类NodeBFS创建
        NodeBFS start = new NodeBFS(null, START_STATE, 1, 1);
        queue.offer(start);//将初始节点插入队列，即open表
        visited.add(start.statestring);//将初始节点加入到Set中，即closed表

        //定义是否到达目标状态，用boolean类型接收，初始为假false
        boolean flag = false;
        //总共尝试的次数
        int countsloop=0;
        while (!queue.isEmpty()) {          //判断队列是否不为空
            NodeBFS node = queue.remove();  //检索并删除此队列的头
            // 若该节点成为目标状态,输出所有父节点
            if (node.statestring.equals(Arrays.deepToString(GOAL_STATE))) {      //判断是否与目标状态相等，相等即执行，并打印出来
                System.out.println("能够到达目标，总共尝试移动次数为：	"+countsloop+"\n成功的路径如下：");
                flag = true;
                /*
                 *父节点不为空时，进行循环打印出节点信息
                 */
                do {
                    System.out.println("第"+node.counts+"次：	"+node.statestring);
                    node = node.father;
                } while (node.father != null);
                System.out.println("初始状态为："+start.statestring);
                break;
            }

            //BFS算法，四个方向
            for (int[] di : DIRECTIONS) {    //进行与0的上下左右交换，变换信息由数组DIRECTIONS决定
                int zero_r = node.zero_row + di[0];
                int zero_c = node.zero_column + di[1];

                // 若超出界外则进入下个方向，因为数组索引下标为0—2
                if (zero_r == -1 || zero_c == -1 || zero_r == 3 || zero_c == 3)
                    continue;

                countsloop++;

                int[][] newstate=new int[3][3];   //创建一个3*3的新数组，用于与0交换时使用
                int i=0;
                //与0交换方向
                for(int[] row:node.state)
                    newstate[i++]=row.clone();
                newstate[node.zero_row][node.zero_column] = newstate[zero_r][zero_c];
                newstate[zero_r][zero_c] = 0;

                NodeBFS newnode = new NodeBFS(node, newstate, zero_r, zero_c);
                // 若该节点已被访问，进入下个方向，否则集合添加该节点
                if (visited.contains(newnode.statestring))
                    continue;
                queue.add(newnode);//队列里面添加新节点
                visited.add(newnode.statestring);//已经到达过的状态 HashSet添加
            }
        }
        if (flag == false)
            System.out.println("无法到达目标状态");
        //***********主方法结束**********
        // 主方法结束的时间
        long endTime = System.currentTimeMillis();
        // 主方法结束剩余内存
        long endMem = r.freeMemory();
        System.out.println("BFS用时： " + (endTime - startTime) + " ms");
        System.out.println("BFS消耗内存： " + (startMem - endMem) / 1024 + " Kb");

    }
}

