package com.chess_AI.model.chass.Piece;

import com.chess_AI.model.chass.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca do cavalo, possui as regras de movimentacao e o estado da peca.
 */
public class Knight extends Piece{

    /**
     * Cria e retorna um cavalo de uma determinada cor
     *
     * @param color Cor do cavalo
     */
    public Knight(PieceColor color) {
        super(color, PieceType.KNIGHT);
    }

    @Override
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        // se as posicoes existirem e forem de cores diferentes
        if(verifyPieces(board, false, fromLine, fromColumn, toLine, toColumn)){
            // valido se movimento em L
            return isLValid(fromLine, fromColumn, toLine, toColumn);
        }
        // movimento invalido
        return false;
    }

    @Override
    public Piece createClone() {
        Piece clone = new Knight(getColor());
        clone.setHasMoved(super.hasMoved());
        return clone;
    }

    /**
     * Verifica se a peca esta fazendo movimento em L
     *
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna de destino
     * @return Se a peca esta fazendo movimento em L
     */
    private boolean isLValid(int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // valido se movimento for em L
        return ((Math.abs(lineDistance) == 2 && Math.abs(columnDistance) == 1) ||
                (Math.abs(lineDistance) == 1 && Math.abs(columnDistance) == 2));
    }
}
