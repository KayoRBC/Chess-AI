package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class Pawn extends Piece{

    private final int VERTICAL_DIRECTION;

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
        // se peca for branca
        if(getColor() == PieceColor.WHITE){
            VERTICAL_DIRECTION = -1; // CIMA
        }
        // se peca for preta
        else{
            VERTICAL_DIRECTION = 1; // BAIXO
        }
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // pegando pecas do tabuleiro
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null && toPiece != null){
            // se a cor das pecas forem diferentes
            if(fromPiece.getColor() != toPiece.getColor()){
                // valido se movimento valido para frente ou diagonal
                return (isVerticalValid(board, fromLine, fromColumn, toLine, toColumn)
                        || isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn));
            }
        }
        // movimento invalido
        return false;
    }

    private boolean isVerticalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas do tabuleiro
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // se for na mesma coluna e peca destino for vazia
        if(columnDistance == 0 && toPiece.getType() == PieceType.NULL) {
            // se um para frente
            if (lineDistance == VERTICAL_DIRECTION) {
                return true;
            }
            // se dois para frente
            else if (!fromPiece.hasMoved() && lineDistance == 2 * VERTICAL_DIRECTION) {
                Piece intermediatePiece = board.getPiece(fromLine + VERTICAL_DIRECTION, fromColumn);
                // se peca intermediaria for vazia
                if(intermediatePiece.getType() == PieceType.NULL){
                    return true;
                }
            }
        }
        // invalido
        return false;
    }

    private boolean isDiagonalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // se direcao vertial certa e movimento de uma casa para a diagonal
        if(lineDistance == VERTICAL_DIRECTION && Math.abs(columnDistance) == 1){
            Piece toPiece = board.getPiece(toLine, toColumn);
            // valido se o destino possui uma peca nao vazia
            return (toPiece.getType() != PieceType.NULL);
        }
        // invalido
        return false;
    }

}
