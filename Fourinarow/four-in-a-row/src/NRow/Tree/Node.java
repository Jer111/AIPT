package NRow.Tree;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Game;

import java.util.ArrayList;

public class Node {
    private Board board;
    private Heuristic heuristic;
    private int playerId;
    private ArrayList<Node> children;
    private int depthToGo;
    private int pos;
    private int GameN;

    // Constructor
    public Node(Board board, Heuristic heuristic, int playerId, ArrayList<Node> children, int depthToGo, int pos,
            int GameN) {
        this.board = board;
        this.heuristic = heuristic;
        this.playerId = playerId;
        this.children = children;
        this.depthToGo = depthToGo;
        this.pos = pos;
        this.GameN = GameN;

        makeTree();
    }

    /**
     * Makes a tree of nodes
     */
    public void makeTree() {
        if (depthToGo == 0) {
            return;
        }

        for (int i = 0; i < board.width; i++) {
            if (board.isValid(i)) {
                Board newBoard = new Board(board);
                newBoard = board.getNewBoard(i, playerId);
                children.add(new Node(newBoard, heuristic, (playerId % 2) + 1, new ArrayList<Node>(), depthToGo - 1, i,
                        GameN));
            }
        }
    }

    /**
     * Implementation of the MiniMax algorithm
     * 
     * @param depth  the current depth of the tree
     * @param depth0 the original depth of the tree
     * @param ISMAX  true if the current node is a maximizing node, false if it is a
     *               minimizing node
     * @return either the heuristic or the position where it needs to be placed
     */
    public int evaluateTree(int depth, int depth0, Boolean ISMAX) {
        int minmaxvalue;
        int minmaxpos = -1;

        // If the move ends in a draw
        if (Game.winning(board.getBoardState(), GameN) == -1){
            return 0;
        }
        // If the move is a winning move for player 1
        if (Game.winning(board.getBoardState(), GameN) == 1) {
            // If the move is at the same depth as the original move
            if (depth == depth0) {
                return getPos();
            }
            // Else return a high heuristic value
            return 10000;
            // If the move is a winning move for player 2
        } else if (Game.winning(board.getBoardState(), GameN) == 2) {
            // If the move is at the same depth as the original move
            if (depth == depth0) {
                return getPos();
            }
            // Else return a low heuristic value
            return -10000;
            // If the lowest depth is reached the heuristic value is returned
        } else if (depth == 0) {
            return heuristic.evaluateBoard(playerId, board);
        } else if (ISMAX) { // Maximizing player
            minmaxvalue = Integer.MIN_VALUE;
            // For every child evaluate the tree and return the highest value
            for (Node child : getChildren()) {
                int tempval = child.evaluateTree(depth - 1, depth0, false);
                if (tempval > minmaxvalue) {
                    minmaxvalue = tempval;
                    minmaxpos = child.getPos();
                }
            }
            // If the move is not at the same depth as the original move
            if (depth != depth0) {
                return minmaxvalue;
            }
        } else if (!ISMAX) { // Minimizing player
            minmaxvalue = Integer.MAX_VALUE;
            // For every child evaluate the tree and return the lowest value
            for (Node child : getChildren()) {
                int tempval = child.evaluateTree(depth - 1, depth0, true);
                if (tempval < minmaxvalue) {
                    minmaxvalue = tempval;
                    minmaxpos = child.getPos();
                }
            }
            // If the move is not at the same depth as the original move
            if (depth != depth0) {
                return minmaxvalue;
            }
            // If the playerid's are not 1 or 2 and error is displayed
        } else if (playerId != 1 && playerId != 2) {
            System.err.println("PlayerId is not 1 or 2");
            return -1;
        }

        // If the pos is not set an error is displayed
        if (minmaxpos == -1) {
            System.err.println("Minmaxpos is -1");
            return -1;
        }
        // Return the pos of the move
        return minmaxpos;
    }

    // Getters and Setters
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    public ArrayList<Node> getChildren() {
        return children;
    }

    public int getPos() {
        return pos;
    }
}
