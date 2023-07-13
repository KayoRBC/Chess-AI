package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca da rainha.
 */
public class Queen extends Piece{

    /**
     * Cria e retorna um objeto de Queen.
     *
     * @param color Cor da rainha.
     */
    public Queen(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Queen
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && (hasNotDiagonalIntermediaries(board, move)
                    || hasNotHorizontalIntermediaries(board, move)
                    || hasNotVerticalIntermediaries(board, move));
    }
}
