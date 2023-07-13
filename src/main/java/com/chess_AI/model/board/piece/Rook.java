package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca da torre.
 */
public class Rook extends Piece{

    /**
     * Cria e retorna objeto de Rook.
     *
     * @param color Cor da torre.
     */
    public Rook(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Rook
                && toPiece != null
                && fromPiece.getColor() != toPiece.getColor()
                && (hasNotHorizontalIntermediaries(board, move)
                    || hasNotVerticalIntermediaries(board, move));

    }
}
