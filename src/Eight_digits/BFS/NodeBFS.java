package Eight_digits.BFS;

import java.util.Arrays;

/*
 * 此类为节点类
 */
public class NodeBFS {
    //0所在行和列位置
    int zero_row,zero_column;
    // 状态数组
    int[][] state;
    //将数组转化为String
    String statestring;
    //记录父节点
    NodeBFS father;
    //记录移动的次数
    int counts;
    /**
     * @param father	父节点
     * @param state	状态
     * @param zero_row	0所在的行
     * @param zero_column	0所在的列
     */
    //构造方法
    public NodeBFS(NodeBFS father,int[][] state,int zero_row,int zero_column) {
        this.father=father;
        this.state=state;
        this.zero_row=zero_row;
        this.zero_column=zero_column;
        this.statestring=Arrays.deepToString(state);

        //判断父节点是否为空，为空则移动次数置0，不为空则加1
        if(null==this.father)
            this.counts=0;
        else
            this.counts=this.father.counts+1;
    }
}
