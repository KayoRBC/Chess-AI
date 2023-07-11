package com.chess_AI.controller;

import com.chess_AI.model.chess.Board;
import com.chess_AI.model.chess.Piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import java.util.ArrayList;

/**
 * Esta classe representa o controller do model chess, ela contem as regras do tabuleiro de xadrez.
 */
public class BoardController implements Cloneable{

    /** Armazena os estados dos tabuleiros anteriores*/
    private final ArrayList<BoardController> BACKUPS = new ArrayList<>();

    /** Cor da peca do usuario*/
    private final PieceColor USER_COLOR;

    /** Tabuleiro que armazena as pecas*/
    private Board board;

    /** Se eh o turno do usuario*/
    private boolean isUserTurn;

    /** Se a IA ganhou ganhou*/
    private boolean isAIWon;

    /** Se o jogador ganhou*/
    private boolean isUserWon;

    /**
     * Cria objeto de BoardController com um novo tabuleiro.
     *
     * @param isUserStarts Se o usuario que vai comecar
     * @param USER_COLOR Cor da peca do usuario
     */
    public BoardController(PieceColor USER_COLOR, boolean isUserStarts){
        board = new Board();
        this.isUserTurn = isUserStarts;
        this.isUserWon = false;
        this.isAIWon = false;
        this.USER_COLOR = USER_COLOR;
    }

