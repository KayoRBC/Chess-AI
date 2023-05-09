package com.Kayo.model.movements;

import com.Kayo.model.Board;
import com.Kayo.model.Piece.NullPiece;
import com.Kayo.model.Piece.Piece;

public class BishopMovementRules extends MovementRules{
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
        if(Math.abs(lineDistance) == Math.abs(columnDistance)){
            // verificando pecas intermediarias
            int[] intermediateLines = createIntermediateValues(fromLine, toLine);
            int[] intermediateColumns = createIntermediateValues(fromColumn, toColumn);
            if(intermediateLines == null || intermediateColumns == null){
                return true;
            }
            else {
                for(int i = 0; i < intermediateLines.length; i++){
                    int line = intermediateLines[i];
                    int column = intermediateColumns[i];
                    Piece piece = board.getPiece(line, column);
                    if(!(piece instanceof NullPiece)){
                        return false;
                    }
                }
                return true;
            }
        }

        // nao foi possivel mover
        return false;
    }

    private int[] createIntermediateValues(int start, int end){
        int intermediateLength = Math.abs(start - end) - 2;
        if(intermediateLength > 0){
            int[] numbers = new int[Math.abs(intermediateLength)];
            if(start < end) {
                int num = start + 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num++;
                }
            }
            else{
                int num = start - 1;
                for (int i = 0; i < numbers.length; i++) {
                    numbers[i] = num;
                    num--;
                }
            }
            return numbers;
        }
        return null;
    }
}
