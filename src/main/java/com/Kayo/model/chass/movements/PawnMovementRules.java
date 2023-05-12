package com.Kayo.model.chass.movements;

import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.NullPiece;
import com.Kayo.model.chass.Piece.Piece;
import com.Kayo.util.PieceColor;


public class PawnMovementRules extends MovementRules{

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

        // direcao representada pelo sinal positivo para baixo, negativo para cima
        int direction = 1;
        if(fromPiece.getColor() == PieceColor.WHITE){
            direction = -1;
        }

        // distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;

        // movimento para frente
        if(columnDistance == 0 && toPiece instanceof NullPiece) {
            // um para frente
            if (lineDistance == direction) {
                return true;
            }
            // dois para frente
            else if (!fromPiece.isHasMoved() && lineDistance == 2 * direction) {
                // verificando se nao tem peca intermediaria
                Piece intermediatePiece = board.getPiece(fromLine + direction, fromColumn);
                if(intermediatePiece instanceof NullPiece){
                    return true;
                }
            }
        }

        // movimento diagonal
        else if(!(toPiece instanceof NullPiece) && lineDistance == direction && Math.abs(columnDistance) == 1){
            return true;
        }

        // nao foi possivel mover
        return false;

    }
}
