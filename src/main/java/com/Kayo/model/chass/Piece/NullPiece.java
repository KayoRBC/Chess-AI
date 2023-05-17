package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceType;

public class NullPiece extends Piece{
    public NullPiece() {
        super(null, PieceType.NULL);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        return false;
    }

    @Override
    public Piece createClone() {
        Piece clone = new NullPiece();
        clone.setHasMoved(super.hasMoved());
        return clone;
    }
}
