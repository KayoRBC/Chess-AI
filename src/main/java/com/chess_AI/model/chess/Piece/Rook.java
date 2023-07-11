package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

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
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Rook
                && toPiece != null
                && fromPiece.getColor() != toPiece.getColor()
                && (isHorizontalValid(board, move)
                    || isVerticalValid(board, move));

    }
}
