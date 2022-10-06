package NRow.Tree;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Game;

import java.util.ArrayList;

public class AlphaBetaNode{
    private Board board;
    private Heuristic heuristic;
    private int playerId;
    private ArrayList<AlphaBetaNode> children;
    private int depthToGo;
    private int pos;
    private int GameN;


    // Constructor
    public AlphaBetaNode(Board board, Heuristic heuristic, int playerId, ArrayList<AlphaBetaNode> children, int depthToGo, int pos, int GameN){
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
    public void makeTree(){
        if (depthToGo == 0){
            return;
        }

        for (int i = 0; i < board.width; i++) {  
            if (board.isValid(i)) {
                Board newBoard = new Board(board);
                newBoard = board.getNewBoard(i, playerId);
                children.add(new AlphaBetaNode(newBoard, heuristic, (playerId % 2) + 1, new ArrayList<AlphaBetaNode>(), depthToGo - 1, i, GameN));
            }
        }
    }
    /**
     * Implementation of the MiniMax algorithm with alpha-beta pruning
     * @param depth the current depth of the tree
     * @param depth0 the original depth of the tree
     * @param ISMAX true if the current node is a maximizing node, false if it is a minimizing node
     * @return either the heuristic or the position where it needs to be placed
     */
    public int evaluateTree(int depth, int depth0, int alpha, int beta, Boolean ISMAX){
        int minmaxpos = -1;

        // If the move is a winning move for player 1
        if (Game.winning(board.getBoardState(), GameN) == 1){
            // If the current depth is the same as the original depth, return the position
            if (depth == depth0){
                return getPos();
            }
            // Else return a high heuristic value
            return 10000;
        // If the move is a winning move for player 2
        } else if (Game.winning(board.getBoardState(), GameN) == 2){
            // If the current depth is the same as the original depth, return the position
            if (depth == depth0){
                return getPos();
            }
            // Else return a low heuristic value
            return -10000;
        // If the lowest depth is reached the heuristic value is returned
        } else if (depth == 0){
            return heuristic.evaluateBoard(playerId, board);
        } else if (ISMAX){ //Maximizing player
            int maxEval = Integer.MIN_VALUE;
            // For every child evaluate the tree and return the highest value
            for (AlphaBetaNode child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, alpha, beta, false);

                if(tempval > maxEval){
                    maxEval = tempval;
                    minmaxpos = child.getPos();
                }   
                // Alpha-beta pruning
                if (maxEval >= beta){
                    break;
                }

                alpha = Math.max(alpha, maxEval);
            }
            // If the current depth is not the same as the original depth
            if (depth != depth0){
                return maxEval;
            }
        } else if (!ISMAX){ //Minimizing player
            int minEval = Integer.MAX_VALUE;
            // For every child evaluate the tree and return the lowest value
            for (AlphaBetaNode child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, alpha, beta, true);

                if(tempval < minEval){
                    minEval = tempval;
                    minmaxpos = child.getPos();
                }
                // Alpha-beta pruning
                if (minEval <= alpha){
                    break;
                }

                beta = Math.min(beta, minEval);
            }
            // If the current depth is not the same as the original depth
            if (depth != depth0){
                return minEval;
            }
        // If the player's id is not 1 or 2
        } else if (playerId != 1 && playerId != 2){
            System.err.println("PlayerId is not 1 or 2");
            return -1;
        }
        // If the position is not set
        if (minmaxpos == -1){
            return -1;
        }

        return minmaxpos;
    }


    // Getters and setters
    public void setHeuristic(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    public ArrayList<AlphaBetaNode> getChildren(){
        return children;
    }

    public int getPos(){
        return pos;
    }
}
