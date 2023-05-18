package com.chess_IA.controller;

import com.chess_IA.model.ai.AlphaBeta;
import com.chess_IA.model.ai.PositionsNode;
import com.chess_IA.util.PieceColor;
import com.chess_IA.util.PieceType;

public class AIController {

    // cor da peca da IA
    private final PieceColor AI_COLOR;

    // tabuleiro que vai fazer as jogadas
    private final BoardController BOARD_CONTROLLER;

    public AIController(PieceColor aIColor, BoardController boardController){
        this.AI_COLOR = aIColor;
        this.BOARD_CONTROLLER = boardController;
    }


    // funcao para a IA fazer a jogada
    // return true caso de certo
    // return false caso nao de certo
    public boolean play(){
        // variavel que representa se a AI conseguiu mover a peca
        boolean moved = false;
        // se for o turno da IA
        if(!BOARD_CONTROLLER.isUserTurn() && !BOARD_CONTROLLER.isUserWon() && !BOARD_CONTROLLER.isOpponentWon()) {
            // calculando o melhor movimento com AlphaBeta
            PositionsNode bestMove = AlphaBeta.search(BOARD_CONTROLLER, 4, true, AI_COLOR);

            // posicoes do melhor movimento
            int fromLine = bestMove.getFromLine();
            int fromColumn = bestMove.getFromColumn();
            int toLine = bestMove.getToLine();
            int toColumn = bestMove.getToColumn();

            // se for possivel mover
            if(BOARD_CONTROLLER.move(false, fromLine, fromColumn, toLine, toColumn)){

                // se for for troca de peao
                if(BOARD_CONTROLLER.isPawnChange() && !(BOARD_CONTROLLER.isUserTurn())){;
                    BOARD_CONTROLLER.changePawnType(true, PieceType.QUEEN);
                }
                moved = true;
            }
        }

        // valido se conseguir mover a peca
        return moved;
    }
}
