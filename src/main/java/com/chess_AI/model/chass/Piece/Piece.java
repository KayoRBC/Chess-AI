package com.chess_AI.model.chass.Piece;

import com.chess_AI.model.chass.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Util;

/**
 * Esta classe abstrata possui os atributos gerais de uma peca e funcoes para verificacao de movimentacao.
 *
 */
public abstract class Piece{

    /** Cor da peca*/
    protected final PieceColor COLOR;

    /** Tipo da peca*/
    protected final PieceType TYPE;

    /** Se foi movimentada alguma vez durante o jogo*/
    protected boolean hasMoved = false;

    /**
     * Contrutor para ser chamado nas classes filhas
     *
     * @param color Cor da peca
     * @param pieceType Tipo da peca
     */
    public Piece(PieceColor color, PieceType pieceType){
        this.COLOR = color;
        this.TYPE = pieceType;
    }

    /**
     * Verifica se pode realizar o movimento de uma posicao origem ate uma posicao destino
     *
     * @param board Tabuleiro para verificar
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna de destino
     * @return Se eh possivel realizar o movimento
     */
    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);

    /**
     * Cria retorna um clone da peca
     *
     * @return Clone da peca
     */
    public abstract Piece createClone();

    /**
     * Retorna a cor da peca
     *
     * @return Cor da peca
     */
    public PieceColor getColor() {
        return COLOR;
    }

    /**
     * Retorna o tipo da peca
     *
     * @return Tipo da peca
     */
    public PieceType getType() {
        return TYPE;
    }

    /**
     * Retorna se a peca ja foi movimentada
     *
     * @return Se ja foi movimentada
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Atualiza se a peca ja foi movimentada com o parametro passado
     *
     * @param hasMoved Se a peca ja foi movimentada
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Verifica se as pecas das duas posicoes passadas existem e pode verificar se as cores das duas pecas sao iguais
     *
     * @param board Tabuleiro para verificar
     * @param isSameColor Se precisam ser da mesma cor
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna de destino
     * @return Se as pecas sao validas
     */
    protected boolean verifyPieces(Board board, boolean isSameColor, int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null && toPiece != null){
            // se as pecas precisas ser da mesma coisa e sao da mesma cor
            if((isSameColor && fromPiece.getColor() == toPiece.getColor())
                    // ou se as pecas precisam ser de cores diferentes e sao de cores diferentes
                    || (!isSameColor && fromPiece.getColor() != toPiece.getColor())){
                // valido
                return true;
            }
        }
        // invalido
        return false;
    }

    /**
     * Verifica se o valor esta de 0 a 8 (tammanho do tabuleiro)
     *
     * @param value Valor para verificar
     * @return Se esta dentro do tabuleiro
     */
    protected boolean valueInBoard(int value){
        return -1 < value && value < 8;
    }

    /**
     * Verifica se nao existem pecas intermediarias na vertical de uma posicao ate outro
     *
     * @param board Tabuleiro para verificar
     * @param fromLine Posicao da linha inicial
     * @param fromColumn Posicao da coluna inicial
     * @param toLine Posicao da linha final
     * @param toColumn Posicao da coluna final
     * @param maxDistance Distancia maxima que precisa ser entre as duas posicoes
     * @return Se nao existe pecas intermediarias
     */
    protected boolean isVerticalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance){
        // se distancia for maior que 0
        if(maxDistance > 0) {
            // se fromValue, toValue e constantValue estao dentro do tabuleiro
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se vertical
                if (Math.abs(fromLine - toLine) <= maxDistance && Math.abs(fromColumn - toColumn) == 0) {
                    // criando valores das linhas intermediaras
                    int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
                    // se existir intermediarios
                    if(intermediateLines != null) {
                        // percorrendo posicoes intermediaras
                        for (int line : intermediateLines) {
                            // pegando peca
                            Piece piece = board.getPiece(line, fromColumn);
                            // se nao for uma peca vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }

    /**
     * Verifica se nao existem pecas intermediarias na horizontal de uma posicao ate outra
     *
     * @param board Tabuleiro para verificar
     * @param fromLine Posicao da linha de inicio
     * @param fromColumn Posicao da coluna de inicio
     * @param toLine Posicao da linha final
     * @param toColumn Posicao da coluna final
     * @param maxDistance Distancia maxima que precisa ser entre as duas posicoes
     * @return Se nao existem pecas intermediarias
     */
    protected boolean isHorizontalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance) {
        // se distacia for maior que 0
        if(maxDistance > 0) {
            // se fromValue, toValue e constantValue estao dentro do tabuleiro
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se horizontal
                if (Math.abs(fromLine - toLine) == 0 && Math.abs(fromColumn - toColumn) <= maxDistance) {
                    // criando valores das colunas intermediaras
                    int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
                    // se exisitir intermediarios
                    if(intermediateColumns != null) {
                        // percorrendo posicoes intermediaras
                        for (int column : intermediateColumns) {
                            // pegando peca
                            Piece piece = board.getPiece(fromLine, column);
                            // se nao for uma peca vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }

    /**
     * Verifica se nao existem pecas intermediarias na diagonal de uma posicao ate outra
     *
     * @param board Tabuleiro para verificar
     * @param fromLine Posicao da linha de inicio
     * @param fromColumn Posicao da coluna de inicio
     * @param toLine Posicao da linha final
     * @param toColumn Posicao da coluna final
     * @param maxDistance Distancia maxima que precisa ser entre as duas posicoes
     * @return Se nao existem pecas intermediarias
     */
    protected boolean isDiagonalValid(Board board, int fromLine, int fromColumn, int toLine, int toColumn, int maxDistance) {
        // se distancia for maior que 0
        if(maxDistance > 0) {
            // se as pecas existirem
            if (valueInBoard(fromLine) && valueInBoard(fromColumn) && valueInBoard(toLine) && valueInBoard(toColumn)) {
                // se as distancias forem iguais (diagonal) e iguais a distancia
                if (Math.abs(fromLine - toLine) == Math.abs(fromColumn - toColumn) && Math.abs(fromLine - toLine) <= maxDistance) {
                    // criando valores intermediarios
                    int[] intermediateLines = Util.createIntermediateValues(fromLine, toLine);
                    int[] intermediateColumns = Util.createIntermediateValues(fromColumn, toColumn);
                    // se existir posicoes intermediarias ao movimento
                    if (intermediateLines != null && intermediateColumns != null) {
                        // verifica se alguma peca intermediaria nao eh vazia
                        for (int i = 0; i < intermediateLines.length; i++) {
                            int line = intermediateLines[i];
                            int column = intermediateColumns[i];
                            Piece piece = board.getPiece(line, column);
                            // se nao for vazia
                            if (piece.getType() != PieceType.NULL) {
                                // invalido
                                return false;
                            }
                        }
                    }
                    // valido
                    return true;
                }
            }
        }
        // invalido
        return false;
    }
}
