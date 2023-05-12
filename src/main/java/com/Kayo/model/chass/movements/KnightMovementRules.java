package com.Kayo.model.chass.movements;

import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.Piece;

public class KnightMovementRules extends MovementRules{
    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // pegando pecas do tabuleiro
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        // verificando se as pecas existem
        if(fromPiece == null || toPiece == null){
            return false;
        }

        // verificando se a cor das pecas sao diferentes
        if(fromPiece.getColor() == toPiece.getColor()) return false;

        // distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;

        // verificando movimento em L
        if(Math.abs(lineDistance) == 2 && Math.abs(columnDistance) == 1){
            return true;
        }
        else if(Math.abs(lineDistance) == 1 && Math.abs(columnDistance) == 2){
            return true;
        }
        return false;
    }
}
