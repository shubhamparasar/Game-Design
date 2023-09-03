package models;

public class Move {

    private Player player;
    private Cell cell;


    public Move(Player player, Cell cell){
        this.cell = cell;
        this.player = player;
    }
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }
}
