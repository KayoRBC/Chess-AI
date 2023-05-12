package com.Kayo.controller;

import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.*;
import com.Kayo.model.chass.movements.MovementRules;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

public class BoardController {

    private Board board;

    private boolean isUserTurn;

    private final PieceColor USER_COLOR;
    private final PieceColor OPPONENT_COLOR;

    public BoardController(boolean isUserTurn, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserTurn;
        this.USER_COLOR = USER_COLOR;
        // seleciona cor da peca oponente de acordo com a peca do usuario
        if(USER_COLOR == PieceColor.WHITE){
            OPPONENT_COLOR = PieceColor.BLACK;
        }
        else{
            OPPONENT_COLOR = PieceColor.WHITE;
        }
    }

    public boolean move(boolean isUser, int fromLine, int fromColumn, int toLine, int toColumn){
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        // se existir peca origem e a peca for diferente da peca vazia
        if(fromPiece != null && !(fromPiece instanceof NullPiece)){
            // se for turno do respectivo jogador
            if((isUser && isUserTurn) || (!isUser && !isUserTurn)){
                MovementRules movementRules = fromPiece.getMovementRules();
                // se for movimento valido de acordo com a regra da peca
                if(movementRules.isValidMove(board, fromLine, fromColumn, toLine, toColumn)){
                    fromPiece.hasMoved();
                    board.switchPieces(fromLine, fromColumn, toLine, toColumn);
                    board.removePiece(fromLine, fromColumn);
                    changeTurn();
                    return true;
                }
            }
        }
        return false;
    }

    private void changeTurn(){
        this.isUserTurn = !isUserTurn;
    }

    public PieceType getTypeOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        if(piece != null){
            if(piece instanceof Rook){
                return PieceType.ROOK;
            }
            else if(piece instanceof Knight){
                return PieceType.KNIGHT;
            }
            else if(piece instanceof Bishop){
                return PieceType.BISHOP;
            }
            else if(piece instanceof Queen){
                return PieceType.QUEEN;
            }
            else if(piece instanceof King){
                return PieceType.KING;
            }
            else if(piece instanceof Pawn){
                return PieceType.PAWN;
            }
            else if(piece instanceof  NullPiece){
                return PieceType.NULL;
            }
        }
        return null;
    }

    public PieceColor getColorOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        return piece.getColor();
    }
}
