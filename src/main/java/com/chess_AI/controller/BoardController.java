package com.chess_AI.controller;

import com.chess_AI.model.backup.Backup;
import com.chess_AI.model.chass.Board;
import com.chess_AI.model.chass.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import java.util.ArrayList;

public class BoardController {

    // lista com os estados do tabuleiro anteriores
    private ArrayList<Backup> backups = new ArrayList<>();

    // tabuleiro que armazena as pecas
    private Board board;

    // se eh turno do usuario
    private boolean isUserTurn;

    // se o oponente ganhou
    private boolean isOpponentWon;

    // se o jogador ganhou
    private boolean isUserWon;

    // se algum peao chegou no final do tabuleiro e precisa selecionar a troca de peca
    private boolean isPawnChange;

    // cor da peca do usuario
    private final PieceColor USER_COLOR;

    public BoardController(boolean isUserTurn, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserTurn;
        this.isUserWon = false;
        this.isOpponentWon = false;
        this.isPawnChange = false;
        this.USER_COLOR = USER_COLOR;
    }

    // movimentar uma peca de um lugar para o outro
    // return true caso de certo
    // return false caso nao de certo
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
                            || (!isUser && !isUserTurn && fromPiece.getColor() == PieceColor.getOpponentOf(USER_COLOR))) {

                        // se for movimento valido de acordo com a regra da peca origem
                        if (fromPiece.isValidMove(board, fromLine, fromColumn, toLine, toColumn)) {

                            // criando backup do tabuleiro atual antes do movimento
                            addBackup();

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



    // aplica o movimento de castiling
    // return true caso conseguiu realizar
    // return false caso nao conseguiu realizar
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
    
    // troca o peao por uma nova peca escolhida caso algum peao tenha chegado no final do tabuleiro
    // return true caso deu certo
    // return false caos nao deu certo
    public boolean changePawnType(boolean isUser, PieceType type){
        // se poder trocar o tipo do peao e for do respectivo jogador
        if(isPawnChange){
            // se jogador certo
            if((isUser && isUserTurn) || (!isUser && !isUserTurn)){
                // pegando linha que vai procurar o peao
                int line;
                if(isUser) line = getLinePawnChangeOf(USER_COLOR);
                else line = getLinePawnChangeOf(PieceColor.getOpponentOf(USER_COLOR));

                // percorrendo colunas da linha
                for(int column = 0; column < 8; column++){
                    // pegando peca
                    Piece piece = board.getPiece(line, column);
                    // se peca eh um peao
                    if(piece instanceof Pawn){
                        // inserindo nova peca selecionada no lugar do peao
                        board.setPiece(type, piece.getColor(), line, column, piece.hasMoved());
                        // troca realizada
                        isPawnChange = false;
                        // trocando turno
                        changeTurn();
                        return true;
                    }
                }
            }
        }
        // troca nao realizada
        return false;
    }
    
    // verifica se existe algum pao no final do tabuleiro
    // return true caso tenha
    // return false naso nao tenha
    private boolean verifyPawnOnFinal(){
        PieceColor[] colors = {USER_COLOR, PieceColor.getOpponentOf(USER_COLOR)};
        for(PieceColor color : colors) {
            int line = getLinePawnChangeOf(color);
            for(int column = 0; column < 8; column++) {
                Piece piece = board.getPiece(line, column);
                if(piece instanceof Pawn){
                    return true;
                }
            }
        }
        return false;
    }

    private int getLinePawnChangeOf(PieceColor color){
        int line;
        if(PieceColor.isWhiteUp()){
            if(color == PieceColor.WHITE) line = 7;
            else line = 0;
        }
        else{
            if(color == PieceColor.WHITE) line = 0;
            else line = 7;
        }
        return line;
    }

    // troca o turno
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
        if(isKingDies(PieceColor.getOpponentOf(USER_COLOR))){
            // usuario venceu
            isUserWon = true;
        }
    }

    // verifica se o rei morrei de um determinada cor
    // return true caso tenha morrido
    // return false caso nao tenha morrido
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
    // return true se check
    // return false se rei seguro
    public boolean checkOnKing(PieceColor allyColor){
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

    // adiciona um backup da lista de backups do tabuleiro atual
    private void addBackup(){
        // criando backup
        Backup newBackup = new Backup(board, isUserTurn, isOpponentWon, isUserWon, isPawnChange);
        // adicionando backup a lista de backups
        backups.add(newBackup);
    }

    // retorna para o ultimo backup do tabuleiro
    // return true caso retornou
    // return false caso nao foi possivel retornar
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
