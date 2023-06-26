package com.chess_AI.model.backup;

import com.chess_AI.model.chass.Board;

/**
 * Esta classe representa um backup de um estado do tabuleiro
 */
public class Backup {

    /** Tabuleiro*/
    private final Board board;

    /** Se eh o turno do usuario*/
    private final boolean isUserTurn;

    /** Se o oponente venceu*/
    private final boolean isOpponentWon;

    /** Se o usuario venceu*/
    private final boolean isUserWon;

    /** Se algum pecao chegou no final do tabuleiro*/
    private final boolean isPawnChange;

    /**
     * Cria e retorna um backup de um tabuleiro
     *
     * @param board Tabuleiro
     * @param isUserTurn Se eh o turno do usario
     * @param isOpponentWon Se o oponente venceu
     * @param isUserWon Se o usuario venceu
     * @param isPawnChange Se algum peao chegou no final do tabuleiro
     */
    public Backup(Board board, boolean isUserTurn, boolean isOpponentWon, boolean isUserWon, boolean isPawnChange) {
        this.board = board.createClone(); // cria clone do tabuleiro atual
        this.isUserTurn = isUserTurn;
        this.isOpponentWon = isOpponentWon;
        this.isUserWon = isUserWon;
        this.isPawnChange = isPawnChange;
    }

    /**
     * Retorna o estado do tabuleiro
     *
     * @return Tabuleiro
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Retorna se eh o turno do oponente
     *
     * @return Se eh o turno do oponente
     */
    public boolean isUserTurn() {
        return isUserTurn;
    }

    /**
     * Retorna se o oponente venceu
     *
     * @return Se o oponente venceu
     */
    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    /**
     * Retorna se o usuario venceu
     *
     * @return Se o usuario venceu
     */
    public boolean isUserWon() {
        return isUserWon;
    }

    /**
     * Retorna se algum peao chegou no final
     *
     * @return Se algum peao chegou no final
     */
    public boolean isPawnChange() {
        return isPawnChange;
    }
}
