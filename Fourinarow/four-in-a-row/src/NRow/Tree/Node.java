package NRow.Tree;

import NRow.Board;
import NRow.Heuristics.Heuristic;
import NRow.Game;

import java.util.ArrayList;

public class Node{
    private Board board;
    private Heuristic heuristic;
    private int playerId;
    private ArrayList<Node> children;
    private int depthToGo;
    private int pos;
    private int GameN;
    private Boolean ALFABETA;

    public Node(Board board, Heuristic heuristic, int playerId, ArrayList<Node> children, int depthToGo, int pos, int GameN, Boolean ALFABETA){
        this.board = board;
        this.heuristic = heuristic;
        this.playerId = playerId;
        this.children = children;
        this.depthToGo = depthToGo;
        this.pos = pos;
        this.GameN = GameN;
        this.ALFABETA = ALFABETA;
        makeTree();
        if (ALFABETA){
            alphabetaPruning(GameN, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        }
    }

    public void makeTree(){
        if (depthToGo == 0){
            return;
        }
        for (int i = 0; i < board.width; i++) {  
            if (board.isValid(i)) {
                Board newBoard = new Board(board);
                newBoard = board.getNewBoard(i, playerId);
                children.add(new Node(newBoard, heuristic, (playerId % 2) + 1, new ArrayList<Node>(), depthToGo - 1, i, GameN, ALFABETA));
            }
        }
    }

    public int evaluateTree(int depth, int depth0, Boolean ISMAX){
        int minmaxvalue;
        int minmaxpos = -1;
        if (Game.winning(board.getBoardState(), GameN) == 1){
            //System.err.println("Player 1 can win");
            //System.err.println(depth);
            //System.err.println(depth0);

            if (depth == depth0){
                return getPos();
            }
            return 10000;
        }
        else if (Game.winning(board.getBoardState(), GameN) == 2){
            if (depth == depth0){
                //System.err.println("Player 2 can win");
                return 1;
            }
            return -10000;
        }
        else if (depth == 0){
            //System.err.println("Heuristic is: "+ heuristic.evaluateBoard(playerId, board));
            //System.err.println(board.toString());
            return heuristic.evaluateBoard(playerId, board);
        }
        else if (ISMAX){ //Maximizing player
            minmaxvalue = Integer.MIN_VALUE;
            minmaxpos = -1;
            for (Node child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, false);
                if(tempval > minmaxvalue){
                    minmaxvalue = tempval;
                    minmaxpos = child.getPos();
                }    
            }

            if (depth != depth0){
                return minmaxvalue;
            }
        }
        else if (!ISMAX){ //Minimizing player
            minmaxvalue = Integer.MAX_VALUE;
            minmaxpos = -1;
            for (Node child : getChildren()){
                int tempval = child.evaluateTree(depth - 1, depth0, true);
                if(tempval < minmaxvalue){
                    minmaxvalue = tempval;
                    minmaxpos = child.getPos();
                }
            }

            if (depth != depth0){
                return minmaxvalue;
            }


        }else if (playerId != 1 && playerId != 2){
            System.err.println("PlayerId is not 1 or 2");
            return -1;
        }

        if (minmaxpos == -1){
            System.err.println("Minmaxpos is -1");
            return -1;
        }

        return minmaxpos;
    }

    public void setHeuristic(Heuristic heuristic){
        this.heuristic = heuristic;
    }

   
    public ArrayList<Node> getChildren(){
        return children;
    }

    public int getPos(){
        return pos;
    }

    public int alphabetaPruning(int depth, int alpha, int beta, Boolean ISMAX){
        if (depth == 0){
            return heuristic.evaluateBoard(playerId, board);
        }
        if (ISMAX){
            int maxEval = Integer.MIN_VALUE;
            for (Node child : getChildren()){
                maxEval = Math.max(maxEval, child.alphabetaPruning(depth - 1, alpha, beta, false));
                if (maxEval >= beta){
                    break;
                }
                alpha = Math.max(alpha, maxEval);
            }
            return maxEval;

        }
        else if (!ISMAX){
            int minEval = Integer.MAX_VALUE;
            for (Node child : getChildren()){
                minEval = Math.min(minEval, child.alphabetaPruning(depth - 1, alpha, beta, true));
                if (minEval <= alpha){
                    break;
                }
                beta = Math.min(beta, minEval);
            }
            return minEval;
        }else{
            System.err.println("ISMAX is not true or false");
            return -1;
        }
    }

}
