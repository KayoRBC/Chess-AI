package com.chess_AI.util;

/**
 * Esta classe armazena as possiveis cores das pecas e o sentido das pecas brancas no tabuleiro.
 */
public enum PieceColor {

    // cores das pecas
    WHITE, BLACK;

    /** se a cor branca eh iniciada na parte de cima do tabuleiro*/
    private static boolean isWhiteUp = true;

    /**
     * Retorna a cor do oponente de uma cor especifica.
     *
     * @param color Cor para pegar do oponente
     * @return Cor do oponente
     */
    public static PieceColor getOpponentOf(PieceColor color){
        if(color == WHITE){
            return BLACK;
        }
        else{
            return WHITE;
        }
    }

    /**
     * Retorna se a cor branca esta na parte de cima do tabuleiro.
     *
     * @return Se a cor branca esta na parte de cima do tabuleiro
     */
    public static boolean isWhiteUp(){
        return isWhiteUp;
    }

    /**
     * Atualiza se a cor branca esta na parte de cima do tabuleiro.
     *
     * @param isUp Se a cor branca esta na parte de cima do tabuleiro
     */
    public static void setIsWhiteUp(boolean isUp){
        isWhiteUp = isUp;
    }

}
