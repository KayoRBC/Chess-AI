package com.Kayo.model.chass;

import com.Kayo.model.chass.Piece.*;
import com.Kayo.util.PieceColor;

public class Board {

    // matrix board
    private Piece[][] pieces;

    public Board() {
        pieces = createInitialPieces();
    }

    private Board(Piece[][] pieces){
        this.pieces = pieces;
    }

    private Piece[][] createInitialPieces(){
        Piece[][] pieces = new Piece[8][8];

        // inserindo pecas brancas da fileira de tras
        pieces[0] = createBackLine(PieceColor.BLACK);
        // inserindo pecas brancas da frente
        pieces[1] = createFrontLine(PieceColor.BLACK);
        // inserindo parte do tabuleiro vazias
        for(int i = 2; i < 6; i++){
            pieces[i] = createNullLine();
        }
        // inserindo pecas pretas da frente
        pieces[6] = createFrontLine(PieceColor.WHITE);
        // inserindo peca pretas de traz
        pieces[7] = createBackLine(PieceColor.WHITE);

        // retornando tabuleiro
        return pieces;
    }

    private Piece[] createNullLine(){
        // criando uma linha com pecas vazias
        Piece[] nullLine = new Piece[8];
        for(int i = 0; i < nullLine.length; i++){
            nullLine[i] = new NullPiece();
        }
        // retornando a linha
        return nullLine;
    }

    private Piece[] createFrontLine(PieceColor color){
        // criando uma linha de peoes
        Piece[] frontLine = new Piece[8];
        for(int i = 0; i < frontLine.length; i++){
            frontLine[i] = new Pawn(color);
        }
        // retornando a linha
        return frontLine;
    }

    private Piece[] createBackLine(PieceColor color){
        // criando linha de tras
        Piece[] backLine = new Piece[8];
        backLine[0] = new Rook(color);
        backLine[1] = new Knight(color);
        backLine[2] = new Bishop(color);
        backLine[3] = new Queen(color);
        backLine[4] = new King(color);
        backLine[5] = new Bishop(color);
        backLine[6] = new Knight(color);
        backLine[7] = new Rook(color);

        // retornando linha de tras
        return backLine;
    }

    public void switchPieces(int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = getPiece(fromLine, fromColumn);
        Piece toPiece = getPiece(toLine, toColumn);
        // se pecas existirem troca as pecas de posicao
        if(fromPiece != null && toPiece != null) {
            pieces[toLine][toColumn] = fromPiece;
            pieces[fromLine][fromColumn] = toPiece;
        }
    }

    public void removePiece(int line, int column){
        // pegando peca
        Piece piece = getPiece(line, column);
        // se peca existir troque por uma peca vazia
        if(piece != null){
            pieces[line][column] = new NullPiece();
        }
    }

    public Piece getPiece(int line, int column){
        try{
            // se peca existir retorna ela
            return pieces[line][column];
        } catch (Exception e) {
            // se peca nao existir retorna null
            return null;
        }
    }

    public boolean isDungerousPosition(PieceColor allyColor, int checkLine, int checkColumn){
        // pegando cada posicao do tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                Piece fromPiece = getPiece(fromLine, fromColumn);
                // se peca for inimiga
                if(fromPiece.getColor() != allyColor){
                    Piece currentPiece = getPiece(fromLine, fromColumn);
                    // se peca inimiga pode ir para a posicao de verificacao
                    if(currentPiece.isValidMove(this, fromLine, fromColumn, checkLine, checkColumn)){
                        // posicao perigosa
                        return true;
                    }
                }
            }
        }
        // posicao nao perigosa
        return false;
    }
    public boolean isSafePosition(PieceColor allyColor, int checkLine, int checkColumn){
        // pegando cada posicao do tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                Piece fromPiece = getPiece(fromLine, fromColumn);
                // se peca for aliada
                if(fromPiece.getColor() == allyColor){
                    // verificando se pode fazer o movimento
                    Piece currentPiece = getPiece(0, 1);
                    // se peca aliada pode ir para a posicao de verificacao
                    if(currentPiece.isValidMove(this, fromLine, fromColumn, checkLine, checkColumn)){
                        // posicao protegida
                        return true;
                    }
                }
            }
        }
        // posicao nao protegida
        return false;
    }

    public Board createClone(){
        Piece[][] piecesClone = new Piece[8][8];
        // percorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                // pegando peca do tabuleiro original
                Piece original = pieces[i][j];
                // inserindo clone dessa peca no tabuleiro clone
                switch (original.getType()) {
                    case BISHOP -> piecesClone[i][j] = new Bishop(original.getColor());
                    case KING -> piecesClone[i][j] = new King(original.getColor());
                    case KNIGHT -> piecesClone[i][j] = new Knight(original.getColor());
                    case PAWN -> piecesClone[i][j] = new Pawn(original.getColor());
                    case QUEEN -> piecesClone[i][j] = new Queen(original.getColor());
                    case ROOK -> piecesClone[i][j] = new Rook(original.getColor());
                    default -> piecesClone[i][j] = new NullPiece();
                }
                // atualizando estado do clone
                piecesClone[i][j].setHasMoved(original.hasMoved());
            }
        }
        // retornando tabuleiro clone
        return new Board(piecesClone);
    }
}