package com.Kayo.model.movements;

import com.Kayo.model.Board;
import com.Kayo.model.Piece.NullPiece;
import com.Kayo.model.Piece.Piece;
import com.Kayo.util.Util;

public class RookMovementRules extends MovementRules{
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

        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;

        if(Math.abs(lineDistance) > 0 && columnDistance == 0){
            int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
            if(intermediateLines == null){
                return true;
            }
            else{
                for(int i = 0; i < intermediateLines.length; i++){
                    Piece piece = board.getPiece(intermediateLines[i], fromColumn);
                    // se existir uma peca no meio
                    if(!(piece instanceof NullPiece)){
                        return false;
                    }
                }
                return true;
            }
        }
        else if(Math.abs(columnDistance) > 0 && lineDistance == 0){
            int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
            if(intermediateColumns == null){
                return true;
            }
            else{
                for(int i = 0; i < intermediateColumns.length; i++){
                    Piece piece = board.getPiece(fromLine, intermediateColumns[i]);
                    // se existir alguma peca no meio
                    if(!(piece instanceof NullPiece)){
                        return false;
                    }
                }
                return true;
            }
        }
        // nao foi possivel movimentar
        return false;
    }
}
