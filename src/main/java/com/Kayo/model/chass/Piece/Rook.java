package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;
import com.Kayo.util.Util;

import java.util.stream.IntStream;

public class Rook extends Piece{
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
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
                // valido se movimento valido para horizontal ou vialido para vertical
                if(isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn)
                        || isVerticalValid(board, fromLine, fromColumn, toLine, toColumn)){
                    return true;
                }
            }
        }
        // se for movimento de roque
        return (isCastlingMove(board, fromLine, fromColumn, toLine, toColumn));
    }

    private boolean isVerticalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // se linhas diferentes e mesma coluna
        if(Math.abs(lineDistance) > 0 && columnDistance == 0){
            int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
            // se possuir linhas intermediarias
            if(intermediateLines != null){
                for(int i = 0; i < intermediateLines.length; i++){
                    Piece intermediate = board.getPiece(intermediateLines[i], fromColumn);
                    // se intermediario nao for uma peca vazia
                    if(intermediate.getType() != PieceType.NULL){
                        // movimento invalido
                        return false;
                    }
                }
            }
            // movimento valido
            return true;
        }
        // movimento invalido
        return false;
    }

    private boolean isHorizontalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // se colunas diferentes e mesma linha
        if(Math.abs(columnDistance) > 0 && lineDistance == 0){
            int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
            // se possuir linhas intermediarias
            if(intermediateColumns != null){
                for(int i = 0; i < intermediateColumns.length; i++){
                    Piece intermediate = board.getPiece(fromLine, intermediateColumns[i]);
                    // se intermediario nao for uma peca vazia
                    if(intermediate.getType() != PieceType.NULL){
                        // movimento invalido
                        return false;
                    }
                }
            }
            // movimento valido
            return true;
        }
        // movimento invalido
        return false;
    }

    // verificando o movimento Roque
    public boolean isCastlingMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null & toPiece != null) {
            // se as pecas forem da mesma cor e nao foram movidas ainda
            if (fromPiece.getColor() == toPiece.getColor() && !fromPiece.hasMoved() && !toPiece.hasMoved()) {
                // se a peca destino for o rei
                if (toPiece.getType() == PieceType.KING) {
                    // se for movimento horizontal e nao possuir pecas intermediarias
                    if (isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn)) {
                        // verificando se do rei a torre sao posicoes seguras
                        int[] columns;
                        if(fromColumn < toColumn) {
                            columns = IntStream.range(fromColumn, toColumn).toArray();
                        }
                        else{
                            columns = IntStream.range(toColumn, fromColumn).toArray();
                        }
                        for (int column : columns) {
                            // se a posicao nao for segura
                            if (board.isDungerousPosition(fromPiece.getColor(), fromLine, column)) {
                                // movimento invalido
                                return false;
                            }
                            // movimento valido
                            return true;
                        }
                    }

                }
            }
        }
        // movimento invalido
        return false;
    }
}
