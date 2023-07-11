package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

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
    public boolean isValidMove(Board board, Move move) {
        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        boolean isOneStep = (horizontalDistance == 1 || verticalDistance == 1)
                            && horizontalDistance < 2 && verticalDistance < 2;

        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof King
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isOneStep;
    }
}
