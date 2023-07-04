package com.chess_AI.controller;

import com.chess_AI.model.chess.Board;
import com.chess_AI.model.chess.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;

import java.util.ArrayList;

/**
 * Esta classe representa o controller do model chess, ela contem as operacores de manipulacao de uma tabuleiro de xadrez.
 */
public class BoardController implements Cloneable{

    /** Armazena os estados dos tabuleiros anteriores*/
    private ArrayList<BoardController> backups = new ArrayList<>();

    /** Tabuleiro que armazena as pecas*/
    private Board board;

    /** Se eh o turno do usuario*/
    private boolean isUserTurn;

    /** Se a IA ganhou ganhou*/
    private boolean isAIWon;

    /** Se o jogador ganhou*/
    private boolean isUserWon;

    /** Cor da peca do usuario*/
    private final PieceColor USER_COLOR;

    /**
     * Cria objeto de BoardController.
     *
     * @param isUserStarts Se o usuario que vai comecar
     * @param USER_COLOR Cor da peca do usuario
     */
    public BoardController(boolean isUserStarts, PieceColor USER_COLOR){
        board = new Board();
        this.isUserTurn = isUserStarts;
        this.isUserWon = false;
        this.isAIWon = false;
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
        if(!(isUserWon && isAIWon && hasPawnOnFinal())){
            Piece fromPiece = board.getPiece(fromLine, fromColumn);

            boolean isUserMove = isUser && isUserTurn
                                && fromPiece.getColor() == USER_COLOR;

            boolean isAIMove = !isUser && !isUserTurn
                                && fromPiece.getColor() == PieceColor.getOpponentOf(USER_COLOR);

            if((isUserMove || isAIMove)) {

                if(applyCastling(fromLine, fromColumn, toLine, toColumn)) return true;

                else if(fromPiece.isValidMove(board, fromLine, fromColumn, toLine, toColumn)){
                    addBackup();

                    fromPiece.setHasMoved(true);
                    board.removePiece(toLine, toColumn);
                    board.switchPieces(fromLine, fromColumn, toLine, toColumn);

                    verifyWin();

                    if(!hasPawnOnFinal()) changeTurn();

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica e aplica caso possivel o movimento de castling
     *
     * @param fromLine Posicao da linha de inicio
     * @param fromColumn Posicao da coluna de inicio
     * @param toLine Posicao da linha de "destino"
     * @param toColumn Posicao da coluna de "destino"
     * @return Se aplicou o castling
     */
    private boolean applyCastling(int fromLine, int fromColumn, int toLine, int toColumn) {
        Piece fromPiece = board.getPiece(fromLine, fromColumn);

        if(fromPiece != null && fromPiece.isCastlingMove(board, fromLine, fromColumn, toLine, toColumn)) {
            addBackup();

            int horizontalDistance = fromColumn - toColumn;

            if (fromPiece instanceof King) {
                boolean isRight = horizontalDistance < 0;
                if (isRight) {
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn + 2);
                    board.switchPieces(toLine, toColumn, toLine, toColumn - 2);
                } else {
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn - 2);
                    board.switchPieces(toLine, toColumn, toLine, toColumn + 3);
                }

            }
            else{ //if (fromPiece instanceof Rook)
                boolean isRight = horizontalDistance > 0;
                if (isRight) {
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn - 2);
                    board.switchPieces(toLine, toColumn, toLine, toColumn + 2);
                } else {
                    board.switchPieces(fromLine, fromColumn, fromLine, fromColumn + 3);
                    board.switchPieces(toLine, toColumn, toLine, toColumn - 2);
                }
            }

            Piece toPiece = board.getPiece(toLine, toColumn);
            fromPiece.setHasMoved(true);
            toPiece.setHasMoved(true);

            return true;
        }
        return false;
    }

    /**
     * Troca o peao que chegou no final do tabuleiro por um novo tipo de peca.
     *
     * @param isUser Se eh o usuario
     * @param newType Tipo da peca para colocar no lugar do peao
     * @return Se a troca foi realizada
     */
    public boolean changePawnType(boolean isUser, PieceType newType){
        if(hasPawnOnFinal() && (isUser && isUserTurn) || (!isUser && !isUserTurn)){
            // pega posicao da linha que vai procurar o peao
            int line;
            if(isUser) line = getFinalLineOf(USER_COLOR);
            else line = getFinalLineOf(PieceColor.getOpponentOf(USER_COLOR));

            for(int column = 0; column < 8; column++){
                Piece pieceOnFinal = board.getPiece(line, column);

                if(pieceOnFinal instanceof Pawn){
                    // insere nova peca selecionada no lugar do peao
                    board.setPiece(newType, pieceOnFinal.getColor(), line, column, pieceOnFinal.hasMoved());

                    changeTurn();

                    // troca realizada
                    return true;
                }
            }
        }
        // troca nao realizada
        return false;
    }

    /**
     * Verifica se algum peao chegou no final do tabuleiro.
     *
     * @return Se algum peao chegou no final do tabuleiro.
     */
    public boolean hasPawnOnFinal(){
        PieceColor[] colors = {USER_COLOR, PieceColor.getOpponentOf(USER_COLOR)};

        for(PieceColor color : colors) {
            int line = getFinalLineOf(color);
            for(int column = 0; column < 8; column++) {

                Piece piece = board.getPiece(line, column);
                if(piece instanceof Pawn){
                    return true;
                }
            }
        }return false;
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
    private void verifyWin(){
        // se o rei do usuario morreu
        if(isKingDies(USER_COLOR)){
            // oponente venceu
            isAIWon = true;
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
    public boolean verifyCheckOnKing(PieceColor color){
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
     * Entretanto nao eh realizada a copia da lista de backups.
     */
    private void addBackup(){
        BoardController backup = clone();
        backups.add(backup);
    }


    /**
     * Retorna o tabuleiro atual para o ultimo backup salvo.
     * Alem disso remove da lista de backups do ultimo salvo.
     *
     * @return Se deu certo retornar
     */
    public boolean returnBackup(){
        // se existir backups anteriores
        if(backups.size() > 0){

            BoardController last = backups.get(backups.size()-1);

            this.board = last.board;
            this.isUserTurn = last.isUserTurn;
            this.isAIWon = last.isAIWon;
            this.isUserWon = last.isUserWon;

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
    public boolean isAIWon() {
        return isAIWon;
    }

    /**
     * Retorna se o usuario venceu.
     *
     * @return Se o usuario venceu
     */
    public boolean isUserWon() {
        return isUserWon;
    }

    @Override
    public BoardController clone() {
        try {
            BoardController clone = (BoardController) super.clone();
            clone.board = board.clone();

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
