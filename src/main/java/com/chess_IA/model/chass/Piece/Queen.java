package com.chess_IA.model.chass.Piece;

import com.chess_IA.model.chass.Board;
import com.chess_IA.util.PieceColor;
import com.chess_IA.util.PieceType;

public class Queen extends Piece{
    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se as pecas existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)){
            // valido se possivel fazer movimento na diagonal, vertical ou horizontal
            return (isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn, 7)
                    ||isVerticalValid(board, fromLine, fromColumn, toLine, toColumn, 7)
                    ||isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn, 7));
        }
        // movimento invalido
        return false;
    }

    @Override
    public Piece createClone() {
        return new Queen(getColor());
    }
}
