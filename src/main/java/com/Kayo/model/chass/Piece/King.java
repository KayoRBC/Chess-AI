package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class King extends Piece{

    public King(PieceColor color) {
        super(color, PieceType.KING);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se as posicoes existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)){
            // valido se o movimento eh de uma casa para qualquer direcao
            return (isOneStep(fromLine, fromColumn, toLine, toColumn));
        }
        // movimento invalido
        return false;
    }

    private boolean isOneStep(int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // valido se o movimento eh de uma casa para qualquer direcao
        return ((Math.abs(lineDistance) == 1 || Math.abs(columnDistance) == 1)
                && (Math.abs(lineDistance) < 2 && Math.abs(columnDistance) < 2));

    }
}
