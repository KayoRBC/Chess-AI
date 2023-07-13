package com.chess_AI.model.board.piece;

import com.chess_AI.model.board.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca do peao.
 */
public class Pawn extends Piece{

    /**
     * Cria e retorna um objeto de Pawn.
     *
     * @param color Cor do peao.
     */
    public Pawn(PieceColor color) {
        super(color);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        if(fromPiece instanceof Pawn && toPiece != null && fromPiece.getColor() != toPiece.getColor()
                && isCorrectlyDirected(fromPiece.getColor(), move.FROM.LINE, move.TO.LINE)){

            if(toPiece instanceof Null){
                if(fromPiece.hasMoved) return verticalDistance == 1 && hasNotVerticalIntermediaries(board, move);

                // peca nao foi movimentada ainda
                else return verticalDistance < 3 && hasNotVerticalIntermediaries(board, move);
            }
            else{
                boolean isOneStepDiagonal = horizontalDistance == 1 && verticalDistance == 1;
                return isOneStepDiagonal && hasNotDiagonalIntermediaries(board, move);
            }
        }

        return false;
    }

    /**
     * Verifica se o sentido do movimento na direcao vertical esta correto.
     *
     * @param color Cor da peca que esta realizando o movimento.
     * @param fromLine Posicao da linha de origem.
     * @param toLine Posicao da linha de destino.
     * @return Se o sentido do movimento esta correto.
     */
    private boolean isCorrectlyDirected(PieceColor color, int fromLine, int toLine){
        if(color == PieceColor.WHITE){
            // se branca comeca por cima | valido se estar se movimentando para baixo
            if(PieceColor.isWhiteUp()) return fromLine - toLine < 0;
            // se branca comeca por baixo | valido se estar se movimentando para cima
            else return fromLine - toLine > 0;
        }
        else{
            // se branca comeca por cima | valido se estar se movimentando para cima
            if(PieceColor.isWhiteUp()) return fromLine - toLine > 0;
            // se branca comeca por baixo | valido se estar se movimentando para baixo
            else return fromLine - toLine < 0;
        }
    }
}
