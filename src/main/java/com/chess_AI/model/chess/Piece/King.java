package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca do rei, possui as regras de movimentacao e o estado da peca.
 */
public class King extends Piece{

    /**
     * Cria e retorna um rei de uma determinada cor.
     *
     * @param color Cor do rei
     */
    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        int horizontalDistance = Math.abs(fromColumn - toColumn);
        int verticalDistance = Math.abs(fromLine - toLine);

        boolean isOneStep = (horizontalDistance == 1 || verticalDistance == 1)
                            && horizontalDistance < 2 && verticalDistance < 2;

        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        return fromPiece instanceof King
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isOneStep;
    }
}
