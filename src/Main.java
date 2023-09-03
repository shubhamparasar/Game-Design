import controllers.GameController;
import models.*;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        GameController gameController = new GameController();
        System.out.println("GAME IS STARTING");
        try {
            int dimension = 3;
            List<Player> playerList = new ArrayList<>();
            Player player1 = new Player(new Symbol('X'), "Shubham", 12L, PlayerType.HUMAN);
            Player player2 = new Bot(new Symbol('O'), "GPT", 2L, BotDifficultyLevel.EASY);
            playerList.add(player1);
            playerList.add(player2);
            List<WinningStrategy> winningStrategies = new ArrayList<>();
            gameController.startGame(dimension, playerList, winningStrategies);
        }
        catch (Exception e){
            System.out.println(e);
        }


    }
}