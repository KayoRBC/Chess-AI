package com.chess_IA.model.chass.Piece;

import com.chess_IA.model.chass.Board;
import com.chess_IA.util.PieceColor;
import com.chess_IA.util.PieceType;

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
                    if(toPiece.getType() == PieceType.NULL){
                        Piece fromPiece = board.getPiece(fromLine, fromColumn);
                        // se for duas casas para frente e a peca ja foi movimentada
                        if(Math.abs(fromLine - toLine) == 2 && fromPiece.hasMoved()){
                            // invalido
                            return false;
                        }
                        // valido
                        return true;
                    }
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
        if(fromPiece.getColor() == PieceColor.WHITE){
            // se branca comeca por cima | valido se estar se movimentando para baixo
            if(PieceColor.isWhiteUp()) return fromLine - toLine < 0;
            // se branca comeca por baixo | valido se estar se movimentando para cima
            else return fromLine - toLine > 0;
        }
        // se peca for preta
        else{
            // se branca comeca por cima | valido se estar se movimentando para cima
            if(PieceColor.isWhiteUp()) return fromLine - toLine > 0;
            // se branca comeca por baixo | valido se estar se movimentando para baixo
            else return fromLine - toLine < 0;
        }
    }

    @Override
    public Piece createClone() {
        Piece clone = new Pawn(getColor());
        clone.setHasMoved(super.hasMoved());
        return clone;
    }
}
