package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Functions;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

/**
 * Esta classe abstrata possui os atributos gerais para uma peca de xadrez.
 */
public abstract class Piece implements Cloneable{

    /** Cor da peca.*/
    protected final PieceColor COLOR;

    /** Se foi movimentada alguma vez durante o jogo.*/
    protected boolean hasMoved = false;

    /**
     * Contrutor para ser chamado nas classes filhas.
     *
     * @param color Cor da peca.
     */
    public Piece(PieceColor color){
        this.COLOR = color;
    }

    /**
     * Verifica se a peca pode realizar um movimento no tabuleiro.
     * Nao verifica movimento de castling.
     *
     * @param board Tabuleiro para verificar.
     * @param move Movimento para verificar.
     * @return Se eh possivel realizar o movimento de acordo com as regras da peca.
     */
    public abstract boolean isValidMove(Board board, Move move);

    /**
     * Verifica se o movimento eh de castling e pode ser aplicado no tabuleiro.
     *
     * @param board Tabuleiro para verificar.
     * @param move Movimento para verificar.
     * @return Se eh possivel realizar o movimento de castling.
     */
    public boolean isCastlingMove(Board board, Move move){
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        if(horizontalDistance == 3 || horizontalDistance == 4) {
            Piece fromPiece = board.getPiece(move.FROM);
            Piece toPiece = board.getPiece(move.TO);

            boolean hasValidTypes = (fromPiece instanceof King && toPiece instanceof Rook)
                                    || (fromPiece instanceof Rook && toPiece instanceof King);
            if(hasValidTypes) {
                boolean sameColor = fromPiece.getColor() == toPiece.getColor();
                boolean hasNotMoved = !fromPiece.hasMoved() && !toPiece.hasMoved();

                if (sameColor && hasNotMoved && hasNotHorizontalIntermediaries(board, move)) {
                    PieceColor allyColor = fromPiece.getColor();
                    return (board.isSafeLine(move.FROM.LINE, move.FROM.COLUMN, move.TO.COLUMN, allyColor));
                }
            }
        }
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
     * Retorna se a peca ja foi movimentada.
     *
     * @return Se ja foi movimentada.
     */
    public boolean hasMoved() {
        return hasMoved;
    }

    /**
     * Atualiza se a peca ja foi movimentada.
     *
     * @param hasMoved Se a peca ja foi movimentada.
     */
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
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

    /**
     * Verifica se o movimento eh na vertical e nao existem pecas intermediarias.
     * Se as posicoes intermediarias nao existirem vai considerar como se fossem vazias.
     * Nao verifica se as pecas nas posicoes de origem e destino no tabuleiro existem.
     *
     * @param board Tabuleiro para verificar.
     * @param move Movimento para verificar.
     * @return Se o movimento eh na vertical e nao existem pecas intermediarias.
     */
    protected boolean hasNotVerticalIntermediaries(Board board, Move move){
        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        boolean isVerticalMove = horizontalDistance == 0 && verticalDistance > 0;
        if(isVerticalMove) {

            // verifica se nao possui pecas intermediarias
            int[] lines = Functions.createIntermediateValues(move.FROM.LINE, move.TO.LINE);
            if(lines != null){
                for(int line: lines){
                    Position position = new Position(line, move.FROM.COLUMN);
                    Piece piece = board.getPiece(position);
                    if(piece != null && !(piece instanceof Null)) return false; // existe intermediario
                }
            }
            return true; // nao existem intermedirios
        }
        return false; // movimento nao eh na vertical
    }

    /**
     * Verifica se o movimento eh na horizontal e se nao existem pecas intermediarias.
     * Se as posicoes intermediarias nao existirem vai considerar como se fossem vazias.
     * Nao verifica se as pecas nas posicoes de origem e destino no tabuleiro existem.
     *
     * @param board Tabuleiro para verificar.
     * @param move Movimento para verificar.
     * @return Se o movimento eh na horizontal e nao existem pecas intermediarias.
     */
    protected boolean hasNotHorizontalIntermediaries(Board board, Move move){
        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        boolean isHorizontalMove = verticalDistance == 0 && horizontalDistance > 0;
        if(isHorizontalMove) {

            // verifica se nao possui pecas intermediarias
            int[] columns = Functions.createIntermediateValues(move.FROM.COLUMN, move.TO.COLUMN);
            if(columns != null){
                for(int column: columns){
                    Position position = new Position(move.FROM.LINE, column);
                    Piece piece = board.getPiece(position);
                    if(piece != null && !(piece instanceof Null)) return false; // existe intermediario
                }
            }
            return true; // nao existem intermediarios
        }
        return false; // movimento nao eh na horizontal
    }

    /**
     * Verifica se o movimento eh na diagonal e se nao existem pecas intermediarias.
     * Se as posicoes intermediarias nao existirem vai considerar como se fossem vazias.
     * Nao verifica se as pecas nas posicoes de origem e destino no tabuleiro existem.
     *
     * @param board Tabuleiro para verificar.
     * @param move Movimento para verificar.
     * @return Se movimento eh na diagonal e nao existem pecas intermediarias.
     */
    protected boolean hasNotDiagonalIntermediaries(Board board, Move move) {
        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        boolean isDiagonalMove = verticalDistance == horizontalDistance;

        if(isDiagonalMove){

            // verifica se nao tem pecas intermediarias
            int[] lines = Functions.createIntermediateValues(move.FROM.LINE, move.TO.LINE);
            int[] columns = Functions.createIntermediateValues(move.FROM.COLUMN, move.TO.COLUMN);
            if(lines != null && columns != null) {
                for(int i = 0; i < lines.length; i++) {
                    int line = lines[i];
                    int column = columns[i];
                    Position position = new Position(line, column);
                    Piece piece = board.getPiece(position);
                    if (piece != null && !(piece instanceof Null)) return false; // existe intermediario
                }
            }
            return true; // nao existem intermediarios
        }
        return false; // movimento nao eh na diagonal
    }
}
