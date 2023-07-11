package com.chess_AI.model.ai;

import com.chess_AI.controller.BoardController;
import com.chess_AI.util.Move;

/**
 * Esta classe representa o No na arvore de busca do AlphaBeta, possui a movimentacao que levou a um estado no tabuleiro
 * e o valor da heuristica.
 */
public class Node {

    /** Movimentacao realizada*/
    public Move move;

    /** Valor da heuristica*/
    public double value;

    /** Tabuleiro para aplicar as movimentacoes*/
    public final BoardController BOARD_CONTROLLER;

    /**
     * Cria e retorna um objeto de Node.
     *
     * @param move Movimento realizado
     * @param value Valor da heuristica resultante
     * @param boardController Tabuleiro que aplicou o movimento
     */
    public Node(Move move, double value, BoardController boardController){
        this.move = move;
        this.value = value;
        BOARD_CONTROLLER = boardController;
    }
}
