package com.chess_IA.model.chass.Piece;

import com.chess_IA.model.chass.Board;
import com.chess_IA.util.PieceColor;
import com.chess_IA.util.PieceType;

import java.util.stream.IntStream;

public class Rook extends Piece{
    public Rook(PieceColor color) {
        super(color, PieceType.ROOK);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se pecas existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)){
            // valido se for possivel mover para horizontal ou vertical
            return (isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn, 7)
                    ||isVerticalValid(board, fromLine, fromColumn, toLine, toColumn, 7));
        }
        // se for movimento de roque
        return (isCastlingMove(board, fromLine, fromColumn, toLine, toColumn));
    }

    // verificando o movimento Roque
    public boolean isCastlingMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn){
        // verificando se pecas existem e sao de cores iguais
        if(verifyPieces(board, true, fromLine, fromColumn, toLine, toColumn)){
            // pegando pecas
            Piece fromPiece = board.getPiece(fromLine, fromColumn);
            Piece toPiece = board.getPiece(toLine, toColumn);
            // se as pecas nao foram movidas ainda
            if (!fromPiece.hasMoved() && !toPiece.hasMoved()) {
                // se a peca destino for o rei
                if (toPiece.getType() == PieceType.KING) {
                    // se for movimento horizontal e nao possuir pecas intermediarias
                    if (isHorizontalValid(board, fromLine, fromColumn, toLine, toColumn, 4)) {
                        // valido se do rei ate a torre sao posicoes seguras
                        return isSafePositions(board, fromLine, fromColumn, toColumn, toPiece.getColor());
                    }

                }
            }
        }
        // movimento invalido
        return false;
    }

    private boolean isSafePositions(Board board, int line, int fromColumn, int toColumn, PieceColor allyColor){
        // verificando se fromColumn, toColumn e line então dentro de board
        if(valueInBoard(fromColumn) && valueInBoard(toColumn) && valueInBoard(line)) {
            // pegando valores de colunas
            int[] columns;
            if (fromColumn < toColumn) {
                columns = IntStream.range(fromColumn, toColumn).toArray();
            } else {
                columns = IntStream.range(toColumn, fromColumn).toArray();
            }

            // percorrendo colunas
            for (int column : columns) {
                // se a posicao nao for segura
                if (board.isDungerousPosition(allyColor, line, column)) {
                    // movimento invalido
                    return false;
                }
                // posicoes seguras
                return true;
            }
        }
        // posicoes perigoras
        return false;
    }

    @Override
    public Piece createClone() {
        Piece clone = new Rook(getColor());
        clone.setHasMoved(super.hasMoved());
        return clone;
    }
}
