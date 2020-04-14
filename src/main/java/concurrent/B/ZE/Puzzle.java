package concurrent.B.ZE;

import java.util.*;

/**
 * Puzzle
 * <p/>
 * Abstraction for puzzles like the 'sliding blocks puzzle'
 *  表示搬箱子谜题抽象类
 *  P:位置类
 *  M:移动类
 * @author Brian Goetz and Tim Peierls
 */
public interface Puzzle <P, M> {
    /**
     * 初始位置
     * @return
     */
    P initialPosition();

    /**
     * 是否是目标
     * @param position
     * @return
     */
    boolean isGoal(P position);

    /**
     * 合法移动
     * @param position
     * @return
     */
    Set<M> legalMoves(P position);

    /**
     * 移动位置
     * @param position
     * @param move
     * @return
     */
    P move(P position, M move);
}
