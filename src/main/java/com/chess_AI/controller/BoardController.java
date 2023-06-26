package com.chess_AI.controller;

import com.chess_AI.model.backup.Backup;
import com.chess_AI.model.chass.Board;
import com.chess_AI.model.chass.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import java.util.ArrayList;

/**
 * Esta classe representa o controller do model chass, ela contem as operacores de manipulacao de uma tabuleiro de xadrez.
 */
public class BoardController {

    /** Armazena os estados dos tabuleiros anteriores*/
    private ArrayList<Backup> backups = new ArrayList<>();

    /** Tabuleiro que armazena as pecas*/
    private Board board;

    /** Se eh o turno do usuario*/
    private boolean isUserTurn;

    /** Se o oponente ganhou*/
    private boolean isOpponentWon;

    /** Se o jogador ganhou*/
    private boolean isUserWon;

    /** Se algum peao chegou no final do tabuleiro e precisa selecionar a troca de peca*/
    private boolean isPawnChange;

    /** Cor da peca do jogador*/
    private final PieceColor USER_COLOR;

    /**
     * Cria objeto de BoardController.
     *
     * @param isUserTurn Se o usuario vai comecar
     * @param USER_COLOR Cor da peca do usuario
     */
    public BoardController(boolean isUserTurn, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserTurn;
        this.isUserWon = false;
        this.isOpponentWon = false;
        this.isPawnChange = false;
        this.USER_COLOR = USER_COLOR;
    }

