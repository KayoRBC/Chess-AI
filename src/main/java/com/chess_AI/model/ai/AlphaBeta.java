package com.chess_AI.model.ai;

import com.chess_AI.controller.BoardController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

/**
 * Esta classe representa a estategia de busca AlphaBeta para um tabuleiro
 */
public abstract class AlphaBeta {

    /**
     * Busca e retorna a melhor movimento esperado de uma peca de acordo com o tabuleiro atual
     *
     * @param boardController  Tabuleiro atual para fazer a busca
     * @param maxDepth Maxima profundidade de busca
     * @param isMax Se quer procurar o movimento esperado que maximize seus ganhos
     * @param allyColor Cor da peca da IA
     * @return Posicoes de origem e destino para movimentar a peca
     */
    public static PositionsNode search(BoardController boardController, int maxDepth, boolean isMax, PieceColor allyColor){
        PositionsNode root = new PositionsNode(-1, -1, -1, -1);
        return alphaBeta(root, boardController, 0, maxDepth, isMax, allyColor);
    }

    /**
     * Aplica busca AlphaBeta recursivo
     *
     * @param current Posicao atual
     * @param board Tabuleiro atual
     * @param depth Profundidade atual
     * @param maxDepth Profundidade maxima
     * @param isMax Se a profundidade atual busca maximizar os ganhos
     * @param allyColor Cor da peca da IA
     * @return Melhor movimento esperado
     */
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
                                if(board.isHasPawnOnFinal()){;
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

    /**
     * Retroproga a movimentacao registrada de uma movimentacao registrada ate outra
     *
     * @param from Movimentacao para retropropagar
     * @param to Movimentacao que vai receber a nova movimentacao
     */
    private static void transferPositions(PositionsNode from, PositionsNode to){
        to.setFromLine(from.getFromLine());
        to.setFromColumn(from.getFromColumn());
        to.setToLine(from.getToLine());
        to.setToColumn(from.getToColumn());
    }

    /**
     * Pega o peso de uma peca de acordo com o tipo dela
     *
     * @param type Tipo da peca
     * @return O peso da pesa
     */
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

    /**
     * Calcula a heuristica de um estado do tabuleiro para uma determinada cor, quanto maior melhor
     *
     * @param state Tabuleiro atual
     * @param allyColor Cor da peca para calcular a heuristica
     * @return O valor do resultado da heuristica
     */
    private static double heuristic(BoardController state, PieceColor allyColor){
        return calculateWeights(state, allyColor);
    }

    /**
     * Calcula de heuristica com base nos pesos das pecas e como elas impactam a cor selecionada
     *
     * @param state Tabuleiro para calcular
     * @param allyColor Cor da peca para calcular
     * @return Valor da heuristica com base nos pesos.
     */
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
