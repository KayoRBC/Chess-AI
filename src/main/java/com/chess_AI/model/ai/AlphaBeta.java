package com.chess_AI.model.ai;

import com.chess_AI.controller.BoardController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

public abstract class AlphaBeta {

    // inicia busca alpha beta
    public static PositionsNode search(BoardController boardController, int maxDepth, boolean isMax, PieceColor allyColor){
        PositionsNode root = new PositionsNode(-1, -1, -1, -1);
        return alphaBeta(root, boardController, 0, maxDepth, isMax, allyColor);
    }


    // aplica alpha beta e retorna o node atual com o melhor valor e com a melhor posicao de movimentacao
    private static PositionsNode alphaBeta(PositionsNode current, BoardController board, int depth, int maxDepth, boolean isMax, PieceColor allyColor){
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
                                // se for for troca de peao
                                if(board.isPawnChange()){;
                                    board.changePawnType(board.isUserTurn(), PieceType.QUEEN);
                                }

                                // criando filho
                                PositionsNode child = new PositionsNode(fromLine, fromColumn, toLine, toColumn);

                                // procurando melhor valor no filho
                                PositionsNode best = alphaBeta(child, board, depth + 1, maxDepth, !isMax, allyColor);
                                double bestValue = best.getValue();

                                // se for max e o melhor valor foi maior do que o registrado
                                if ((isMax && bestValue > current.getValue())
                                        // ou se for min e melhor valor for melhor do que o registrado
                                        || (!isMax && bestValue < current.getValue())
                                        // ou se o melhor valor for igual ao valor atual, tem 10% de chance de trocar
                                        || (bestValue == current.getValue() && Math.random() < 0.1)) {
                                    // registrando melhor valor no Node atual
                                    current.setValue(bestValue);
                                    // retropopagando posicoes de movimentacao se profundidade atual for 0
                                    if (depth == 0) {
                                        transferPositions(best, current);
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

    // tranfere o registro das posicoes de movimentacao de uma peca para a outra
    private static void transferPositions(PositionsNode from, PositionsNode to){
        to.setFromLine(from.getFromLine());
        to.setFromColumn(from.getFromColumn());
        to.setToLine(from.getToLine());
        to.setToColumn(from.getToColumn());
    }

    // pega o peso de uma peca
    private static double getWeight(PieceType type){
        return switch (type) {
            case PAWN -> 1;
            case KNIGHT, BISHOP -> 3;
            case ROOK -> 5;
            case QUEEN -> 9;
            case KING -> 1000;
            default -> 0;
        };
    }

    // retorna a heuristica de um tabuleiro para uma determinada cor de peca
    private static double heuristic(BoardController state, PieceColor allyColor){
        return calculateWeights(state, allyColor);
    }

    // calculo de heuristica com base nos pesos das pecas e como elas impactam a cor selecionada
    private static double calculateWeights(BoardController state, PieceColor allyColor) {
        // variavel que vai armazenar o resultado
        double sum = 0;
        // percorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // pegando cor e tipo da peca em certa posicao
                PieceType type = state.getTypeOf(i, j);
                PieceColor color = state.getColorOf(i, j);
                // se peca inimiga
                if(color != allyColor){
                    // subtrai
                    sum -= getWeight(type);
                }
                // peca aliada
                else{
                    // soma
                    sum += getWeight(type);
                }
            }
        }
        // retornando resultado
        return sum;
    }
}
