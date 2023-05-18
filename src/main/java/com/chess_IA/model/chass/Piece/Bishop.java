package com.chess_IA.model.chass.Piece;

import com.chess_IA.model.chass.Board;
import com.chess_IA.util.PieceColor;
import com.chess_IA.util.PieceType;

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
