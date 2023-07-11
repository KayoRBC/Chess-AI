package com.chess_AI.util;

/**
 * Esta classe representa uma movimentacao de uma posicao ate outra.
 */
public class Move {

    /** Posicao de origem*/
    public final Position FROM;

    /** Posicao de destino*/
    public final Position TO;

    /**
     * Cria e retorna um objeto de Move
     *
     * @param fromLine Linha de origem
     * @param fromColumn Coluna de origem
     * @param toLine Linha de destino
     * @param toColumn Coluna de destino
     */
    public Move(int fromLine, int fromColumn, int toLine, int toColumn){
        FROM = new Position(fromLine, fromColumn);
        TO = new Position(toLine, toColumn);
    }

    /**
     * Cria e retorna um objeto de Move
     *
     * @param from Posicao de origem
     * @param to Posicao de destino
     */
    public Move(Position from, Position to){
        FROM = from;
        TO = to;
    }
}
