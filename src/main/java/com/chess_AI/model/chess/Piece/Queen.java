package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca da rainha, possui as regras de movimentacao e o estado da peca.
 */
public class Queen extends Piece{

    /**
     * Cria e retorna uma rainha de uma determinada cor.
     *
     * @param color Cor da rainha
     */
    public Queen(PieceColor color) {
        super(color, PieceType.QUEEN);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        return fromPiece instanceof Queen
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && (isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn)
                    || isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn)
                    || isVerticalValid(board, fromLine, fromColumn, toLine, toColumn));
    }
}
