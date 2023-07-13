package com.chess_AI.view.gameScreen;

import com.chess_AI.util.Move;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta interface eh responsavel por mostrar no terminal o historico do jogo.
 */
public interface History {

    /**
     * Mostra no terminal que o jogo foi iniciado.
     */
    static void printStart(){
        System.out.println("-----------------------------------------------------");
        System.out.println("Game starting!");
    }

    /**
     * Mostra no terminal a cor da peca de alguem.
     *
     * @param isUser Se eh o usuario mostrando.
     * @param color Cor para mostrar.
     */
    static void printColor(boolean isUser, PieceColor color){
        printSubject(isUser);
        System.out.println("Color: "+color);
    }

    /**
     * Mostra no terminal uma movimentacao.
     *
     * @param move Movimentacao.
     * @param isUser Se eh o usuario realizando a movimento.
     * @param fromPiece Peca na posicao de origem.
     */
    static void printMove(Move move, Boolean isUser, PieceType fromPiece){
        System.out.println("-----------------------------------------------------");
        printSubject(isUser);

        System.out.print("Moving piece "+fromPiece+" ");
        System.out.print("from LINE "+move.FROM.LINE+" COLUMN "+move.FROM.COLUMN+" ");
        System.out.println("to LINE "+move.TO.LINE+" COLUMN "+move.TO.COLUMN);
    }

    /**
     * Avisa no terminal se um movimento foi realizado ou nao.
     *
     * @param isUser Se eh o usuario que realizou o movimento.
     * @param hasBeenCompleted Se conseguiu realizar o movimento.
     */
    static void printIsCompletedMove(Boolean isUser, Boolean hasBeenCompleted){
        printSubject(isUser);

        if(hasBeenCompleted) System.out.println("Movement has been completed!");
        else System.out.println("Movement has not been completed!");
    }

    /**
     * Avisa no terminal a troca de um peao por um novo tipo de peca.
     *
     * @param isUser Se eh o usuario realizando a troca.
     * @param newType Novo tipo no lugar do peao.
     */
    static void printPawnPromote(Boolean isUser, PieceType newType){
        printSubject(isUser);

        System.out.println("Promoted the "+PieceType.PAWN+" for a "+newType);
    }

    /**
     * Avisa no terminal que um rei esta em check.
     *
     * @param isUser Se eh o usuario avisando o check.
     */
    static void printCheck(Boolean isUser){
        printSubject(isUser);

        if(isUser) System.out.println("Check on the AI "+PieceType.KING);
        else System.out.println("Check on the User "+PieceType.KING);
    }

    /**
     * Mostra no terminal o tempo decorrido.
     *
     * @param isUser Se eh o usuario mostrando.
     * @param start Tempo inicial em millissegundo.
     * @param end Tempo final em millissegundo.
     */
    static void printTime(Boolean isUser, long start, long end){
        printSubject(isUser);
        long delta = end - start;
        System.out.println("Elapsed time: "+delta+" seconds");
    }

    /**
     * Avisa no terminal que alguem venceu.
     *
     * @param isUser Se eh o usuario que venceu.
     */
    static void printWon(Boolean isUser){
        if(isUser) System.out.println("User won!");
        else System.out.println("AI won!");
    }

    /**
     * Mostra no terminal que a IA nao conseguiu predizer o movimento.
     */
    static void printAIError(){
        System.out.println("-----------------------------------------------------");
        printSubject(false);
        System.out.println("Cannot predict movement!");
    }

    /**
     * Mostra no inicio da linha quem esta mandando a mensagem.
     *
     * @param isUser Se eh o usuario.
     */
    private static void printSubject(boolean isUser){
        if(isUser) System.out.print("[USER] - ");
        else System.out.print("[AI] - ");
    }
}
