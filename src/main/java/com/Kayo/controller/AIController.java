package com.Kayo.controller;

import com.Kayo.model.ai.MinMax;
import com.Kayo.model.ai.PositionsNode;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class AIController {
    private final PieceColor AI_COLOR;
    private final BoardController BOARD_CONTROLLER;

    public AIController(PieceColor aIColor, BoardController boardController){
        this.AI_COLOR = aIColor;
        this.BOARD_CONTROLLER = boardController;
    }

    public boolean play(){
        // variavel que representa se a AI conseguiu mover a peca
        boolean moved = false;
        // se for o turno da IA
        if(!BOARD_CONTROLLER.isUserTurn() && !BOARD_CONTROLLER.isUserWon() && !BOARD_CONTROLLER.isOpponentWon()) {
            // calculando o melhor movimento com MinMax
            PositionsNode bestMove = MinMax.search(BOARD_CONTROLLER, 4, true, AI_COLOR);

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
