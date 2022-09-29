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

    public int evaluateTree(int depth, int depth0, int alpha, int beta, Boolean ISMAX){
        int minmaxpos = -1;
        if (Game.winning(board.getBoardState(), GameN) == 1){
            if (depth == depth0){
                return getPos();
            }

            return 10000;
        } else if (Game.winning(board.getBoardState(), GameN) == 2){
            if (depth == depth0){
                return getPos();
            }
            return -10000;
        } else if (depth == 0){
            return heuristic.evaluateBoard(playerId, board);
        } else if (ISMAX){ //Maximizing player
            int maxEval = Integer.MIN_VALUE;

            for (AlphaBetaNode child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, alpha, beta, false);

                if(tempval > maxEval){
                    maxEval = tempval;
                    minmaxpos = child.getPos();
                }   

                if (maxEval >= beta){
                    break;
                }

                alpha = Math.max(alpha, maxEval);
            }

            if (depth != depth0){
                return maxEval;
            }
        } else if (!ISMAX){ //Minimizing player
            int minEval = Integer.MAX_VALUE;

            for (AlphaBetaNode child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, alpha, beta, true);

                if(tempval < minEval){
                    minEval = tempval;
                    minmaxpos = child.getPos();
                }

                if (minEval <= alpha){
                    break;
                }

                beta = Math.min(beta, minEval);
            }

            if (depth != depth0){
                return minEval;
            }
        } else if (playerId != 1 && playerId != 2){
            System.err.println("PlayerId is not 1 or 2");
            return -1;
        }

        if (minmaxpos == -1){
            return -1;
        }

        return minmaxpos;
    }

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
