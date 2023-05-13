package com.Kayo.model.ai;

import com.Kayo.controller.BoardController;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

import java.util.Random;

public abstract class MinMax {

    public static MovePositions search(BoardController boardController, int depth, boolean isMax, PieceColor allyColor){
        // criando clone do boardController
        BoardController clone = boardController.createClone();

        // criando Node inicial para busca
        Node move = new Node(-1, -1, -1, -1, clone);

        // encontrando melhor valor com minMax
        double bestValue = minMax(move, depth, isMax, allyColor);

        // criando variavel que vai armazenar a melhor movimentacao
        MovePositions movePositions = null;

        // pegando filhos no Node inicial
        Node[] childs = move.getChilds();

        Random random = new Random();
        // percorrendo filhos no No inicial
        for(Node child : childs){
            // se filho tem o melhor valor encontrado pelo MaxMin
            if(child.getValue() == bestValue){
                // se nenhum movimento foi registrado
                if(movePositions == null){
                    movePositions = new MovePositions(child.getFromLine(), child.getFromColumn(), child.getToLine(), child.getToColumn());
                }
                // se um movimento ja foi registrado anteriormente sortear se vai substituir
                else if (random.nextBoolean()){
                    movePositions = new MovePositions(child.getFromLine(), child.getFromColumn(), child.getToLine(), child.getToColumn());
                }
            }
        }
        // retornando melhor movimentacao
        return movePositions;
    }

    private static double minMax(Node current, int depth, boolean isMax, PieceColor allyColor){
        // se chegar na profundidade maxima
        if(depth == 0){
            double value = heuristic(current.getBoardController(), allyColor);
            current.setValue(value);
            return value;
        }
        // se ainda nao chegou na profundidade maxima
        else{
            // criando e inserindo filhos no Node atual
            current.generateChilds();

            // pegando filhos do Node atual
            Node[] childs = current.getChilds();

            // variavel que vai armazenar o melhor valor
            double bestValue;
            // se MAX
            if(isMax) {
                // procurando maior valor entre os filhos
                bestValue = Double.NEGATIVE_INFINITY;
                for (Node child : childs) {
                    double value = minMax(child, depth - 1, false, allyColor);
                    if(value > bestValue){
                        bestValue = value;
                    }
                }
            }
            // se MIN
            else{
                // procurando menor valor entre os filhos
                bestValue = Double.POSITIVE_INFINITY;
                for(Node child: childs){
                    double value = minMax(child, depth - 1, true, allyColor);
                    if(value < bestValue){
                        bestValue = value;
                    }
                }
            }
            // inserindo melhor valor de heuristica entre os filhos no atual
            current.setValue(bestValue);
            // retornando melhor valor encontrado
            return bestValue;
        }
    }

    private static double heuristic(BoardController state, PieceColor allyColor){
        // pesos de cada peca
        final int PAWN_WEIGHT = 1;
        final int KNIGHT_WEIGHT = 3;
        final int BISHOP_WEIGHT = 3;
        final int ROOK_WEIGHT = 5;
        final int QUEEN_WEIGHT = 9;
        final double KING_WEIGHT = 1000;

        // variavel que vai armazenar o resultado
        double sum = 0;

        // percorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // pegando cor e tipo da peca em certa posicao
                PieceType type = state.getTypeOf(i, j);
                PieceColor color = state.getColorOf(i, j);

                // verificando se vai somar ou subtrair da heuristica
                int contributionSignal;
                if(color == allyColor) contributionSignal = 1;
                else contributionSignal = -1;

                // inserindo valor na heuristica
                switch (type) {
                    case PAWN -> sum += PAWN_WEIGHT * contributionSignal;
                    case KNIGHT -> sum += KNIGHT_WEIGHT * contributionSignal;
                    case BISHOP -> sum += BISHOP_WEIGHT * contributionSignal;
                    case ROOK -> sum += ROOK_WEIGHT * contributionSignal;
                    case QUEEN -> sum += QUEEN_WEIGHT * contributionSignal;
                    case KING -> sum += KING_WEIGHT * contributionSignal;
                    default -> sum += 0;
                }
            }
        }
        // retornando resultado
        return sum;
    }
}
