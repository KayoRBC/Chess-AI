package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

/**
 * Esta classe representa uma posicao vazia do tabuleiro.
 */
public class NullPiece extends Piece{

    /**
     * Cria e retorna uma peca vazia.
     */
    public NullPiece() {
        super(null, PieceType.NULL);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        return false;
    }
}
