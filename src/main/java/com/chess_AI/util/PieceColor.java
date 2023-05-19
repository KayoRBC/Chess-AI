package com.chess_AI.util;

// representa as cores das pecas
public enum PieceColor {
    WHITE, BLACK;

    // se a cor branca eh iniciada na parte de cima do tabuleiro
    private static boolean isWhiteUp = true;

    public static PieceColor getOpponentOf(PieceColor color){
        if(color == WHITE){
            return BLACK;
        }
        else{
            return WHITE;
        }
    }

    public static boolean isWhiteUp(){
        return isWhiteUp;
    }

    public static void setIsWhiteUp(boolean isUp){
        isWhiteUp = isUp;
    }

}
