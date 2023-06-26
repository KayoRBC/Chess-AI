package com.chess_AI.model.chass.Piece;

import com.chess_AI.model.chass.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a peca do rei, possui as regras de movimentacao e o estado da peca.
 */
public class King extends Piece{

    /**
     * Cria e retorna um rei de uma determinada cor
     *
     * @param color Cor do rei
     */
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

    @Override
    public Piece createClone() {
        Piece clone = new King(getColor());
        clone.setHasMoved(super.hasMoved());
        return clone;
    }

    /**
     * Verifica se eh a peca esta movimentando uma casa para qualquer posicao
     *
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna
     * @return Se a movimentacao eh de uma casa
     */
    private boolean isOneStep(int fromLine, int fromColumn, int toLine, int toColumn){
        // calculando distancias
        int lineDistance = toLine - fromLine;
        int columnDistance = toColumn - fromColumn;
        // valido se o movimento eh de uma casa para qualquer direcao
        return ((Math.abs(lineDistance) == 1 || Math.abs(columnDistance) == 1)
                && (Math.abs(lineDistance) < 2 && Math.abs(columnDistance) < 2));

    }
}
