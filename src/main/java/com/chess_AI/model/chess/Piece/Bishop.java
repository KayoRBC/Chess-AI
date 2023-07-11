package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca do bispo, possui as regras de movimentacao e o estado da peca.
 */
public class Bishop extends Piece{

    /**
     * Cria e retorna um bispo de uma determinada cor.
     *
     * @param color Cor do bispo
     */
    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Bishop
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isDiagonalValid(board, move);
    }
}