    /**
     * Tenta movimentar a peca de uma dada posicao para outra.
     * Se aplicar e um peao nao chegou no final entao adiciona backup.
     *
     * @param isUser Se eh o usuario movimentando a peca
     * @param move Movimentacao para aplicar no tabuleiro
     * @return Se deu certo movimentar a peca
     */
    public boolean makeMove(boolean isUser, Move move){

        boolean isNotAnyoneWon = !(isUserWon || isAIWon);

        if(isNotAnyoneWon && !verifyPawnPromote()){
            Piece fromPiece = board.getPiece(move.FROM);

            boolean isUserMove = isUser && isUserTurn
                                && fromPiece.getColor() == USER_COLOR;

            boolean isAIMove = !isUser && !isUserTurn
                                && fromPiece.getColor() == PieceColor.getOpponentOf(USER_COLOR);

            if((isUserMove || isAIMove)) {

                if(applyCastling(move)) return true;

                else if(fromPiece.isValidMove(board, move)){
                    addBackup();

                    fromPiece.setHasMoved(true);
                    board.removePiece(move.TO);
                    board.switchPieces(move.FROM, move.TO);

                    if(verifyPawnPromote()){
                        returnBackup();
                        fromPiece.setHasMoved(true);
                        board.removePiece(move.TO);
                        board.switchPieces(move.FROM, move.TO);
                    }

                    verifyWin();

                    if(!verifyPawnPromote()) changeTurn();

                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica e aplica caso possivel o movimento de castling.
     * Se aplicar entao adiciona backup e troca o turno.
     * Tomar cuidado pois nao verifica quem esta realizando o movimento.
     *
     * @param move Movimento para verificar e aplicar
     * @return Se aplicou o castling
     */
    private boolean applyCastling(Move move) {
        Piece fromPiece = board.getPiece(move.FROM);

        if(fromPiece != null && fromPiece.isCastlingMove(board, move)) {
            addBackup();

            int horizontalDistance = move.FROM.COLUMN - move.TO.COLUMN;

            if (fromPiece instanceof King) {
                boolean isRight = horizontalDistance < 0;
                if (isRight) {
                    board.switchPieces(move.FROM, new Position(move.FROM.LINE, move.FROM.COLUMN + 2));
                    board.switchPieces(move.TO, new Position(move.TO.LINE, move.TO.COLUMN - 2));
                } else {
                    board.switchPieces(move.FROM, new Position(move.FROM.LINE, move.FROM.COLUMN - 2));
                    board.switchPieces(move.TO, new Position(move.TO.LINE, move.TO.COLUMN + 3));
                }

            }
            else{ //if (fromPiece instanceof Rook)
                boolean isRight = horizontalDistance > 0;
                if (isRight) {
                    board.switchPieces(move.FROM, new Position(move.FROM.LINE, move.FROM.COLUMN - 2));
                    board.switchPieces(move.TO, new Position(move.TO.LINE, move.TO.COLUMN + 2));
                } else {
                    board.switchPieces(move.FROM, new Position(move.FROM.LINE, move.FROM.COLUMN + 3));
                    board.switchPieces(move.TO, new Position(move.TO.LINE, move.TO.COLUMN - 2));
                }
            }

            Piece toPiece = board.getPiece(move.TO);
            fromPiece.setHasMoved(true);
            toPiece.setHasMoved(true);

            changeTurn();

            return true;
        }
        return false;
    }

    /**
     * Troca o peao que chegou no final do tabuleiro por um novo tipo de peca.
     * Se conseguir mudar cria backup e troca o turno.
     *
     * @param isUser Se eh o usuario
     * @param newType Tipo da peca para colocar no lugar do peao
     * @return Se a troca foi realizada
     */
    public boolean makePawnPromote(boolean isUser, PieceType newType){
        if(verifyPawnPromote() && (isUser && isUserTurn) || (!isUser && !isUserTurn)){
            // pega posicao da linha que vai procurar o peao
            int line;
            if(isUser) line = getFinalLineOf(USER_COLOR);
            else line = getFinalLineOf(PieceColor.getOpponentOf(USER_COLOR));

            for(int column = 0; column < 8; column++){
                Position position = new Position(line, column);
                Piece pieceOnFinal = board.getPiece(position);

                if(pieceOnFinal instanceof Pawn){
                    addBackup();

                    // insere nova peca selecionada no lugar do peao
                    board.setPiece(newType, pieceOnFinal.getColor(), pieceOnFinal.hasMoved(), position);

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
    public boolean verifyPawnPromote(){
        PieceColor[] colors = {USER_COLOR, PieceColor.getOpponentOf(USER_COLOR)};

        for(PieceColor color : colors) {
            int line = getFinalLineOf(color);
            for(int column = 0; column < 8; column++) {
                Position position = new Position(line, column);

                Piece piece = board.getPiece(position);
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
                Position position = new Position(i, j);
                Piece piece = board.getPiece(position);
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
                Position position = new Position(i, j);
                Piece piece = board.getPiece(position);

                if(piece instanceof King && piece.getColor() == color){
                    if(board.isDungerousPosition(color, position)){
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
     * Entretanto nao eh realizada a copia da lista de backups, a mesma eh reaproveitada.
     */
    private void addBackup(){
        BoardController backup = clone();
        BACKUPS.add(backup);
    }


    /**
     * Retorna o tabuleiro atual para o ultimo backup salvo.
     * Alem disso remove da lista de backups o ultimo salvo.
     *
     * @return Se deu certo retornar
     */
    public boolean returnBackup(){
        // se existir backups anteriores
        if(BACKUPS.size() > 0){

            BoardController last = BACKUPS.get(BACKUPS.size()-1);

            this.board = last.board;
            this.isUserTurn = last.isUserTurn;
            this.isAIWon = last.isAIWon;
            this.isUserWon = last.isUserWon;

            // deleta ultimo backup da lista de backups
            BACKUPS.remove(last);

            // backup realizado
            return true;
        }
        // backup nao realizado
        return false;
    }


    /**
     * Pega o tipo da peca de uma determinada posicao do tabuleiro.
     *
     * @param position Posicao no tabuleiro
     * @return Tipo da peca. Se nao existir entao null
     */
    public PieceType getTypeOf(Position position){
        Piece piece = board.getPiece(position);
        if (piece != null) return piece.getType();
        else return null;
    }

    /**
     * Pega a cor da peca de uma determinada posicao do tabuleiro.
     *
     * @param position Posicao no tabuleiro
     * @return Cor da peca. Se nao existir entao null
     */
    public PieceColor getColorOf(Position position){
        Piece piece = board.getPiece(position);
        if (piece != null) return piece.getColor();
        else return null;
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
