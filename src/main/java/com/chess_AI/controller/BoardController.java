package com.chess_AI.controller;

import com.chess_AI.model.board.Board;
import com.chess_AI.model.board.piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.PieceType;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import java.util.ArrayList;

/**
 * Esta classe representa o controller de board.
 */
public class BoardController implements Cloneable{

    /** Tabuleiro que armazena as pecas.*/
    private Board board;

    /** Backups dos tabuleiros anteriores.*/
    private ArrayList<Board> BACKUPS = new ArrayList<>();

    /**
     * Cria objeto de BoardController com um novo tabuleiro.
     *
     * @param USER_COLOR Cor da peca do usuario.
     */
    public BoardController(PieceColor USER_COLOR){
        board = new Board(USER_COLOR);
    }

    /**
     * Tenta movimentar a peca de uma dada posicao para outra.
     * Se aplicar e um peao nao chegou no final entao adiciona backup.
     *
     * @param isUser Se eh o usuario movimentando a peca.
     * @param move Movimentacao para aplicar no tabuleiro.
     * @return Se deu certo movimentar a peca.
     */
    public boolean makeMove(boolean isUser, Move move){

        boolean isNotAnyoneWon = !(board.isUserWon() || board.isAIWon());

        if(isNotAnyoneWon && !verifyPawnPromote()){
            Piece fromPiece = board.getPiece(move.FROM);

            boolean isAUserPiece = fromPiece.getColor() == board.getUserColor();

            boolean isUserMove = isUser && board.isUserTurn()
                                && isAUserPiece;

            boolean isAIMove = !isUser && !board.isUserTurn()
                                && !isAUserPiece;

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

                    board.verifyWon();

                    if(!verifyPawnPromote()) board.changeTurn();

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
     * @param move Movimento para verificar e aplicar.
     * @return Se aplicou o castling.
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

            board.changeTurn();

            return true;
        }
        return false;
    }

    /**
     * Troca o peao que chegou no final do tabuleiro por um novo tipo de peca.
     * Se conseguir mudar cria backup e troca o turno.
     *
     * @param isUser Se eh o usuario.
     * @param newType Tipo da peca para colocar no lugar do peao.
     * @return Se a troca foi realizada.
     */
    public boolean makePawnPromote(boolean isUser, PieceType newType){

        boolean isCorrectSubject = (isUser && board.isUserTurn()) || (!isUser && !board.isUserTurn());

        boolean isValidType = newType == PieceType.BISHOP
                            || newType == PieceType.KNIGHT
                            || newType == PieceType.QUEEN
                            || newType == PieceType.ROOK;

        if(verifyPawnPromote() && isCorrectSubject && isValidType){
            // pega posicao da linha que vai procurar o peao
            int line;
            if(isUser) line = PieceColor.getFinalLineOf(board.getUserColor());
            else line = PieceColor.getFinalLineOf(PieceColor.getOpponentOf(board.getUserColor()));

            // procura peao para promover
            for(int column = 0; column < 8; column++){
                Position position = new Position(line, column);
                Piece pieceOnFinal = board.getPiece(position);

                if(pieceOnFinal instanceof Pawn){
                    addBackup();

                    // cria nova peca
                    PieceColor color = pieceOnFinal.getColor();
                    Piece newPiece = switch (newType) {
                        case BISHOP -> new Bishop(color);
                        case KNIGHT -> new Knight(color);
                        case QUEEN -> new Queen(color);
                        default -> new Rook(color);
                    };
                    newPiece.setHasMoved(true);

                    board.setPiece(newPiece, position);

                    board.changeTurn();

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
        for(PieceColor color : PieceColor.values()) {
            int line = PieceColor.getFinalLineOf(color);
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
     * Verifica se o rei de uma determinada cor esta em check.
     *
     * @param color Cor do rei.
     * @return Se esta em check.
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
     */
    private void addBackup(){
        BACKUPS.add(board.clone());
    }

    /**
     * Retorna o tabuleiro atual para o ultimo backup salvo.
     * Alem disso remove da lista de backups o ultimo salvo.
     */
    public void returnBackup(){
        if(BACKUPS.size() > 0){
            int lastIndex = BACKUPS.size() - 1;
            board = BACKUPS.get(lastIndex);
            BACKUPS.remove(lastIndex);
        }
    }

    /**
     * Pega o tipo da peca de uma determinada posicao do tabuleiro.
     *
     * @param position Posicao do tabuleiro.
     * @return Tipo da peca. Se nao existir a posicao entao retorna null.
     */
    public PieceType getTypeOf(Position position){
        Piece piece = board.getPiece(position);
        if(piece instanceof Null) return PieceType.NULL;
        else if(piece instanceof Pawn) return PieceType.PAWN;
        else if(piece instanceof Rook) return PieceType.ROOK;
        else if(piece instanceof Bishop) return PieceType.BISHOP;
        else if(piece instanceof Knight) return PieceType.KNIGHT;
        else if(piece instanceof Queen) return PieceType.QUEEN;
        else if(piece instanceof King) return PieceType.KING;
        else return null;
    }

    /**
     * Pega a cor da peca de uma determinada posicao do tabuleiro.
     *
     * @param position Posicao no tabuleiro.
     * @return Cor da peca. Se nao existir entao null.
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
        return board.isUserTurn();
    }

    /**
     * Retorna se o oponente venceu.
     *
     * @return Se o oponente venceu
     */
    public boolean isAIWon() {
        return board.isAIWon();
    }

    /**
     * Retorna se o usuario venceu.
     *
     * @return Se o usuario venceu
     */
    public boolean isUserWon() {
        return board.isUserWon();
    }
}
