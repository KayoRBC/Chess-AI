package com.Kayo.model.backup;

import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.Piece;

public class Backup {
    private final Board board;

    private final boolean isUserTurn;

    private final boolean isOpponentWon;
    private final boolean isUserWon;

    private final boolean isPawnChange;

    public Backup(Board board, boolean isUserTurn, boolean isOpponentWon, boolean isUserWon, boolean isPawnChange) {
        this.board = board.createClone();
        this.isUserTurn = isUserTurn;
        this.isOpponentWon = isOpponentWon;
        this.isUserWon = isUserWon;
        this.isPawnChange = isPawnChange;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    public boolean isUserWon() {
        return isUserWon;
    }

    public boolean isPawnChange() {
        return isPawnChange;
    }
}
