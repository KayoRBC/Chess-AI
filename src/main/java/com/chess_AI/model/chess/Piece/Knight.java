package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

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
    public boolean isValidMove(Board board, Move move) {
        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        boolean isLMovement = (verticalDistance == 2 && horizontalDistance == 1)
                                || (verticalDistance == 1 && horizontalDistance == 2);

        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Knight
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && isLMovement;
    }
}
