package com.Kayo.controller;

import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.*;
import com.Kayo.model.chass.movements.MovementRules;
import com.Kayo.util.PieceColor;
import com.Kayo.view.PieceType;

public class BoardController {

    private Board board;

    public BoardController(){
        board = new Board();
    }

    public boolean move(int fromLine, int fromColumn, int toLine, int toColumn){
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        if(fromPiece != null && !(fromPiece instanceof NullPiece)){
            MovementRules movementRules = fromPiece.getMovementRules();
            if(movementRules.isValidMove(board, fromLine, fromColumn, toLine, toColumn)){
                fromPiece.hasMoved();
                board.switchPieces(fromLine, fromColumn, toLine, toColumn);
                board.removePiece(fromLine, fromColumn);
                return true;
            }
        }
        return false;
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
