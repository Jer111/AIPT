package NRow.Players;

import java.util.ArrayList;
import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Tree.AlphaBetaNode;

public class AlphaBetaPlayer extends PlayerController{
    private int depth;

    public AlphaBetaPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        // You can add functionality which runs when the player is first created (before
        // the game starts)
    }

     /**
     * makeMove is called every time it is this player's turn to make a move.
     * @param board the current board
     * @return column integer the player chose
     */
    @Override
    public int makeMove(Board board) {   
        AlphaBetaNode root = new AlphaBetaNode(board, heuristic, playerId, new ArrayList<AlphaBetaNode>(), depth, -1, gameN);

        if (playerId == 1) {
            return root.evaluateTree(depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        } else if (playerId == 2) {
            return root.evaluateTree(depth, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
        } else {
            return -1;
        }
    }
}
