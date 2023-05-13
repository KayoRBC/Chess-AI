package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;
import com.Kayo.util.Util;

public class Bishop extends Piece{
    public Bishop(PieceColor color) {
        super(color, PieceType.BISHOP);
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
                // valido se movimento valido diagonal
                return isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn);
            }
        }
        // movimento invalido
        return false;
    }

    private boolean isDiagonalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // se o movimento eh diagonal
        if(isDiagonalMove(fromLine, fromColumn, toLine, toColumn)){
            int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
            int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
            // valido se as pecas intermedias sao validas
            return isIntermediateValid(board, intermediateLines, intermediateColumns);
        }
        // movimento invalido
        return false;
    }

    private boolean isDiagonalMove(int fromLine, int fromColumn, int toLine, int toColumn){
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // valido se a distancia entre as linhas eh igual a distancia entre as colunas
        return (Math.abs(lineDistance) == Math.abs(columnDistance));
    }

    private boolean isIntermediateValid(Board board, int[] intermediateLines, int[] intermediateColumns){
        // se existir posicoes intermediarias ao movimento
        if(intermediateLines != null && intermediateColumns != null){
            // verifica se alguma peca intermediaria nao eh vazia
            for(int i = 0; i < intermediateLines.length; i++){
                int line = intermediateLines[i];
                int column = intermediateColumns[i];
                Piece piece = board.getPiece(line, column);
                // se nao for vazia
                if(piece.getType() != PieceType.NULL){
                    // movimento invalido
                    return false;
                }
            }
        }
        // valido
        return true;
    }

    /*
    private boolean isIntermediateValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // se o movimento for diagonal
        if(isDiagonalMove(fromLine, fromColumn, toLine, toColumn)){
            int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
            int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
            // se existir posicoes intermediarias ao movimento
            if(intermediateLines != null && intermediateColumns != null){
                // verifica se alguma peca intermediaria eh vazia
                for(int i = 0; i < intermediateLines.length; i++){
                    int line = intermediateLines[i];
                    int column = intermediateColumns[i];
                    Piece piece = board.getPiece(line, column);
                    // se nao for vazia
                    if(piece.getType() != PieceType.NULL){
                        // movimento invalido
                        return false;
                    }
                }
            }
            // se nao existir posicoes intermedirias
            return true;
        }

        // movimento invalido
        return false;
    }

     */
}
