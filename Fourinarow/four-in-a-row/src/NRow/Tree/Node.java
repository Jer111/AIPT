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
    }

    public void setHeuristic(Heuristic heuristic){
        this.heuristic = heuristic;
    }

    public void addChild(Node child){
        children.add(child);
    }

    public int getHeuristic(){
        return heuristic.evaluateBoard(playerId, board);
    }
    public int getDepthToGo(){
        return depthToGo;
    }

}
