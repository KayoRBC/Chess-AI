package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca do bispo.
 */
public class Bishop extends Piece{

    /**
     * Cria e retorna um objeto de Bishop.
     *
     * @param color Cor do bispo.
     */
    public Bishop(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Bishop
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && hasNotDiagonalIntermediaries(board, move);
    }
}
