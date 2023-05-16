package com.Kayo.controller;

import com.Kayo.model.ai.MovePositions;
import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.*;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

import java.util.ArrayList;

public class BoardController {

    private Board board;

    private boolean isUserTurn;
    private boolean isOpponentWon;
    private boolean isUserWon;

    private final PieceColor USER_COLOR;
    private final PieceColor OPPONENT_COLOR;

    public BoardController(boolean isUserTurn, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserTurn;
        this.isUserWon = false;
        this.isOpponentWon = false;
        this.USER_COLOR = USER_COLOR;
        // seleciona cor da peca oponente de acordo com a peca do usuario
        if(USER_COLOR == PieceColor.WHITE){
            OPPONENT_COLOR = PieceColor.BLACK;
        }
        else{
            OPPONENT_COLOR = PieceColor.WHITE;
        }
    }

    private BoardController(Board board, boolean isUserTurn, boolean isUserWon, boolean isOpponentWon, PieceColor USER_COLOR){
        this.board = board;
        this.isUserTurn = isUserTurn;
        this.isUserWon = isUserWon;
        this.isOpponentWon = isOpponentWon;
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
        // se nenhum jogador venceu ainda
        if(!isUserWon && !isOpponentWon) {
            Piece fromPiece = board.getPiece(fromLine, fromColumn);
            Piece toPiece = board.getPiece(toLine, toColumn);
            // se pecas existirem
            if (fromPiece != null && toPiece != null) {
                // se for turno do respectivo jogador
                if ((isUser && isUserTurn && fromPiece.getColor() == USER_COLOR)
                        || (!isUser && !isUserTurn && fromPiece.getColor() == OPPONENT_COLOR)) {

                    // se for movimento valido de acordo com a regra da peca origem
                    if (fromPiece.isValidMove(board, fromLine, fromColumn, toLine, toColumn)) {

                        // se nao foi possivel aplicar o Roque
                        if(!applyCastling(fromLine, fromColumn, toLine, toColumn)) {
                            // mudando estado da peca para movida
                            fromPiece.setHasMoved(true);
                            // removendo peca da posicao destino
                            board.removePiece(toLine, toColumn);
                            // trocando posicoes de origem e destino
                            board.switchPieces(fromLine, fromColumn, toLine, toColumn);
                        }
                        // trocando turno
                        changeTurn();

                        // verificando xeque mate no rei do usuario
                        checkMate(USER_COLOR);
                        // verificando xeque mate no rei do oponente
                        checkMate(OPPONENT_COLOR);

                        // verificando vitoria
                        verifyKings();

                        // movimentacao realizada
                        return true;
                    }
                }
            }
        }
        // movimentacao nao realizada
        return false;
    }

    private boolean applyCastling(int fromLine, int fromColumn, int toLine, int toColumn){
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        if(fromPiece instanceof Rook rook){
            // se for possivel fazer o Roque
            if(rook.isCastlingMove(board, fromLine, fromColumn, toLine, toColumn)){
                // se para esquerda
                if(fromColumn < toColumn){
                    // movendo rei
                    board.switchPieces(toLine, toColumn, toLine, toColumn-2);
                    // movendo torre
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn+3);
                }
                // para direita
                else{
                    // movendo rei
                    board.switchPieces(toLine, toColumn, toLine, toColumn+2);
                    // movendo torre
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn-2);
                }
                // atualizando estados
                fromPiece.setHasMoved(true);
                toPiece.setHasMoved(true);

                // movimento realizado
                return true;
            }
        }
        // movimento nao realizado
        return false;
    }

    private void changeTurn(){
        this.isUserTurn = !isUserTurn;
    }

    // verifica se algum rei morreu e se algum morrer atualiza quem ganhou
    private void verifyKings(){
        // se o rei do usuario morreu
        if(isKingDies(USER_COLOR)){
            // oponente venceu
            isOpponentWon = true;
        }
        // se o rei do oponente morreu
        if(isKingDies(OPPONENT_COLOR)){
            // usuario venceu
            isUserWon = true;
        }
    }

    private boolean isKingDies(PieceColor allyColor){
        // percorrendo posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = board.getPiece(i, j);
                // se a peca for o rei e da cor aliada
                if(piece instanceof King && piece.getColor() == allyColor){
                    // rei nao morreu
                    return false;
                }
            }
        }

        // rei morreu
        return true;
    }

    // verificar xeque mate no rei aliado
    public boolean checkMate(PieceColor allyColor){
        // pecorrendo possicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = board.getPiece(i, j);
                // se for o rei da cor procurada
                if(piece instanceof King && piece.getColor() == allyColor){
                    // resetando estado de xeque mate
                    ((King) piece).setCheckMated(false);
                    // se a posicao for perigosa em que o rei esta
                    if(board.isDungerousPosition(allyColor, i, j)){
                        // atualizando estado do rei
                        ((King) piece).setCheckMated(true);
                        // tem xeque mate
                        return true;
                    }
                }
            }
        }
        // nao tem xeque mate
        return false;
    }


    public PieceType getTypeOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        return piece.getType();
    }

    public PieceColor getColorOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        return piece.getColor();
    }

    public BoardController createClone(){
        return new BoardController(board.createClone(), isUserWon, isOpponentWon, isUserTurn, USER_COLOR);
    }

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    public boolean isUserWon() {
        return isUserWon;
    }
}
