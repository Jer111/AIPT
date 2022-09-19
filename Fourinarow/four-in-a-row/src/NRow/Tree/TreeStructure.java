package NRow.Tree;

import NRow.Board;
import NRow.Heuristics.Heuristic;

import java.util.ArrayList;

public class TreeStructure{
    private Node root;
    
    public TreeStructure(Board board, Heuristic heuristic, int playerId){
        //TODO DIT MOET IN NODE
        root = new Node(board, heuristic, playerId, new ArrayList<Node>(), 3);
        if (root.getDepthToGo() == 0){
            return;
        }
        for (int i = 0; i < board.width; i++) {  
            if (board.isValid(i)) {
                Board newBoard = new Board(board);
                newBoard = board.getNewBoard(i, playerId);
                System.out.println(i+1);
                System.out.println(newBoard.toString());
                root.addChild(new Node(newBoard, heuristic, (playerId % 2) + 1, new ArrayList<Node>(), root.getDepthToGo() - 1));
            }
        }

    }
}






