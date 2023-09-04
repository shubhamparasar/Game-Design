package strategies.winningstrategies;

import models.Board;
import models.Move;
import models.Symbol;

import java.util.HashMap;
import java.util.Map;

public class ColumnWinningStrategy implements WinningStrategy{

    private final Map<Integer, HashMap<Symbol, Integer>> counts = new HashMap<>();
    @Override
    public boolean checkWinner(Board board, Move move) {

        int col = move.getCell().getCol();
        Symbol symbol = move.getPlayer().getSymbol();

        if(!counts.containsKey(col)){
            counts.put(col, new HashMap<>());
        }

        HashMap<Symbol, Integer> colMap = counts.get(col);

        if(!colMap.containsKey(symbol)){
            colMap.put(symbol, 0);
        }

        colMap.put(symbol, colMap.get(symbol) + 1);

        if(colMap.get(symbol).equals(board.getSize())){
            return true;
        }

        return false;
    }
}
