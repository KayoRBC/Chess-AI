package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;
import com.Kayo.util.Util;

public abstract class Piece {
    protected final PieceColor COLOR;
    protected final PieceType TYPE;
    protected boolean hasMoved = false;

    public Piece(PieceColor COLOR, PieceType PIECE_TYPE){
        this.COLOR = COLOR;
        this.TYPE = PIECE_TYPE;
    }

    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);

    public PieceColor getColor() {
        return COLOR;
    }

    public PieceType getType() {
        return TYPE;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
