package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class Knight extends Piece{
    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
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
                // verifica se o movimento eh em L
                return isLValid(fromLine, fromColumn, toLine, toColumn);
            }
        }
        // movimento invalido
        return false;
    }

    private boolean isLValid(int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // valido se movimento for em L
        return ((Math.abs(lineDistance) == 2 && Math.abs(columnDistance) == 1) ||
                (Math.abs(lineDistance) == 1 && Math.abs(columnDistance) == 2));
    }
}
