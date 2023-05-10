package com.Kayo.model.movements;

import com.Kayo.model.Board;
import com.Kayo.model.Piece.NullPiece;
import com.Kayo.model.Piece.Piece;

public class KingMovementRules extends MovementRules{
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
        // se o movimento eh de uma casa para qualquer direcao
        if(Math.abs(lineDistance) == 1 || Math.abs(columnDistance) == 1){
            if(toPiece instanceof NullPiece){
                // verificando se a casa ja eh dominada pela peca inimiga
                if(board.hasPositionDominated(fromPiece.getColor(), toLine, toColumn)){
                    return false;
                }
            }
            // caso a casa que vai ir tenha uma peca inimiga
            return true;
        }
        // caso o movimento nao for de 1 casa
        return false;
    }
}
