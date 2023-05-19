package com.chess_AI.model.chass.Piece;

import com.chess_AI.model.chass.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Util;

public abstract class Piece{
    protected final PieceColor COLOR;
    protected final PieceType TYPE;
    protected boolean hasMoved = false;

    public Piece(PieceColor color, PieceType pieceType){
        this.COLOR = color;
        this.TYPE = pieceType;
    }

    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);

    public abstract Piece createClone();

    public PieceColor getColor() {
        return COLOR;
    }

    public PieceType getType() {
        return TYPE;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    // verifica se as pecas existem e comparando as cores entre elas
    // return true caso tudo certo
    // return false caso algo de errado
    protected boolean verifyPieces(Board board, boolean isSameColor, int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null && toPiece != null){
            // se as pecas precisas ser da mesma coisa e sao da mesma cor
            if((isSameColor && fromPiece.getColor() == toPiece.getColor())
                    // ou se as pecas precisam ser de cores diferentes e sao de cores diferentes
                    || (!isSameColor && fromPiece.getColor() != toPiece.getColor())){
                // valido
                return true;
            }
        }
        // invalido
        return false;
    }

    // verifica se o valor esta dentro de board
    protected boolean valueInBoard(int value){
        return -1 < value && value < 8;
    }

    // verifica se exitem pecas intermediarias na vertical de uma posicao para a outra
    // return true caso nao tenha pecas intermediarias
    // return false caso tenha
    protected boolean isVerticalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance){
        // se distancia for maior que 0
        if(maxDistance > 0) {
            // se fromValue, toValue e constantValue estao dentro do tabuleiro
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se vertical
                if (Math.abs(fromLine - toLine) <= maxDistance && Math.abs(fromColumn - toColumn) == 0) {
                    // criando valores das linhas intermediaras
                    int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
                    // se existir intermediarios
                    if(intermediateLines != null) {
                        // percorrendo posicoes intermediaras
                        for (int line : intermediateLines) {
                            // pegando peca
                            Piece piece = board.getPiece(line, fromColumn);
                            // se nao for uma peca vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }

    // verifica se exitem pecas intermediarias na horizontal de uma posicao para a outra
    // return true caso nao tenha pecas intermediarias
    // return false caso tenha
    protected boolean isHorizontalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance) {
        // se distacia for maior que 0
        if(maxDistance > 0) {
            // se fromValue, toValue e constantValue estao dentro do tabuleiro
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se horizontal
                if (Math.abs(fromLine - toLine) == 0 && Math.abs(fromColumn - toColumn) <= maxDistance) {
                    // criando valores das colunas intermediaras
                    int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
                    // se exisitir intermediarios
                    if(intermediateColumns != null) {
                        // percorrendo posicoes intermediaras
                        for (int column : intermediateColumns) {
                            // pegando peca
                            Piece piece = board.getPiece(fromLine, column);
                            // se nao for uma peca vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }

    // verifica se exitem pecas intermediarias na diagonal de uma posicao para a outra
    // return true caso nao tenha pecas intermediarias
    // return false caso tenha
    protected boolean isDiagonalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance) {
        // se distancia for maior que 0
        if(maxDistance > 0) {
            // se as pecas existirem
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se as distancias forem iguais (diagonal) e iguais a distancia
                if (Math.abs(fromLine - toLine) == Math.abs(fromColumn - toColumn) && Math.abs(fromLine - toLine) <= maxDistance) {
                    // criando valores intermediarios
                    int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
                    int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
                    // se existir posicoes intermediarias ao movimento
                    if (intermediateLines != null && intermediateColumns != null) {
                        // verifica se alguma peca intermediaria nao eh vazia
                        for (int i = 0; i < intermediateLines.length; i++) {
                            int line = intermediateLines[i];
                            int column = intermediateColumns[i];
                            Piece piece = board.getPiece(line, column);
                            // se nao for vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }
}
