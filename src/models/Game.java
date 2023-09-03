package models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMisMatchException;
import strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    private List<Player> players;
    private Board board;
    private List<Move> moves;
    private Player winner;
    private GameState gameState;
    private int nextMovePlayerIndex;
    private List<WinningStrategy> winningStrategies;

    public static Builder getBuilder(){
        return new Builder();
    }

    private Game(int dimension, List<Player> players, List<WinningStrategy> winningStrategies){
        this.players=players;
        this.winningStrategies=winningStrategies;
        this.nextMovePlayerIndex = 0;
        this.gameState=GameState.IN_PROGRESS;
        this.moves= new ArrayList<>();
        this.board= new Board(dimension);

    }


    public static class Builder{
        private List<Player> players;
        private List<WinningStrategy> winningStrategies;
        private int dimension;

        public Builder(){
            this.players = new ArrayList<>();
            this.winningStrategies = new ArrayList<>();
            this.dimension = 0;
        }
        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder addPlayers(Player player){
            this.players.add(player);
            return  this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        public Builder addWinningStrategies(WinningStrategy winningStrategy){
            this.winningStrategies.add(winningStrategy);
            return this;
        }

        public Builder setDimension(int dimension){
            this.dimension=dimension;
            return this;
        }

        private boolean validateBotCounts() throws MoreThanOneBotException {
            int botCount = 0;

            for(Player player:players){
                if(player.getPlayerType().equals(PlayerType.BOT)){
                    botCount += 1;
                }
            }
            if(botCount > 1){
                throw new MoreThanOneBotException();
            }
            return true;
        }

        private void validateDimension() throws PlayersCountDimensionMisMatchException {

            if(players.size() != dimension - 1){
                throw new PlayersCountDimensionMisMatchException();
            }
        }

        private void validateSymbol() throws DuplicateSymbolException {
            Map<Character,Integer> symbolCounts = new HashMap<>();

            for(Player player : players){
                if(!symbolCounts.containsKey(player.getSymbol().getaChar())){
                    symbolCounts.put(player.getSymbol().getaChar(),0);
                }
                else{
                    symbolCounts.put(player.getSymbol().getaChar(),symbolCounts.get(player.getSymbol().getaChar())+1);
                }
                if(symbolCounts.get(player.getSymbol().getaChar()) > 1){
                    throw new DuplicateSymbolException();
                }
            }
        }
        private void validate() throws PlayersCountDimensionMisMatchException, DuplicateSymbolException, MoreThanOneBotException {
            try {
                validateBotCounts();
                validateDimension();
                validateSymbol();
            }
            catch (Exception e){
                throw e;
            }
        }
        public Game build() throws PlayersCountDimensionMisMatchException, DuplicateSymbolException, MoreThanOneBotException {
            validate();
            return new Game(dimension,players,winningStrategies);
        }
    }


}
