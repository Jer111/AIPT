package NRow;
import NRow.Heuristics.*;
import NRow.Players.PlayerController;
import NRow.Players.AlphaBetaPlayer;
import NRow.Players.MinMaxPlayer;

public class Testcase {

    public Testcase() {

        // Cases with width 5, height 5, gameN 3, depth1 3 and depth2 3
        // 1. MinMaxPlayer vs MinMaxPlayer
        /*System.out.println("Case 1: MinMaxPlayer X vs MinMaxPlayer O, width 5, height 5, gameN 3, depth1 3 and depth2 3");
        testOnlyMinimax(5, 5, 3, 3, 3);
        // 2. AlphaBetaPlayer vs AlphaBetaPlayer
        System.out.println("Case 2: AlphaBetaPlayer X vs AlphaBetaPlayer O, width 5, height 5, gameN 3, depth1 3 and depth2 3");
        testOnlyAlphabeta(5, 5, 3, 3, 3);
        // 3. AlphaBetaPlayer vs MiniMaxPlayer
        System.out.println("Case 3: AlphaBetaPlayer X vs MiniMaxPlayer O, width 5, height 5, gameN 3, depth1 3 and depth2 3");
        testBoth(5, 5, 3, 3, 3);
        System.out.println("------------------------------------------------------------------------------------------------------");
        // Cases with width 8, height 5, gameN 4, depth1 3 and depth2 5
        // 4. MinMaxPlayer vs MinMaxPlayer
        System.out.println("Case 4: MinMaxPlayer X vs MinMaxPlayer O, width 8, height 5, gameN 4, depth1 3 and depth2 5");
        testOnlyMinimax(8, 5, 4, 3, 5);
        // 5. AlphaBetaPlayer vs AlphaBetaPlayer
        System.out.println("Case 5: AlphaBetaPlayer X vs AlphaBetaPlayer O, width 8, height 5, gameN 4, depth1 3 and depth2 5");
        testOnlyAlphabeta(8, 5, 4, 3, 5);
        // 6. AlphaBetaPlayer vs MiniMaxPlayer
        System.out.println("Case 6: AlphaBetaPlayer X vs MiniMaxPlayer O, width 8, height 5, gameN 4, depth1 3 and depth2 5");
        testBoth(8, 5, 4, 3, 5);
        System.out.println("------------------------------------------------------------------------------------------------------");
        // Cases with width 12, height 20, gameN 5, depth1 5 and depth2 3
        // 7. MinMaxPlayer vs MinMaxPlayer
        System.out.println("Case 7: MinMaxPlayer X vs MinMaxPlayer O, width 12, height 19, gameN 4, depth1 3 and depth2 4");
        testOnlyMinimax(12, 20, 4, 3, 4);
        // 8. AlphaBetaPlayer vs AlphaBetaPlayer
        System.out.println("Case 8: AlphaBetaPlayer X vs AlphaBetaPlayer O, width 12, height 19, gameN 4, depth1 3 and depth2 4");
        testOnlyAlphabeta(12, 20, 4, 3, 4);
        // 9. AlphaBetaPlayer vs MiniMaxPlayer
        System.out.println("Case 9: AlphaBetaPlayer X vs MiniMaxPlayer O, width 12, height 19, gameN 4, depth1 3 and depth2 4");
        testBoth(12, 20, 4, 3, 4);
        System.out.println("------------------------------------------------------------------------------------------------------");*/
        System.out.println("Case 10: MinMaxPlayer X vs MinMaxPlayer O, width 12, height 19, gameN 5, depth1 1 and depth2 1");
        testOnlyMinimax(12, 20, 5, 1, 1);
        System.out.println("Case 11: AlphaBetaPlayer X vs AlphaBetaPlayer O, width 12, height 19, gameN 5, depth1 1 and depth2 1");
        testOnlyAlphabeta(12, 20, 5, 1, 1);
        System.out.println("Case 12: AlphaBetaPlayer X vs MiniMaxPlayer O, width 12, height 19, gameN 5, depth1 1 and depth2 1");
        testBoth(12, 20, 5, 1, 1);
        System.out.println("------------------------------------------------------------------------------------------------------");
    }



    public void testOnlyMinimax(int width, int height, int gameN, int depth1, int depth2) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(gameN);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(gameN);
        PlayerController one = new MinMaxPlayer(1, gameN, depth1, heuristic1);
        PlayerController two = new MinMaxPlayer(2, gameN, depth2, heuristic2);
        PlayerController[] players = { one, two };
        Game game = new Game(gameN, width, height, players);
        game.startGame();
    }

    public void testOnlyAlphabeta(int width, int height, int gameN, int depth1, int depth2) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(gameN);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(gameN);
        PlayerController one = new AlphaBetaPlayer(1, gameN, depth1, heuristic1);
        PlayerController two = new AlphaBetaPlayer(2, gameN, depth2, heuristic2);
        PlayerController[] players = { one, two };
        Game game = new Game(gameN, width, height, players);
        game.startGame();
    }

    public void testBoth(int width, int height, int gameN, int depth1, int depth2) {
        SimpleHeuristic heuristic1 = new SimpleHeuristic(gameN);
        SimpleHeuristic heuristic2 = new SimpleHeuristic(gameN);
        PlayerController one = new AlphaBetaPlayer(1, gameN, depth1, heuristic1);
        PlayerController two = new MinMaxPlayer(2, gameN, depth2, heuristic2);
        PlayerController[] players = { one, two };
        Game game = new Game(gameN, width, height, players);
        game.startGame();
    }
    
    
}
