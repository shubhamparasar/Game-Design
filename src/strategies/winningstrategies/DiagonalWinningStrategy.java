package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;

public class DiagonalWinningStrategy implements WinningStrategy{

    private HashMap<Symbol, Integer> leftDiagCounts = new HashMap<>();
    private HashMap<Symbol, Integer> rightDiagCounts = new HashMap<>();

    @Override
    public boolean checkWinner(Board board, Move move) {
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();
        if(row ==  col){

            if(!leftDiagCounts.containsKey(symbol)){
                leftDiagCounts.put(symbol, 0);
            }

            leftDiagCounts.put(symbol,leftDiagCounts.get(symbol) + 1);
        }

        if(row + col == board.getSize() - 1){

            if(!rightDiagCounts.containsKey(symbol)){
                rightDiagCounts.put(symbol, 0);
            }

            rightDiagCounts.put(symbol, rightDiagCounts.get(symbol) + 1);
        }

        if(row == col){
            if(leftDiagCounts.get(symbol).equals(board.getSize())){
                return true;
            }
        }

        if(row + col == board.getSize() - 1){
            if(rightDiagCounts.get(symbol).equals(board.getSize())){
                return true;
            }
        }
        return false;
    }
}
