package com.chess_AI.controller;

import com.chess_AI.model.ai.AlphaBeta;
import com.chess_AI.model.ai.PositionsNode;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa o controller do model AI, ela contem as funcoes para manipulacao da IA.
 */
public class AIController {

    /** Cor da peca da IA*/
    private final PieceColor AI_COLOR;

    /** Controller do tabuleiro que vai realizar as operacoes*/
    private final BoardController BOARD_CONTROLLER;

    /**
     * Cria objeto de AIController com informacoes de cor da peca da IA e do tabuleiro que vai realizar as operacoes.
     *
     * @param aIColor Cor da peca da IA
     * @param boardController Controller do tabuleiro que vai realizar as operacoes
     */
    public AIController(PieceColor aIColor, BoardController boardController){
        this.AI_COLOR = aIColor;
        this.BOARD_CONTROLLER = boardController;
    }


    /**
     * Faz a IA realizar uma jogada no tabuleiro.
     *
     * @return Se ela conseguir realizar a jogada
     */
    public boolean play(){
        // se for o turno da IA
        if(!BOARD_CONTROLLER.isUserTurn() && !BOARD_CONTROLLER.isUserWon() && !BOARD_CONTROLLER.isAIWon()) {

            // calcula o melhor movimento com AlphaBeta
            PositionsNode bestMove = AlphaBeta.search(BOARD_CONTROLLER, 4, true, AI_COLOR);

            // posicoes do melhor movimento
            int fromLine = bestMove.getFromLine();
            int fromColumn = bestMove.getFromColumn();
            int toLine = bestMove.getToLine();
            int toColumn = bestMove.getToColumn();

            // se for possivel mover
            if(BOARD_CONTROLLER.move(false, fromLine, fromColumn, toLine, toColumn)){

                // se for for troca de peao
                if(BOARD_CONTROLLER.isHasPawnOnFinal() && !(BOARD_CONTROLLER.isUserTurn())){;
                    BOARD_CONTROLLER.changePawnType(true, PieceType.QUEEN);
                }

                // conseguiu realizar a jogada
                return true;
            }
        }
        // nao conseguiu realizar a jogada
        return false;
    }
}
