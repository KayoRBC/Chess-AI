package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

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
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        return fromPiece instanceof Queen
                && toPiece != null
                && toPiece.getColor() != fromPiece.getColor()
                && (isDiagonalValid(board, move)
                    || isHorizontalValid(board, move)
                    || isVerticalValid(board, move));
    }
}
