package com.chess_AI.controller;

import com.chess_AI.model.ai.AlphaBeta;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Move;

/**
 * Esta interface representa o controller do model AI.
 */
public interface AIController {

    /**
     * Faz a IA predizer a melhor jogada para um tabuleiro de xadrez utilizando AlphaBeta.
     * Usar quando for o turno da IA.
     *
     * @param AIColor cor da peca da IA no tabuleiro de xadrez.
     * @param boardController Tabuleiro para aplicar os movimentos
     * @param maxDepth Maxima profundidade de busca do AlphaBeta
     *
     * @return A melhor jogada. Se nao encontrar retorna null.
     */
    static Move predict(PieceColor AIColor, BoardController boardController, int maxDepth){
        return AlphaBeta.search(AIColor, boardController, maxDepth, true);
    }
}
