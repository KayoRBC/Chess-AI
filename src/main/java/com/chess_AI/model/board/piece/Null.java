package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.Move;

/**
 * Esta classe representa uma peca vazia.
 */
public class Null extends Piece{

    /**
     * Cria e retorna um objeto de Null.
     */
    public Null() {
        super(null);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        return false;
    }
}
