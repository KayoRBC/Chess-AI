package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

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
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        return fromPiece instanceof Bishop
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn);
    }
}
