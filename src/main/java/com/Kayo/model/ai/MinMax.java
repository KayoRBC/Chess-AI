package com.Kayo.model.ai;

import com.Kayo.controller.BoardController;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;
import org.w3c.dom.Node;

public abstract class MinMax {

    public static PositionsNode search(BoardController boardController, int maxDepth, boolean isMax, PieceColor allyColor){
        PositionsNode root = new PositionsNode(-1, -1, -1, -1);
        return minMax(root, boardController, 0, maxDepth, isMax, allyColor);
    }

    private static PositionsNode minMax(PositionsNode current, BoardController board, int depth, int maxDepth, boolean isMax, PieceColor allyColor){
        // se chegar na profundidade maxima
        if(depth >= maxDepth){
            double value = heuristic(board, allyColor);
            current.setValue(value);
        }
        // se ainda nao chegou na profundidade maxima
        else{
            // setando valor inicial para o Node atual
            // se MAX
            if(isMax){
                current.setValue(Double.NEGATIVE_INFINITY);
            }
            // se MIN
            else{
                current.setValue(Double.POSITIVE_INFINITY);
            }
            // percorrendo posicoes para fromPiece
            for(int fromLine = 0; fromLine < 8; fromLine++){
                for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                    // percorrendo posicoes para toPiece
                    for(int toLine = 0; toLine < 8; toLine++){
                        for(int toColumn = 0; toColumn < 8; toColumn++){
                            // se conseguir fazer movimento de fromPiece para toPiece
                            if(board.move(board.isUserTurn(), fromLine, fromColumn, toLine, toColumn)){
                                PositionsNode child = new PositionsNode(fromLine, fromColumn, toLine, toColumn);
                                // se for MAX
                                if(isMax) {
                                    // procurando melhor valor no filho
                                    PositionsNode best = minMax(child, board, depth+1, maxDepth, false, allyColor);
                                    double bestValue = best.getValue();
                                    // se o melhor valor encontrado do filho for maior do que o valor registrado no pai
                                    if(bestValue > current.getValue()){
                                        current.setValue(bestValue);
                                        if(depth == 0){
                                            transferPositions(best, current);
                                        }
                                    }
                                }
                                // se for MIN
                                else{
                                    // procurando melhor valor no filho
                                    PositionsNode best = minMax(child, board, depth+1, maxDepth, true, allyColor);
                                    double bestValue = best.getValue();
                                    // se o melhor valor encontrado do filho for menor do que o valor registrado no pai
                                    if(bestValue < current.getValue()){
                                        current.setValue(bestValue);
                                        if(depth == 0){
                                            transferPositions(best, current);
                                        }
                                    }
                                }
                                // retornando backup
                                board.returnBackup();
                            }
                        }
                    }
                }
            }
        }
        // retornando Node atual com o melhor valor encontrado entre os filhos
        return current;
    }

    private static void searchBest(PositionsNode current, BoardController board, int depth, int maxDepth, boolean isMax, PieceColor allyColor) {
        // se for MAX
        if(isMax) {
            // procurando melhor valor no filho do filho
            PositionsNode best = minMax(current, board, depth+1, maxDepth, false, allyColor);
            double bestValue = best.getValue();
            // se o melhor valor encontrado do filho for maior do que o valor registrado no pai
            if(bestValue > current.getValue()){
                current.setValue(bestValue);
                if(depth == 0){
                    transferPositions(best, current);
                }
            }
        }
        // se for MIN
        else{
            // procurando melhor valor no filho
            PositionsNode best = minMax(current, board, depth+1, maxDepth, true, allyColor);
            double bestValue = best.getValue();
            // se o melhor valor encontrado do filho for menor do que o valor registrado no pai
            if(bestValue < current.getValue()){
                current.setValue(bestValue);
                if(depth == 0){
                    transferPositions(best, current);
                }
            }
        }
    }

    private static void transferPositions(PositionsNode from, PositionsNode to){
        to.setFromLine(from.getFromLine());
        to.setFromColumn(from.getFromColumn());
        to.setToLine(from.getToLine());
        to.setToColumn(from.getToColumn());
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
