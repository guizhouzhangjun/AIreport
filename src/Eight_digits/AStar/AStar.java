package Eight_digits.AStar;

import java.util.*;

public class AStar {

    /// 初始状态 START_STATE{7 ,2, 4 }, {5, 0, 6}, { 8, 3, 1 }   /{1 ,3, 4 }, {8, 0, 5}, { 6, 2, 7 }
    public static final int[][] START_STATE = {  {7 ,2, 4 }, {5, 0, 6}, { 8, 3, 1 } };
    // 目标状态 Goal_State
    public static final int[][] GOAL_STATE = {  { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 } };
    // 0 交换的方向  顺序依次为左、上、右、下
    public static final int[][] DIRECTIONS = { { 0, -1 }, { -1, 0 }, { 0, 1 }, { 1, 0 } };

    // 绝对不可能到达目标状态的状态
    public static final int[][] WRONG_STATE = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 8, 7 } };

    public static void main(String[] args) {

        Runtime r = Runtime.getRuntime();
        r.gc();
        // 主方法开始运行的时间
        long startTime = System.currentTimeMillis();
        // 主方法开始时的剩余内存
        long startMem = r.freeMemory();

        // BFS用的是队列，此处为优先队列，每次 poll 的都是 f(n)最小的状态节点,用Comparator接口实现
        PriorityQueue<NodeAStar> pqueue = new PriorityQueue<>(new Comparator<NodeAStar>() {
            public int compare(NodeAStar o1, NodeAStar o2) {
                return o1.distance + o1.counts - o2.distance - o2.counts;
            }
        });
        // 已经到达过的状态 HashSet 无序且唯一
        Set<String> visited = new HashSet<String>();

        // 初始状态节点
        NodeAStar start = new NodeAStar(null, START_STATE, 1, 1);
        pqueue.add(start);
        visited.add(start.statestring);

        // 是否到达目标状态
        boolean flag = false;
        // 总共尝试的次数
        int countsloop = 0;
        while (!pqueue.isEmpty()) {
            NodeAStar node = pqueue.poll(); //检索并删除此队列的头，如果此队列为空，则返回 null
            if (node.statestring.equals(Arrays.deepToString(GOAL_STATE))) {
                System.out.println("能够到达目标，总共尝试移动次数为：	" + countsloop + "\n成功的路径如下：");
                flag = true;
                do {
                    System.out.println("第" + node.counts + "次：	" + node.statestring);
                    System.out.println("曼哈顿距离" + node.distance);
                    node = node.father;
                } while (node.father != null);
                System.out.println("初始状态为：" + start.statestring);
                break;
            }

            // 错误状态
           if (node.statestring.equals(Arrays.deepToString(WRONG_STATE)))
               continue;

            // A*算法，基于BFS的四个方向
           for (int[] di : DIRECTIONS) {
               int zero_r = node.zero_row + di[0];
               int zero_c = node.zero_column + di[1];

                // 若超出界外则进入下个方向
               if (zero_r == -1 || zero_c == -1 || zero_r == 3 || zero_c == 3)
                    continue;

                countsloop++;

                // 数组中0与方向上的数字交换
               int[][] newstate = new int[3][3];
               int i = 0;
               for (int[] row : node.state)
                   newstate[i++] = row.clone();
               newstate[node.zero_row][node.zero_column] = newstate[zero_r][zero_c];
               newstate[zero_r][zero_c] = 0;

               NodeAStar newnode = new NodeAStar(node, newstate, zero_r, zero_c);
                // 若该节点已被访问，进入下个方向，否则集合添加该节点
               if (visited.contains(newnode.statestring))
                   continue;
               pqueue.add(newnode);
               visited.add(newnode.statestring);
           }
        }
        if (flag == false)
            System.out.println("无法到达目标状态");

        // 主方法结束的时间
        long endTime = System.currentTimeMillis();
        // 主方法结束剩余内存
        long endMem = r.freeMemory();
        System.out.println("A*用时： " + (endTime - startTime) + " ms");
        System.out.println("A*消耗内存： " + (startMem - endMem) / 1024 + " Kb");
    }
}

