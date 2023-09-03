package models;

import exceptions.DuplicateSymbolException;
import exceptions.MoreThanOneBotException;
import exceptions.PlayersCountDimensionMisMatchException;
import strategies.winningstrategies.WinningStrategy;

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

    public List<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public Player getWinner() {
        return winner;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getNextMovePlayerIndex() {
        return nextMovePlayerIndex;
    }

    public List<WinningStrategy> getWinningStrategies() {
        return winningStrategies;
    }

    public void printBoard(){
        board.printBoard();
    }

    private boolean validateMove(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        if(row >= board.getSize() || col >= board.getSize()){
            return false;
        }
        if(board.getBoard().get(row).get(col).getCellState().equals(CellState.EMPTY)){
            return true;
        }
        return false;
    }

    private boolean checkWinner(Board board, Move move){
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(board,move)){
                return true;
            }
        }
        return false;
    }
    public void makeMove(){
        Player currentPlayer = players.get(nextMovePlayerIndex);
        System.out.println("it is " + currentPlayer.getName() + "'s turn. Please make the move");
        Move move = currentPlayer.makeMove(board);
        System.out.println(currentPlayer.getName() + " has made move at row : "+move.getCell().getRow()
                + " and column : "+move.getCell().getCol());

        if (!validateMove(move)){
            System.out.println("invalid move, please try again");
            return;
        }

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();

        Cell cellToChange = board.getBoard().get(row).get(col);
        cellToChange.setCellState(CellState.FILLED);
        cellToChange.setPlayer(currentPlayer);
        Move finalMoveObject = new Move(currentPlayer,cellToChange);
        moves.add(move);

        nextMovePlayerIndex += 1;
        nextMovePlayerIndex %= players.size();

        if (checkWinner(board,finalMoveObject)){
            gameState = GameState.WIN;
            winner = currentPlayer;
        }

        if(moves.size() >= this.board.getSize() * this.board.getSize()){
            gameState = GameState.DRAW;
        }

    }
}
