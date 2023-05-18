package com.Kayo.controller;

import com.Kayo.model.backup.Backup;
import com.Kayo.model.chass.Board;
import com.Kayo.model.chass.Piece.*;
import com.Kayo.util.PieceColor;
import com.Kayo.util.PieceType;

import java.util.ArrayList;

public class BoardController {

    private ArrayList<Backup> backups = new ArrayList<>();

    private Board board;

    private boolean isUserTurn;
    private boolean isOpponentWon;
    private boolean isUserWon;

    // se algum peao chegar no final do tabuleiro
    private boolean isPawnChange;

    private final PieceColor USER_COLOR;
    private final PieceColor OPPONENT_COLOR;

    public BoardController(boolean isUserTurn, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserTurn;
        this.isUserWon = false;
        this.isOpponentWon = false;
        this.isPawnChange = false;
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
        // se nao for hora de trocar o tipo do peao quando ele chega no final do tabuleiro
        if(!isPawnChange) {
            // se nenhum jogador venceu ainda
            if (!isUserWon && !isOpponentWon) {
                Piece fromPiece = board.getPiece(fromLine, fromColumn);
                Piece toPiece = board.getPiece(toLine, toColumn);
                // se pecas existirem
                if (fromPiece != null && toPiece != null) {
                    // se for turno do respectivo jogador
                    if ((isUser && isUserTurn && fromPiece.getColor() == USER_COLOR)
                            || (!isUser && !isUserTurn && fromPiece.getColor() == OPPONENT_COLOR)) {

                        // se for movimento valido de acordo com a regra da peca origem
                        if (fromPiece.isValidMove(board, fromLine, fromColumn, toLine, toColumn)) {

                            // criando backup antes de realizar a jogada
                            addBackup(fromLine, fromColumn, toLine, toColumn);

                            // se nao foi possivel aplicar o Roque
                            if (!applyCastling(fromLine, fromColumn, toLine, toColumn)) {
                                // mudando estado da peca para movida
                                fromPiece.setHasMoved(true);
                                // removendo peca da posicao destino
                                board.removePiece(toLine, toColumn);
                                // trocando posicoes de origem e destino
                                board.switchPieces(fromLine, fromColumn, toLine, toColumn);
                            }

                            // verificando vitoria
                            winCondition();

                            // verificando se algum peao chegou no final
                            if (verifyPawnOnFinal()) {
                                isPawnChange = true;
                                return true;
                            }

                            // trocando turno
                            changeTurn();

                            // movimentacao realizada
                            return true;
                        }
                    }
                }
            }
        }
        // movimentacao nao realizada
        return false;
    }

