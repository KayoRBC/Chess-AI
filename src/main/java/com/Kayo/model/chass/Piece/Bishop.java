package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;
import com.Kayo.util.Util;

public class Bishop extends Piece{
    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se as posicoes existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)){
            // se ter apenas pecas intermediaris vazias
            if(isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn, 7)){
                return true;
            }
        }

        // movimento invalido
        return false;
    }

    @Override
    public Piece createClone() {
        Piece clone = new Bishop(getColor());
        clone.setHasMoved(super.hasMoved());
        return clone;
    }
}
