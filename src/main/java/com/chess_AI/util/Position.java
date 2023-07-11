package com.chess_AI.util;

/**
 * Esta classe representa uma posicao em um espaco bidimensional.
 */
public class Position {

    /** Valor da linha*/
    public final int LINE;

    /** Valor da coluna*/
    public final int COLUMN;

    /**
     * Cria e retorna objeto de Position
     *
     * @param line Linha que esta a posicao
     * @param column Coluna que esta a posicao
     */
    public Position(int line, int column){
        LINE = line;
        COLUMN = column;
    }
}
