package com.chess_AI.model.ai;

/**
 * Esta classe representa a movimentacao de uma peca
 */
public class PositionsNode {

    /** Posicao da linha de origem*/
    private int fromLine;

    /** Posicao da coluna de origem*/
    private int fromColumn;

    /** Posicao da linha de destino*/
    private int toLine;

    /** Posicao da coluna de destino*/
    private int toColumn;

    /** Valor da heuristica*/
    private double value;

    /**
     * Cria e retorna classe de uma movimentacao de uma peca
     *
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da
     */
    public PositionsNode(int fromLine, int fromColumn, int toLine, int toColumn) {
        this.fromLine = fromLine;
        this.fromColumn = fromColumn;
        this.toLine = toLine;
        this.toColumn = toColumn;
    }

    public int getFromLine() {
        return fromLine;
    }

    public void setFromLine(int fromLine) {
        this.fromLine = fromLine;
    }

    public int getFromColumn() {
        return fromColumn;
    }

    public void setFromColumn(int fromColumn) {
        this.fromColumn = fromColumn;
    }

    public int getToLine() {
        return toLine;
    }

    public void setToLine(int toLine) {
        this.toLine = toLine;
    }

    public int getToColumn() {
        return toColumn;
    }

    public void setToColumn(int toColumn) {
        this.toColumn = toColumn;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
