package com.chess_AI.model.chass.Piece;

import com.chess_AI.model.chass.Board;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa uma posicao vazia do tabuleiro.
 */
public class NullPiece extends Piece{

    /**
     * Cria e retorna uma peca vazia
     */
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
