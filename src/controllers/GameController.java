package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMisMatchException;
import models.Game;
import models.GameState;
import models.Player;
import strategies.winningstrategies.WinningStrategy;

import java.util.List;

public class GameController {

    public Game startGame(int dimensionOfBoard, List<Player> players, List<WinningStrategy> winningStrategies) throws PlayersCountDimensionMisMatchException, DuplicateSymbolException, MoreThanOneBotException {
       return Game.getBuilder()
                .setDimension(dimensionOfBoard)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies).build();
    }

    public void makeMove(Game game){
        game.makeMove();
    }

    void undo(Game game){

    }

    public GameState checkState(Game game){
        return game.getGameState();
    }

    void getWinner(Game game){

    }

    public void printBoard(Game game){
        game.printBoard();
    }
}
