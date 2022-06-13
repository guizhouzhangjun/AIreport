package Eight_digits.AStar;


import java.util.Arrays;

public class NodeAStar {
    // 0所在位置,定义行和列
    int zero_row, zero_column;
    // 状态数组
    int[][] state;
    // 将数组转化为String
    String statestring;
    // 记录父节点
    NodeAStar father;
    // 记录移动的次数 g(n)
    int counts;
    // 记录状态各点距离各自终点的曼哈顿距离h(n)，启发信息
    int distance;

    /**
     *
     * @param father      父节点
     * @param state       状态
     * @param zero_row    0所在的行
     * @param zero_column 0所在的列
     */
    //构造方法
    public NodeAStar(NodeAStar father, int[][] state, int zero_row, int zero_column) {
        this.father = father;
        this.state = state;
        this.zero_row = zero_row;
        this.zero_column = zero_column;
        this.statestring = Arrays.deepToString(state);

        if (null == this.father)
            this.counts = 0;
        else
            this.counts = this.father.counts + 1;

        // 计算曼哈顿距离，两层for循环遍历各个坐标值
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // 不用计算0的
                if (state[i][j] == 0)
                    continue;
                // 二维坐标曼哈顿距离计算：distance = |x - i| + |y - j|
                int x = state[i][j] / 3;// 该数字的终点行数
                int y = state[i][j] % 3;// 该数字的终点列数
                this.distance += Math.abs(x - i) + Math.abs(y - j);
            }
        }

    }
}


