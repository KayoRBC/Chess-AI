package com.Kayo.controller;

import com.Kayo.model.ai.MinMax;
import com.Kayo.model.ai.MovePositions;
import com.Kayo.model.ai.Node;
import com.Kayo.util.PieceColor;

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
        if(!BOARD_CONTROLLER.isUserTurn()) {
            // calculando o melhor movimento com MinMax
            MovePositions bestMove = MinMax.search(BOARD_CONTROLLER, 4, true, AI_COLOR);

            // posicoes do melhor movimento
            int fromLine = bestMove.getFromLine();
            int fromColumn = bestMove.getFromColumn();
            int toLine = bestMove.getToLine();
            int toColumn = bestMove.getToColumn();

            // se for possivel mover
            if(BOARD_CONTROLLER.move(false, fromLine, fromColumn, toLine, toColumn)){
                moved = true;
            }
        }
        // liberando memoria
        freeMemory();

        // valido se conseguir mover a peca
        return moved;
    }

    public void freeMemory(){
        // liberando memoria
        Runtime runtime = Runtime.getRuntime();

        // Recupera a quantidade de memória livre
        long memoryBefore = runtime.freeMemory();

        // Solicita a limpeza de memória
        runtime.gc();

        // Aguarda a finalização dos objetos pendentes de finalização
        System.runFinalization();

        // Recupera a quantidade de memória livre após a limpeza
        long memoryAfter = runtime.freeMemory();

        // Calcula a quantidade de memória liberada e mostrando no terminal
        long memoryFreed = memoryAfter - memoryBefore;
        System.out.println(memoryFreed);
    }
}
