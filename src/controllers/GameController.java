package controllers;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMisMatchException;
import models.Game;
import models.Player;
import strategies.WinningStrategy;

import java.util.List;

public class GameController {

    public Game startGame(int dimensionOfBoard, List<Player> players, List<WinningStrategy> winningStrategies) throws PlayersCountDimensionMisMatchException, DuplicateSymbolException, MoreThanOneBotException {
       return Game.getBuilder()
                .setDimension(dimensionOfBoard)
                .setPlayers(players)
                .setWinningStrategies(winningStrategies).build();
    }

    void makeMove(Game game){

    }

    void undo(Game game){

    }

    void checkState(Game game){

    }

    void getWinner(Game game){

    }

    void printBoard(Game game){

    }
}
