package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Util;

/**
 * Esta classe abstrata possui os atributos gerais de uma peca e funcoes para serem utilizadas na verificacao
 * de movimentacao.
 */
public abstract class Piece implements Cloneable{

    /** Cor da peca*/
    protected final PieceColor COLOR;

    /** Tipo da peca*/
    protected final PieceType TYPE;

    /** Se foi movimentada alguma vez durante o jogo*/
    protected boolean hasMoved = false;

    /**
     * Contrutor para ser chamado nas classes filhas.
     *
     * @param color Cor da peca
     * @param pieceType Tipo da peca
     */
    public Piece(PieceColor color, PieceType pieceType){
        this.COLOR = color;
        this.TYPE = pieceType;
    }

    /**
     * Verifica se pode realizar o movimento de uma posicao origem ate uma posicao destino de acordo com
     * as regras da peca.
     * Nao verifica movimento de castling.
     *
     * @param board Tabuleiro para verificar
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna de destino
     * @return Se eh possivel realizar o movimento de acordo com as regras da peca
     */
    public abstract boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn);

    /**
     * Verifica se eh possivel realizar o movimento de castling.
     * Alem disso, uma posicao tem quer ser de uma torre e outra de um rei,
     * verifica se a cores sao iguais, movimento eh horizontal e a distancia
     * horizontal entre o rei e a torre tem que ser 3 ou 4.
     *
     * @param board Tabuleiro para verificar
     * @param firstLine Posicao da linha da primeira peca
     * @param firstColumn Posicao da coluna da primeira peca
     * @param secondLine Posicao da linha da segunda peca
     * @param secondColumn Posicao da coluna da segunda peca
     * @return Se eh possivel realizar o movimento de castling.
     */
    public boolean isCastlingMove(Board board, int firstLine, int firstColumn, int secondLine, int secondColumn){
        Piece firstPiece = board.getPiece(firstLine, firstColumn);
        Piece secondPiece = board.getPiece(secondLine, secondColumn);

        int horizontalDistance = Math.abs(firstColumn - secondColumn);

        if(((firstPiece instanceof King && secondPiece instanceof Rook)
            || (firstPiece instanceof Rook && secondPiece instanceof King))
            && (horizontalDistance == 3 || horizontalDistance == 4)) {

            boolean sameColor = firstPiece.getColor() == secondPiece.getColor();
            boolean hasNotMoved = !firstPiece.hasMoved() && !secondPiece.hasMoved();

            if (sameColor && hasNotMoved && isHorizontalValid(board, firstLine, firstColumn, secondLine, secondColumn)) {
                return (board.isSafeLine(firstLine, firstColumn, secondColumn, firstPiece.getColor()));
            }
        }

        // movimento invalido
        return false;
    }

    /**
     * Retorna a cor da peca.
     *
     * @return Cor da peca.
     */
    public PieceColor getColor() {
        return COLOR;
    }

    /**
     * Retorna o tipo da peca.
     *
     * @return Tipo da peca.
     */
    public PieceType getType() {
        return TYPE;
    }

    /**
     * Retorna se a peca ja foi movimentada.
     *
     * @return Se ja foi movimentada.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Atualiza se a peca ja foi movimentada com o parametro passado.
     *
     * @param hasMoved Se a peca ja foi movimentada.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    /**
     * Verifica se o movimento eh na vertical e se nao existem pecas intermediarias
     * de uma posicao ate outra.
     * Se as posicoes nao existirem vai considerar como se fosse uma NullPiece.
     *
     * @param board Tabuleiro para verificar
     * @param initialLine Posicao da linha inicial
     * @param initialColumn Posicao da coluna inicial
     * @param finalLine Posicao da linha final
     * @param finalColumn Posicao da coluna final
     * @return Se movimento eh na vertical e nao existem pecas intermediarias.
     */
    protected boolean isVerticalValid(Board board, int initialLine, int initialColumn, int finalLine, int finalColumn){
        int verticalDistance = Math.abs(initialLine - finalLine);
        int horizontalDistance = Math.abs(initialColumn - finalColumn);

        boolean isVerticalMove = horizontalDistance == 0 && verticalDistance > 0;

        if(isVerticalMove) {
            int[] lines = Util.createIntermediateValues(initialLine, finalLine);

            if(lines != null){
                for(int line: lines){
                    Piece piece = board.getPiece(line, initialColumn);
                    if(piece != null && !(piece instanceof NullPiece)) return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Verifica se o movimento eh na horizontal e se nao existem pecas intermediarias
     * de uma posicao inicial ate uma posicao final.
     * Se as posicoes nao existirem vai considerar como se fosse uma NullPiece.
     *
     * @param board Tabuleiro para verificar
     * @param initialLine Posicao da linha inicial
     * @param initialColumn Posicao da coluna inicial
     * @param finalLine Posicao da linha final
     * @param finalColumn Posicao da coluna final
     * @return Se movimento eh na horizontal e nao existem pecas intermediarias.
     */
    protected boolean isHorizontalValid(Board board, int initialLine, int initialColumn, int finalLine, int finalColumn){
        int verticalDistance = Math.abs(initialLine - finalLine);
        int horizontalDistance = Math.abs(initialColumn - finalColumn);

        boolean isHorizontalMove = verticalDistance == 0 && horizontalDistance > 0;

        if(isHorizontalMove) {
            int[] columns = Util.createIntermediateValues(initialColumn, finalColumn);

            if(columns != null){
                for(int column: columns){
                    Piece piece = board.getPiece(initialLine, column);
                    if(piece != null && !(piece instanceof NullPiece)) return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * Verifica se o movimento eh na diagonal e se nao existem pecas intermediarias
     * de uma posicao inicial ate uma posicao final.
     * Se as posicoes nao existirem vai considerar como se fosse uma NullPiece.
     *
     * @param board Tabuleiro para verificar
     * @param initialLine Posicao da linha inicial
     * @param initialColumn Posicao da coluna inicial
     * @param finalLine Posicao da linha final
     * @param finalColumn Posicao da coluna final
     * @return Se movimento eh na diagonal e nao existem pecas intermediarias.
     */
    protected boolean isDiagonalValid(Board board, int initialLine, int initialColumn, int finalLine, int finalColumn) {
        int verticalDistance = Math.abs(initialLine - finalLine);
        int horizontalDistance = Math.abs(initialColumn - finalColumn);

        boolean isDiagonalMove = verticalDistance == horizontalDistance;

        if(isDiagonalMove){
            int[] lines = Util.createIntermediateValues(initialLine, finalLine);
            int[] columns = Util.createIntermediateValues(initialColumn, finalColumn);
            if(lines != null && columns != null) {

                for(int i = 0; i < lines.length; i++) {
                    int line = lines[i];
                    int column = columns[i];
                    Piece piece = board.getPiece(line, column);
                    if (piece != null && !(piece instanceof NullPiece)) return false;
                }

            }
            return true;
        }
        return false;
    }

    @Override
    public Piece clone() {
        try {
            Piece clone = (Piece) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
