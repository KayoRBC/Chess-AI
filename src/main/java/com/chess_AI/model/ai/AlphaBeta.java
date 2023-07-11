package com.chess_AI.model.ai;

import com.chess_AI.controller.BoardController;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import java.util.ArrayList;
import java.util.Random;

/**
 * Esta interface representa a estategia de busca AlphaBeta para um tabuleiro de xadrez.
 */
public interface AlphaBeta {

    /**
     * Busca e retorna a melhor movimento esperado em um tabuleiro.
     *
     * @param AIColor Cor da peca da IA
     * @param boardController  Tabuleiro atual para fazer a busca
     * @param maxDepth Maxima profundidade de busca
     * @param isMax Se quer procurar o movimento esperado que maximize seus ganhos
     * @return A melhor jogada encontrada. Se nao encontrar retorna null.
     */
    static Move search(PieceColor AIColor, BoardController boardController, int maxDepth, boolean isMax){
        Node initial = new Node(null, 0, boardController);

        if(!boardController.isUserTurn()) {
            alphaBeta(initial, maxDepth, 0, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, isMax, AIColor);
        }

        return initial.move;
    }

    /**
     * Faz busca alpha beta recursivo.
     *
     * @param current Node atual
     * @param maxDepth Profundidade maxima
     * @param currentDepth Profundidade atual
     * @param alpha Valor de alpha
     * @param beta Valor de beta
     * @param isMax Se busca maximizar a heuristica
     * @param AIColor Cor da peca da IA
     * @return Current com o melhor movimento registrado. Se nao conseguir encontrar entao nao vai mudar
     * o movimento registrado.
     */
    private static Node alphaBeta(Node current, int maxDepth, int currentDepth,
                           double alpha, double beta, boolean isMax, PieceColor AIColor){

        Node[] childrens = generateChildrens(current);

        // lista para caso tenha mais de um Node com o mesmo valor de heuristica
        ArrayList<Node> bestChildrens = new ArrayList<>();

        BoardController board = current.BOARD_CONTROLLER; // tabuleiro para aplicar as movimentacoes

        // se chegar no final da arvore
        boolean isTerminalNode = childrens.length == 0 || maxDepth - currentDepth == 0;
        if(isTerminalNode){
            current.value = getHeuristic(board, AIColor);
            return current;
        }
        // encontra o melhores filhos e insere na lista bestChildrens
        else{
            if(isMax){

                double bestValue = Double.NEGATIVE_INFINITY;

                for (Node child : childrens) {
                    if(board.makeMove(false, child.move)) {
                        if (board.verifyPawnPromote()) board.makePawnPromote(false, PieceType.QUEEN);

                        Node b = alphaBeta(child, maxDepth, currentDepth + 1, alpha, beta, false, AIColor);
                        board.returnBackup();

                        // verifica se eh melhor filho e coloca na lista bestChildrens
                        if(b.value > bestValue){
                            bestValue = b.value;
                            bestChildrens = new ArrayList<>();
                            bestChildrens.add(b);
                        }
                        else if(b.value == bestValue) bestChildrens.add(b);

                        if(bestValue > alpha) alpha = bestValue;

                        // verifica se o jogador minimizador ja encontrou uma jogada melhor
                        if(alpha > beta) break;
                    }
                }
            }
            else{ // min

                double bestValue = Double.POSITIVE_INFINITY;

                for (Node child : childrens) {
                    if(board.makeMove(true, child.move)) {
                        if (board.verifyPawnPromote()) board.makePawnPromote(true, PieceType.QUEEN);

                        Node b = alphaBeta(child, maxDepth, currentDepth + 1, alpha, beta, true, AIColor);
                        board.returnBackup();

                        // verifica se eh melhor filho e coloca na lista bestChildrens
                        if(b.value < bestValue){
                            bestValue = b.value;
                            bestChildrens = new ArrayList<>();
                            bestChildrens.add(b);
                        }
                        else if (b.value == bestValue) bestChildrens.add(b);

                        if(bestValue < beta) beta = bestValue;

                        // verifica se o jogaador maximizador ja encontrou uma jogada melhor
                        if(beta < alpha) break;
                    }
                }
            }
        }

        if(bestChildrens.size() > 0) {
            // se estar na profundidade mais rasa da arvore entao retropagar a movimentacao do filho ao pai
            if (currentDepth == 0) {
                // sorteia um filho aleatorio com melhor heuristica
                Random random = new Random();
                int i = random.nextInt(bestChildrens.size());
                current.move = bestChildrens.get(i).move;
            }

            // retropropaga o valor da heuristica do melhor filho ao pai
            current.value = bestChildrens.get(0).value;
        }
        return current;
    }

    /**
     * Cria os filhos do Node atual. Entretanto os filhos apenas possuem a movimentacao registrada.
     * Cria independentemente se for o turno da IA ou do usuario.
     *
     * @param current Node atual
     * @return Lista de filhos do node.
     */
    private static Node[] generateChildrens(Node current){
        ArrayList<Node> childrens = new ArrayList<>();

        BoardController boardController = current.BOARD_CONTROLLER;

        // cria todas as movimentacoes possivies no tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                for(int toLine = 0; toLine < 8; toLine++){
                    for(int toColumn = 0; toColumn < 8; toColumn++){
                        Move move = new Move(fromLine, fromColumn, toLine, toColumn);

                        // se conseguir aplicar a movimentacao entao adiciona na lista e retorna backup
                        if(boardController.makeMove(true, move)){
                            if(current.BOARD_CONTROLLER.verifyPawnPromote()){ // se algum peao chegou no final do tabuleiro
                                current.BOARD_CONTROLLER.makePawnPromote(true, PieceType.QUEEN);
                            }
                            childrens.add(new Node(move, 0, boardController));
                            boardController.returnBackup();
                        }
                        else if(boardController.makeMove(false, move)){
                            if(current.BOARD_CONTROLLER.verifyPawnPromote()){ // se algum peao chegou no final do tabuleiro
                                current.BOARD_CONTROLLER.makePawnPromote(false, PieceType.QUEEN);
                            }
                            childrens.add(new Node(move, 0, boardController));
                            boardController.returnBackup();
                        }
                    }
                }
            }
        }
        return childrens.toArray(new Node[0]);
    }

    /**
     * Calcula e retorna a heuristica de um boardController para uma respectiva cor.
     *
     * @param boardController Tabuleiro para calcular a heuristica
     * @param color Cor para calcular a heuristica
     * @return O valor do resultado da heuristica, quanto maior melhor para a cor selecionada.
     */
    private static double getHeuristic(BoardController boardController, PieceColor color){
        double heuristic = 0; // resultado da heuristica

        // percorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Position position = new Position(i, j);

                PieceType type = boardController.getTypeOf(position);
                PieceColor pieceColor = boardController.getColorOf(position);

                boolean isEnemy = color != pieceColor;
                if(isEnemy) heuristic -= getWeight(type);
                else heuristic += getWeight(type);
            }
        }

        return heuristic;
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
}