    /**
     * Tenta movimentar a peca de uma dada posicao para outra.
     *
     * @param isUser Se eh o usuario movimenntando a peca
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da linha de destino
     * @return Se deu certo movimentar a peca
     */
    public boolean move(boolean isUser, int fromLine, int fromColumn, int toLine, int toColumn){
        // se nao for hora de trocar o tipo do peao quando ele chega no final do tabuleiro
        if(!isPawnChange) {
            // se nenhum jogador venceu ainda
            if (!isUserWon && !isOpponentWon) {

                // pega peca da posicao origem
                Piece fromPiece = board.getPiece(fromLine, fromColumn);

                // pega peca da posicao destino
                Piece toPiece = board.getPiece(toLine, toColumn);

                // se pecas existirem
                if (fromPiece != null && toPiece != null) {

                    if ((isUser && isUserTurn && fromPiece.getColor() == USER_COLOR) // se for usuario
                       || (!isUser && !isUserTurn && fromPiece.getColor() == PieceColor.getOpponentOf(USER_COLOR))) // se for oponente
                        {

                        // se for movimento valido de acordo com a regra da peca origem
                        if (fromPiece.isValidMove(board, fromLine, fromColumn, toLine, toColumn)) {

                            // cria backup do tabuleiro atual antes do movimento
                            addBackup();

                            // se nao foi possivel aplicar o Roque
                            if (!applyCastling(fromLine, fromColumn, toLine, toColumn)) {
                                // muda estado da peca para movida
                                fromPiece.setHasMoved(true);
                                // remove peca da posicao destino
                                board.removePiece(toLine, toColumn);
                                // troca posicoes de origem e destino
                                board.switchPieces(fromLine, fromColumn, toLine, toColumn);
                            }

                            // verifica vitoria
                            winCondition();

                            // verifica se algum peao chegou no final
                            if (verifyPawnOnFinal()) {
                                isPawnChange = true;
                            }
                            else{
                                // troca turno
                                changeTurn();
                            }

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


    /**
     * Tenta aplicar o movimento de castling (movimento do rei com a torre).
     *
     * @param fromLine Posicao da linha de origem
     * @param fromColumn Posicao da coluna de origem
     * @param toLine Posicao da linha de destino
     * @param toColumn Posicao da coluna de destino
     * @return Se deu certo
     */
    private boolean applyCastling(int fromLine, int fromColumn, int toLine, int toColumn){
        // pega peca da posicao de origem
        Piece fromPiece = board.getPiece(fromLine, fromColumn);

        // pega peca da posicao de destino
        Piece toPiece = board.getPiece(toLine, toColumn);

        // se as pecas existirem
        if(fromPiece != null && toPiece != null) {

            // se peca de origem for uma torre
            if (fromPiece instanceof Rook rook) {

                // se for possivel fazer o Roque
                if (rook.isCastlingMove(board, fromLine, fromColumn, toLine, toColumn)) {
                    // se para esquerda
                    if (fromColumn < toColumn) {
                        // move rei
                        board.switchPieces(toLine, toColumn, toLine, toColumn - 2);
                        // move torre
                        board.switchPieces(fromLine, fromColumn, fromLine, fromColumn + 3);
                    }
                    // para direita
                    else {
                        // move rei
                        board.switchPieces(toLine, toColumn, toLine, toColumn + 2);
                        // move torre
                        board.switchPieces(fromLine, fromColumn, fromLine, fromColumn - 2);
                    }

                    // atualiza estados das pecas
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

    /**
     * Troca a pecao do peao que chegou no final do tabuleiro.
     *
     * @param isUser Se eh o usuario
     * @param type Tipo da peca para colocar no lugar do peao
     * @return Se a troca foi realizada
     */
    public boolean changePawnType(boolean isUser, PieceType type){
        // se aguardando troca do peao
        if(isPawnChange){
            // se jogador certo que esta realizando a troca
            if((isUser && isUserTurn) || (!isUser && !isUserTurn)){

                // pega posicao da linha que vai procurar o peao
                int line;
                if(isUser) line = getFinalLineOf(USER_COLOR);
                else line = getFinalLineOf(PieceColor.getOpponentOf(USER_COLOR));

                // percorre colunas da linha de procura
                for(int column = 0; column < 8; column++){
                    // pega peca da linha procurada
                    Piece piece = board.getPiece(line, column);

                    // se peca eh um peao
                    if(piece instanceof Pawn){
                        // insere nova peca selecionada no lugar do peao
                        board.setPiece(type, piece.getColor(), line, column, piece.hasMoved());
                        // troca realizada
                        isPawnChange = false;
                        // trocando turno
                        changeTurn();

                        // troca realizada
                        return true;
                    }
                }
            }
        }
        // troca nao realizada
        return false;
    }

    /**
     * Verifica se algum peao chegou no final do tabuleiro.
     *
     * @return Se algum peao chegou no final do tabuleiro
     */
    private boolean verifyPawnOnFinal(){
        // cores dos pacas do tabuleiro
        PieceColor[] colors = {USER_COLOR, PieceColor.getOpponentOf(USER_COLOR)};

        // percorre cores
        for(PieceColor color : colors) {

            // pega linha final da cor
            int line = getFinalLineOf(color);

            // para cada coluna da linha final
            for(int column = 0; column < 8; column++) {

                // se peca for um peao
                Piece piece = board.getPiece(line, column);
                if(piece instanceof Pawn){
                    // precisa trocar o tipo
                    return true;
                }
            }
        }

        // nao precisa trocar o tipo
        return false;
    }

    /**
     * Pega a posicao da linha final de uma determinada cor.
     *
     * @param color Cor para pegar a posicao da linha final
     * @return Posicao da linha final
     */
    private int getFinalLineOf(PieceColor color){
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

    /**
     * Troca o turno
     */
    private void changeTurn(){
        this.isUserTurn = !isUserTurn;
    }

    /**
     * Verifica se alguem venceu e atualiza as variaveis isOpponentWon e isUserWon.
     */
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

    /**
     * Verifica se o rei de uma determinada cor morreu.
     *
     * @param color Cor do rei
     * @return Se o rei morreu
     */
    private boolean isKingDies(PieceColor color){
        // percorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = board.getPiece(i, j);
                // se a peca for o rei e da cor aliada
                if(piece instanceof King && piece.getColor() == color){
                    // rei nao morreu
                    return false;
                }
            }
        }

        // rei morreu
        return true;
    }

    /**
     * Verifica se o rei de uma determinada cor esta em check.
     *
     * @param color Cor do rei
     * @return Se esta em check
     */
    public boolean checkOnKing(PieceColor color){
        // pecorrendo possicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = board.getPiece(i, j);
                // se for o rei da cor procurada
                if(piece instanceof King && piece.getColor() == color){
                    // se a posicao for perigosa em que o rei esta
                    if(board.isDungerousPosition(color, i, j)){
                        // xeque mate
                        return true;
                    }
                }
            }
        }
        // nao xeque mate
        return false;
    }

    /**
     * Adiciona um backup do tabuleiro atual na lista de backups.
     */
    private void addBackup(){
        // crian backup
        Backup newBackup = new Backup(board, isUserTurn, isOpponentWon, isUserWon, isPawnChange);
        // adiciona backup a lista de backups
        backups.add(newBackup);
    }


    /**
     * Retorna o tabuleiro atual para o ultimo backup salvo.
     *
     * @return Se deu certo retornar
     */
    public boolean returnBackup(){
        // se existir backups anteriores
        if(backups.size() > 0){

            // pega ultimo backup salvo
            Backup last = backups.get(backups.size()-1);

            // volta estado do tabuleiro
            this.board = last.getBoard();
            this.isUserTurn = last.isUserTurn();
            this.isOpponentWon = last.isOpponentWon();
            this.isUserWon = last.isUserWon();
            this.isPawnChange = last.isPawnChange();

            // deleta ultimo backup da lista de backups
            backups.remove(last);

            // backup realizado
            return true;
        }
        // backup nao realizado
        return false;
    }


    /**
     * Pega o tipo da peca de uma determinada posicao do tabuleiro.
     *
     * @param line Posicao da linha da peca
     * @param column Posicao da coluna da peca
     * @return Tipo da peca
     */
    public PieceType getTypeOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        return piece.getType();
    }

    /**
     * Pega a cor da peca de uma determinada posicao do tabuleiro.
     *
     * @param line Posicao da linha da peca
     * @param column Posicao da coluna da peca
     * @return Cor da peca
     */
    public PieceColor getColorOf(int line, int column){
        Piece piece = board.getPiece(line, column);
        return piece.getColor();
    }

    /**
     * Retorna se eh o turno do usuario.
     *
     * @return Se eh o turno do usuario
     */
    public boolean isUserTurn() {
        return isUserTurn;
    }

    /**
     * Retorna se o oponente venceu.
     *
     * @return Se o oponente venceu
     */
    public boolean isOpponentWon() {
        return isOpponentWon;
    }

    /**
     * Retorna se o usuario venceu.
     *
     * @return Se o usuario venceu
     */
    public boolean isUserWon() {
        return isUserWon;
    }

    /**
     * Retorna se alguem precisa trocar o tipo de algum peao que chegou no final do tabuleiro.
     *
     * @return Se alguem precisa trocar o tipo do peao
     */
    public boolean isPawnChange() {
        return isPawnChange;
    }
}
