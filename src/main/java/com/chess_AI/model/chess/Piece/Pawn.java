package com.chess_AI.model.chess.Piece;

import com.chess_AI.model.chess.Board;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

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
    public boolean isValidMove(Board board, int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);

        int horizontalDistance = Math.abs(fromColumn - toColumn);
        int verticalDistance = Math.abs(fromLine - toLine);

        if(fromPiece instanceof Pawn && toPiece != null
                && fromPiece.getColor() != toPiece.getColor()
                && verifyDirection(board, fromColumn, fromLine, toLine))
            {

            if(toPiece instanceof NullPiece){
                if(fromPiece.hasMoved) return verticalDistance == 1
                                                && isVerticalValid(board, fromLine, fromColumn, toLine, toColumn);

                // peca nao foi movimentada ainda
                else return verticalDistance < 3
                            && isVerticalValid(board, fromLine, fromColumn, toLine, toColumn);
            }
            else{
                boolean isOneStepDiagonal = horizontalDistance == 1 && verticalDistance == 1;

                return isOneStepDiagonal && isDiagonalValid(board, fromLine, fromColumn, toLine, toColumn);
            }
        }

        return false;
    }

    /**
     * Verifica a direcao de movimentacao vertical de acordo com a cor
     *
     * @param board Tabuleiro que esta a peca
     * @param fromLine Posicao da linha de origem
     * @param column Posicao da coluna
     * @param toLine Posicao da linha de destino
     * @return Se direcao da movimentacao certa
     */
    private boolean verifyDirection(Board board, int column, int fromLine, int toLine){
        Piece fromPiece = board.getPiece(fromLine, column);
        // se peca for branca
        if(fromPiece.getColor() == PieceColor.WHITE){
            // se branca comeca por cima | valido se estar se movimentando para baixo
            if(PieceColor.isWhiteUp()) return fromLine - toLine < 0;
                // se branca comeca por baixo | valido se estar se movimentando para cima
            else return fromLine - toLine > 0;
        }
        // se peca for preta
        else{
            // se branca comeca por cima | valido se estar se movimentando para cima
            if(PieceColor.isWhiteUp()) return fromLine - toLine > 0;
                // se branca comeca por baixo | valido se estar se movimentando para baixo
            else return fromLine - toLine < 0;
        }
    }
}
