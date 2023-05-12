package com.Kayo.model.chass;

import com.Kayo.model.chass.Piece.*;
import com.Kayo.model.chass.movements.MovementRules;
import com.Kayo.util.PieceColor;

public class Board {

    // matrix board
    Piece[][] pieces;

    public Board() {
        pieces = createInitialPieces();
    }

    private Piece[][] createInitialPieces(){
        Piece[][] pieces = new Piece[8][8];
        pieces[0] = createBackLine(PieceColor.BLACK);
        pieces[1] = createFrontLine(PieceColor.BLACK);
        for(int i = 2; i < 6; i++){
            pieces[i] = createNullLine();
        }
        pieces[6] = createFrontLine(PieceColor.WHITE);
        pieces[7] = createBackLine(PieceColor.WHITE);
        return pieces;
    }

    private Piece[] createNullLine(){
        Piece[] nullLine = new Piece[8];
        for(int i = 0; i < nullLine.length; i++){
            nullLine[i] = new NullPiece();
        }
        return nullLine;
    }

    private Piece[] createFrontLine(PieceColor color){
        Piece[] frontLine = new Piece[8];
        for(int i = 0; i < frontLine.length; i++){
            frontLine[i] = new Pawn(color);
        }
        return frontLine;
    }

    private Piece[] createBackLine(PieceColor color){
        Piece[] backLine = new Piece[8];

        backLine[0] = new Rook(color);
        backLine[1] = new Knight(color);
        backLine[2] = new Bishop(color);
        backLine[3] = new Queen(color);
        backLine[4] = new King(color);
        backLine[5] = new Bishop(color);
        backLine[6] = new Knight(color);
        backLine[7] = new Rook(color);

        return backLine;
    }

    public void switchPieces(int fromLine, int fromColumn, int toLine, int toColumn){
        Piece fromPiece = getPiece(fromLine, fromColumn);
        Piece toPiece = getPiece(toLine, toColumn);

        if(fromPiece != null && toPiece != null) {
            pieces[toLine][toColumn] = fromPiece;
            pieces[fromLine][fromColumn] = toPiece;
        }
    }

    public void removePiece(int line, int column){
        Piece piece = getPiece(line, column);
        if(piece != null){
            pieces[line][column] = new NullPiece();
        }
    }

    public Piece getPiece(int line, int column){
        try{
            return pieces[line][column];
        } catch (Exception e) {
            return null;
        }
    }

    public boolean hasPositionDominated(PieceColor allyColor, int toLine, int toColumn){
        // pegando cada peca inimiga e verificando se ela pode mover para aquela posicao
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                Piece fromPiece = getPiece(fromLine, fromColumn);
                if(fromPiece.getColor() != allyColor){
                    // verificando se pode fazer o movimento
                    MovementRules movementRules = fromPiece.getMovementRules();
                    if(movementRules != null) {
                        if (movementRules.isValidMove(this, fromLine, fromColumn, toLine, toColumn)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}