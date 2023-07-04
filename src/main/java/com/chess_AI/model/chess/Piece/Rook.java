package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca da torre, possui as regras de movimentacao e o estado da peca.
 */
public class Rook extends Piece{

    /**
     * Cria e retorna uma torre de uma determinada cor.
     *
     * @param color Cor da torre
     */
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        return fromPiece instanceof Rook
                && toPiece != null
                && fromPiece.getColor() != toPiece.getColor()
                && (isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn)
                    || isVerticalValid(board, fromLine, fromColumn, toLine, toColumn));

    }
}