    private boolean applyCastling(int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null && toPiece != null) {
            if (fromPiece instanceof Rook rook) {
                // se for possivel fazer o Roque
                if (rook.isCastlingMove(board, fromLine, fromColumn, toLine, toColumn)) {
                    // se para esquerda
                    if (fromColumn < toColumn) {
                        // movendo rei
                        board.switchPieces(toLine, toColumn, toLine, toColumn - 2);
                        // movendo torre
                        board.switchPieces(fromLine, fromColumn, fromLine, fromColumn + 3);
                    }
                    // para direita
                    else {
                        // movendo rei
                        board.switchPieces(toLine, toColumn, toLine, toColumn + 2);
                        // movendo torre
                        board.switchPieces(fromLine, fromColumn, fromLine, fromColumn - 2);
                    }
                    // atualizando estados
                    fromPiece.setHasMoved(true);
                    toPiece.setHasMoved(true);

                    // movimento realizado
                    return true;
                }
            }
        }
        // movimento nao realizado
        return false;
    }

    public boolean changePawnType(boolean isUser, PieceType type){
        // se poder trocar o tipo do peao e for do respectivo jogador
        if(isPawnChange){
            // se usuario
            if(isUser && isUserTurn){
                int line = 0;
                for(int column = 0; column < 8; column++){
                    Piece piece = board.getPiece(line, column);
                    // se peao
                    if(piece instanceof Pawn){
                        // inserindo nova peca selecionada no lugar do peao
                        board.setPiece(type, piece.getColor(), line, column, piece.hasMoved());
                        // troca realizada
                        isPawnChange = false;
                        return true;
                    }
                }
            }
            // se oponente
            else if(!isUser && !isUserTurn){
                int line = 7;
                for(int column = 0; column < 8; column++){
                    Piece piece = board.getPiece(line, column);
                    // se peao
                    if(piece instanceof Pawn){
                        // inserindo nova peca selecionada no lugar do peao
                        board.setPiece(type, piece.getColor(), line, column, piece.hasMoved());
                        // troca realizada
                        isPawnChange = false;
                        return true;
                    }
                }
            }
        }
        // troca nao realizada
        return false;
    }

    // verifica se existe algum pao no final do tabuleiro
    private boolean verifyPawnOnFinal(){
        for(int column = 0; column < 8; column++){
            if(isPawnOnFinal(0, column) || isPawnOnFinal(7, column)) {
                return true;
            }
        }
        return false;
    }

    // verifica se eh um peao no final do tabuleiro
    private boolean isPawnOnFinal(int line, int column){
        // pegando peca
        Piece piece = board.getPiece(line, column);
        // se peca existir
        if(piece != null){
            // se a peca for um peao
            if(piece instanceof Pawn){
                PieceColor color = piece.getColor();
                // se a peca for branca e na linha 0
                if(color == PieceColor.WHITE && line == 0){
                    // chegou no final
                    return true;
                }
                // se a peca for preta e na linha 7
                else if(color == PieceColor.WHITE && line == 7){
                    // chegou no final
                    return true;
                }
            }
        }
        // nao chegou no final
        return false;
    }

    private void changeTurn(){
        this.isUserTurn = !isUserTurn;
    }

    // verifica se algum rei morreu e se algum morrer atualiza quem ganhou
    private void winCondition(){
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

    // verificar xeque no rei aliado
    public boolean check(PieceColor allyColor){
        // pecorrendo possicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = board.getPiece(i, j);
                // se for o rei da cor procurada
                if(piece instanceof King && piece.getColor() == allyColor){
                    // se a posicao for perigosa em que o rei esta
                    if(board.isDungerousPosition(allyColor, i, j)){
                        // xeque mate
                        return true;
                    }
                }
            }
        }
        // nao xeque mate
        return false;
    }

    private boolean addBackup(int fromLine, int fromColumn, int toLine, int toColumn){
        // pegando pecas
        Piece fromPiece = board.getPiece(fromLine, fromColumn);
        Piece toPiece = board.getPiece(toLine, toColumn);
        // se as pecas existirem
        if(fromPiece != null && toPiece != null) {
            // criando backup
            Backup newBackup = new Backup(board, isUserTurn, isOpponentWon, isUserWon, isPawnChange);
            // adicionando backup a lista de backups
            backups.add(newBackup);
            // backup adicionado
            return true;
        }
        // backup nao adicionado
        return false;
    }

    public boolean returnBackup(){
        // se existir backups anteriores
        if(backups.size() > 0){
            Backup last = backups.get(backups.size()-1);
            // voltando estado do tabuleiro
            this.board = last.getBoard();
            this.isUserTurn = last.isUserTurn();
            this.isOpponentWon = last.isOpponentWon();
            this.isUserWon = last.isUserWon();
            this.isPawnChange = last.isPawnChange();

            // deletando backup da lista de backups
            backups.remove(last);

            // backup realizado
            return true;
        }
        // backup nao realizado
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

    public boolean isUserTurn() {
        return isUserTurn;
    }

    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    public boolean isUserWon() {
        return isUserWon;
    }

    public boolean isPawnChange() {
        return isPawnChange;
    }
}
