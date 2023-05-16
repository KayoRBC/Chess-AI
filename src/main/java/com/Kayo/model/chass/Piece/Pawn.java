package com.Kayo.model.chass.Piece;

import com.Kayo.model.chass.Board;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class Pawn extends Piece{

    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se as pecas existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)) {
            // se a direcao de movimentacao for certa
            if (verifyDirection(board, fromLine, fromColumn, toLine)) {
                Piece toPiece = board.getPiece(toLine, toColumn);
                // verificando se eh possivel fazer movimento para frente ate duas casas
                if(isVerticalValid(board, fromLine, fromColumn, toLine, toColumn, 2)) {
                    // se for duas casas para frente
                    if(Math.abs(fromLine - toLine) == 2){

                    }
                    // valido se peca destino eh vazia
                    return (toPiece.getType() == PieceType.NULL);
                }
                // verificando diagonal
                else if (isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn, 1)){
                    // valido se peca destino nao eh vazia
                    return (toPiece.getType() != PieceType.NULL);
                }
            }
        }
        // movimento invalido
        return false;
    }

    private boolean verifyDirection(Board board, int fromLine, int fromColumn, int toLine){
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        // se peca for branca
        if(fromPiece.getColor() == PieceColor.WHITE && fromLine - toLine > 0){
            // direcao certa
            return true;
        }
        // se peca for preta
        if(fromPiece.getColor() == PieceColor.BLACK && fromLine - toLine < 0){
            // direcao certa
            return true;
        }
        // dicrecao errada
        return false;
    }

}
