package NRow.Tree;

import NRow.Board;
import NRow.Heuristics.Heuristic;

import java.util.ArrayList;

public class Node{
    private Board board;
    private Heuristic heuristic;
    private int playerId;
    private ArrayList<Node> children;
    private int depthToGo;

    public Node(Board board, Heuristic heuristic, int playerId, ArrayList<Node> children, int depthToGo){
        this.board = board;
        this.heuristic = heuristic;
        this.playerId = playerId;
        this.children = children;
        this.depthToGo = depthToGo;
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
                System.out.println(i+1);
                System.out.println(heuristic.getBestAction(playerId, newBoard));
                System.out.println(newBoard.toString());
                children.add(new Node(newBoard, heuristic, (playerId % 2) + 1, new ArrayList<Node>(), depthToGo - 1));
            }
        }
    }

    public int evaluateTree(int depth, Boolean ISMAX){
        int minmaxvalue;
        if (depth == 0){
            return heuristic.getBestAction(playerId, board);
        }
        if (ISMAX){ //Maximizing player
            minmaxvalue = Integer.MIN_VALUE;
            for (Node child : getChildren()){
                minmaxvalue = Math.max(minmaxvalue, child.evaluateTree(depth -1, false));
            }
            return minmaxvalue;
        }
        else if (!ISMAX){ //Minimizing player
            minmaxvalue = Integer.MAX_VALUE;
            for (Node child : getChildren()){
                minmaxvalue = Math.min(minmaxvalue, child.evaluateTree(depth -1, true));
            }
            return minmaxvalue;
        }else{
            System.err.println("PlayerId is not 1 or 2");
            return 0;
        }
    }

    public void setHeuristic(Heuristic heuristic){
        this.heuristic = heuristic;
    }

   
    public ArrayList<Node> getChildren(){
        return children;
    }

}
