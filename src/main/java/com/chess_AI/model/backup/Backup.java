package com.chess_AI.model.backup;

import com.chess_AI.model.chass.Board;

public class Backup {

    // tabuleiro
    private final Board board;

    // se eh turno do usuario
    private final boolean isUserTurn;

    // se o oponente ganhou
    private final boolean isOpponentWon;

    // se o usuario ganhou
    private final boolean isUserWon;

    // se algum peao chegou no final do tabuleiro e precisa selecionar a troca de peca
    private final boolean isPawnChange;

    public Backup(Board board, boolean isUserTurn, boolean isOpponentWon, boolean isUserWon, boolean isPawnChange) {
        this.board = board.createClone(); // cria clone do tabuleiro atual
        this.isUserTurn = isUserTurn;
        this.isOpponentWon = isOpponentWon;
        this.isUserWon = isUserWon;
        this.isPawnChange = isPawnChange;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    public boolean isUserWon() {
        return isUserWon;
    }

    public boolean isPawnChange() {
        return isPawnChange;
    }
}
