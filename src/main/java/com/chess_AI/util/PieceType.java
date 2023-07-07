package com.chess_AI.util;

import java.util.Random;

/**
 * Esta classe armazena os possiveis tipos de pecas no tabuleiro.
 */
public enum PieceType {
    ROOK, KNIGHT, BISHOP, QUEEN, KING, PAWN, NULL;

    /**
     * Retorna um tipo de peca aleatorio, podendendo ate ser uma vazia(NULL).
     *
     * @return Tipo de peca
     */
    public static PieceType getRand(){
        Random random = new Random();
        PieceType[] types = PieceType.values();

        return types[random.nextInt(types.length)];
    }
}
