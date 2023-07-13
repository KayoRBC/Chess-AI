package com.chess_AI.util;

/**
 * Esta classe armazena as possiveis cores das pecas e informacoes para serem utilizadas nas regras do tabuleiro de xadrez.
 */
public enum PieceColor {

    // cores das pecas
    WHITE, BLACK;

    /** O sentido das pecas brancas no tabuleiro.*/
    private static boolean isWhiteUp = true;

    /**
     * Retorna a cor do oponente de uma cor especifica.
     *
     * @param color Cor para pegar do oponente.
     * @return Cor do oponente.
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
     * Pega a posicao da linha final de uma determinada cor.
     *
     * @param color Cor para pegar a posicao da linha final.
     * @return Posicao da linha final.
     */
    public static int getFinalLineOf(PieceColor color){
        int line;
        if(isWhiteUp){
            if(color == PieceColor.WHITE) line = 7;
            else line = 0;
        }
        else{
            if(color == PieceColor.WHITE) line = 0;
            else line = 7;
        }
        return line;
    }

    /**
     * Retorna se a cor branca esta na parte de cima do tabuleiro.
     *
     * @return Se a cor branca esta na parte de cima do tabuleiro.
     */
    public static boolean isWhiteUp(){
        return isWhiteUp;
    }

    /**
     * Atualiza se a cor branca esta na parte de cima do tabuleiro.
     *
     * @param isUp Se a cor branca esta na parte de cima do tabuleiro.
     */
    public static void setIsWhiteUp(boolean isUp){
        isWhiteUp = isUp;
    }
}
