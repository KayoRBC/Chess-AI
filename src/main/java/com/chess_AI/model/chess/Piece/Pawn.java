package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;

/**
 * Esta classe representa a peca do peao, possui as regras de movimentacao e o estado da peca.
 */
public class Pawn extends Piece{

    /**
     * Cria e retorna um peao de uma determinada cor.
     *
     * @param color Cor do peao
     */
    public Pawn(PieceColor color) {
        super(color, PieceType.PAWN);
    }

    @Override
    public boolean isValidMove(Board board, Move move) {
        Piece fromPiece = board.getPiece(move.FROM);
        Piece toPiece = board.getPiece(move.TO);

        int verticalDistance = Math.abs(move.FROM.LINE - move.TO.LINE);
        int horizontalDistance = Math.abs(move.FROM.COLUMN - move.TO.COLUMN);

        if(fromPiece instanceof Pawn && toPiece != null && fromPiece.getColor() != toPiece.getColor()
                && verifyDirection(fromPiece.getColor(), move.FROM.LINE, move.TO.LINE)){

            if(toPiece instanceof NullPiece){
                if(fromPiece.hasMoved) return verticalDistance == 1 && isVerticalValid(board, move);

                // peca nao foi movimentada ainda
                else return verticalDistance < 3 && isVerticalValid(board, move);
            }
            else{
                boolean isOneStepDiagonal = horizontalDistance == 1 && verticalDistance == 1;
                return isOneStepDiagonal && isDiagonalValid(board, move);
            }
        }

        return false;
    }

    /**
     * Verifica se a direcao de movimentacao vertical esta certa de acordo com a cor.
     *
     * @param color Cor da peca para verificar
     * @param fromLine Posicao da linha de origem
     * @param toLine Posicao da linha de destino
     * @return Se direcao da movimentacao esta certa
     */
    private boolean verifyDirection(PieceColor color, int fromLine, int toLine){
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
