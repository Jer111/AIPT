package NRow.Players;

import java.util.ArrayList;
import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Tree.Node;

public class MinMaxPlayer extends PlayerController {
    private int depth;

    public MinMaxPlayer(int playerId, int gameN, int depth, Heuristic heuristic) {
        super(playerId, gameN, heuristic);
        this.depth = depth;
        // You can add functionality which runs when the player is first created (before
        // the game starts)
    }

    /**
   * Implement this method yourself!
   * @param board the current board
   * @return column integer the player chose
   */
    @Override
    public int makeMove(Board board) {
        Node root = new Node(board, heuristic, playerId, new ArrayList<Node>(), depth, -1, gameN);
        
        if (playerId == 1) {
            return root.evaluateTree(depth, depth, true);
        } else if (playerId == 2) {
            return root.evaluateTree(depth, depth, false);
        } else {
            return -1;
        }
    }
}
