package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca do cavalo, possui as regras de movimentacao e o estado da peca.
 */
public class Knight extends Piece{

    /**
     * Cria e retorna um cavalo de uma determinada cor.
     *
     * @param color Cor do cavalo
     */
    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        int horizontalDistance = Math.abs(fromColumn - toColumn);
        int verticalDistance = Math.abs(fromLine - toLine);

        boolean isLMovement = (verticalDistance == 2 && horizontalDistance == 1)
                                || (verticalDistance == 1 && horizontalDistance == 2);

        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        return fromPiece instanceof Knight
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isLMovement;
    }
}
