package com.Kayo.model.ai;

import com.Kayo.controller.BoardController;

import java.util.ArrayList;

public class Node {
    private final BoardController boardController;

    // posicoes da movimentacao realizada
    private int FROM_LINE;
    private int FROM_COLUMN;
    private int TO_LINE;
    private int TO_COLUMN;

    // valor da heuristica
    private double value;

    private Node[] childs;

    public Node(int fromLine, int fromColumn, int toLine, int toColumn, BoardController boardController) {
        this.FROM_LINE = fromLine;
        this.FROM_COLUMN = fromColumn;
        this.TO_LINE = toLine;
        this.TO_COLUMN = toColumn;
        this.boardController = boardController;
    }

    public int getFromLine() {
        return FROM_LINE;
    }

    public int getFromColumn() {
        return FROM_COLUMN;
    }

    public int getToLine() {
        return TO_LINE;
    }

    public int getToColumn() {
        return TO_COLUMN;
    }

    public BoardController getBoardController() {
        return boardController;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value){
        this.value = value;
    }

    public Node[] getChilds() {
        return childs;
    }

    public void generateChilds(){
        childs = createChilds(this);
    }

    private Node[] createChilds(Node father){
        // lista que vai armazenar os filhos
        ArrayList<Node> childs = new ArrayList<>();

        // criando clone do boardControler do pai
        BoardController fatherBoard = father.getBoardController();
        BoardController boardClone = fatherBoard.createClone();

        // pegando posicao da peca atual
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                // pegando cada posicao destino
                for(int toLine = 0; toLine < 8; toLine++){
                    for(int toColumn = 0; toColumn < 8; toColumn++){
                        // se for possivel mover a peca atual para a posicao de destino
                        if(boardClone.move(fatherBoard.isUserTurn(), fromLine, fromColumn, toLine, toColumn)){
                            Node child = new Node(fromLine, fromColumn, toLine, toColumn, boardClone);
                            // adicionando possivel jogada a lista de filhos
                            childs.add(child);

                            // criando novo clone do tabuleiro do pai
                            boardClone = fatherBoard.createClone();
                        }
                    }
                }
            }
        }
        return childs.toArray(new Node[0]);
    }
}
