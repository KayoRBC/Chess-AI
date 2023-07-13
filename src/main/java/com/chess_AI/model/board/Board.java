package com.chess_AI.model.board;

import com.chess_AI.model.board.piece.*;
import com.chess_AI.util.PieceColor;
import com.chess_AI.util.Functions;
import com.chess_AI.util.Move;
import com.chess_AI.util.Position;

import java.util.ArrayList;

/**
 * Esta classe representa o tabuleiro da xadrez.
 */
public class Board implements Cloneable{

    /** Representa a distrubuicao das pecas no tabuleiro.*/
    private Piece[][] pieces;

    /** Cor da peca do usuario.*/
    private final PieceColor USER_COLOR;

    /** Se eh o turno do usuario.*/
    private boolean isUserTurn;

    /** Se a IA ganhou.*/
    private boolean isAIWon;

    /** Se o jogador ganhou.*/
    private boolean isUserWon;

    /**
     * Cria e retorna um objeto de Board com o estado inicial do jogo.
     */
    public Board(PieceColor userColor){
        USER_COLOR = userColor;

        // define quem vai comecar o jogo
        isUserTurn = userColor == PieceColor.WHITE;

        // define a direcao do tabuleiro para ser mostrado
        PieceColor.setIsWhiteUp(USER_COLOR != PieceColor.WHITE);

        isAIWon = false;
        isUserWon = false;

        pieces = createInitialState();
    }

    /**
     * Troca o turno.
     */
    public void changeTurn(){
        this.isUserTurn = !isUserTurn;
    }

    /**
     * Verifica se alguem venceu e atualiza as variaveis isOpponentWon e isUserWon.
     */
    public void verifyWon(){
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
     * Troca de lugar entre duas pecas no tabuleiro.
     * Se as pecas nao existirem entao nao realiza a troca.
     *
     * @param first Posicao da primeira peca.
     * @param second Posicao da segunda peca.
     */
    public void switchPieces(Position first, Position second){
        Piece firstPiece = getPiece(first);

        Piece secondPiece = getPiece(second);

        // troca posicoes
        if(firstPiece != null && secondPiece != null) {
            pieces[first.LINE][first.COLUMN] = secondPiece;
            pieces[second.LINE][second.COLUMN] = firstPiece;
        }
    }

    /**
     * Remove uma peca do tabuleiro.
     *
     * @param position Posicao da peca para remover
     */
    public void removePiece(Position position){
        try {
            pieces[position.LINE][position.COLUMN] = new Null();
        } catch (Exception ignored){
        }
    }

    /**
     * Retorna uma peca do tabuleiro.
     *
     * @param position Posicao da peca no tabuleiro.
     * @return A peca (se nao existir retorna null).
     */
    public Piece getPiece(Position position){
        try{
            return pieces[position.LINE][position.COLUMN];
        } catch (Exception e){
            return null;
        }
    }

    /**
     * Insere uma nova peca no tabuleiro.
     * Cuidado pois substitui a peca que estava anteriormente na posicao do tabuleiro.
     *
     * @param piece Peca para inserir. Se for null entao insere uma peca Null.
     * @param position Posicao para inserir no tabuleiro.
     * @return Se conseguiu inserir a peca.
     */
    public boolean setPiece(Piece piece, Position position){
        if(piece == null) piece = new Null();

        // insere peca no tabuleiro
        try{
            pieces[position.LINE][position.COLUMN] = piece;
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * Verifica se uma parte de uma linha do tabuleiro eh segura para uma cor.
     * Posicoes que nao existem vao ser consideradas seguras.
     *
     * @param line Posicao da linha para verificar.
     * @param startColumn Posicao da coluna inicial da linha (incluso).
     * @param finalColumn Posicao da coluna final da linha (incluso).
     * @param allyColor Cor para verificar se eh seguro.
     * @return Se as posicoes sao seguras para a cor.
     */
    public boolean isSafeLine(int line, int startColumn, int finalColumn, PieceColor allyColor){

        // aumenta o intervalo de valores superior e inferior em +1,
        // pois a funcao para pegar as colunas apenas retorna valores intermediarios
        if(startColumn < finalColumn){
            startColumn--;
            finalColumn++;
        }
        else{
            startColumn++;
            finalColumn--;
        }

        // verifica se as colunas da linha sao seguras
        int[] columns = Functions.createIntermediateValues(startColumn, finalColumn);
        if(columns != null){
            for(int column: columns){
                Position position = new Position(line, column);
                if(isDungerousPosition(allyColor, position)) return false;
            }
        }
        return true;
    }

    /**
     * Verifica se uma posicao do tabuleiro eh perigosa para uma cor de peca.
     * Para saber se uma posicao do tabuleiro eh perigosa, uma peca da cor oposta
     * deve conseguir ir na posicao verificada.
     *
     * @param allyColor Cor da peca para verificar se eh segura.
     * @param position Posicao no tabuleiro para verificar.
     * @return Se a posicao eh perigosa para a cor.
     */
    public boolean isDungerousPosition(PieceColor allyColor, Position position){
        // percorre posicoes do tabuleiro
        for(int fromLine = 0; fromLine < 8; fromLine++){
            for(int fromColumn = 0; fromColumn < 8; fromColumn++){
                Position from = new Position(fromLine, fromColumn);
                Piece piece = getPiece(from);

                if(piece != null) {
                    boolean isEnemy = piece.getColor() != allyColor;
                    if (isEnemy) {
                        // verifica se peca inimiga pode ir na posicao aliada
                        Move move = new Move(from, position);
                        if (piece.isValidMove(this, move)) return true; // posicao perigosa
                    }
                }
            }
        }
        return false; // posicao segura
    }

    /**
     * Retorna a cor das pecas do usuario.
     *
     * @return Cor das pecas do usuario.
     */
    public PieceColor getUserColor() {
        return USER_COLOR;
    }

    /**
     * Retorna se eh o turno do usuario.
     *
     * @return Se eh o turno do usuario.
     */
    public boolean isUserTurn() {
        return isUserTurn;
    }

    /**
     * Retorna se a IA venceu.
     *
     * @return Se a IA venceu.
     */
    public boolean isAIWon() {
        return isAIWon;
    }

    /**
     * Retorna se o jogador venceu.
     *
     * @return Se o jogador venceu.
     */
    public boolean isUserWon() {
        return isUserWon;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();

            Piece[][] piecesClone = new Piece[8][8];
            for(int i = 0; i < 8; i++){
                for(int j = 0; j < 8; j++){
                    piecesClone[i][j] = pieces[i][j].clone();
                }
            }
            clone.pieces = piecesClone;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    /**
     * Cria e retorna uma matriz com as pecas em suas posicoes iniciais no tabuleiro.
     * A orientacao das pecas brancas e pretas no tabuleiro eh definida pela classe PieceColor.
     *
     * @return Uma matriz com as pecas em suas posicoes iniciais no tabuleiro.
     */
    private Piece[][] createInitialState(){
        // matriz que vai armazenar as pecas
        Piece[][] pieces = new Piece[8][8];

        // define qual vai ser a cor que vai ser desenhada em cima e em baixo do tabuleiro
        PieceColor colorUp;
        PieceColor colorDown;
        if(PieceColor.isWhiteUp()){
            colorUp = PieceColor.WHITE;
            colorDown = PieceColor.BLACK;
        }
        else{
            colorUp = PieceColor.BLACK;
            colorDown = PieceColor.WHITE;
        }

        // insere pecas iniciais na parte de cima do tabuleiro
        pieces[0] = createBackLine(colorUp);
        pieces[1] = createFrontLine(colorUp);

        // insere regiao intermediaria entre as duas cores
        for(int i = 2; i < 6; i++){
            pieces[i] = createNullLine();
        }

        // insere pecas iniciais na parte de baixo do tabuleiro
        pieces[6] = createFrontLine(colorDown);
        pieces[7] = createBackLine(colorDown);

        return pieces;
    }

    /**
     * Cria e retorna uma linha com pecas vazias.
     *
     * @return Array com pecas vazias.
     */
    private Piece[] createNullLine(){
        Piece[] nullLine = new Piece[8];

        // insere NullPieces em toda linha
        for(int i = 0; i < nullLine.length; i++){
            nullLine[i] = new Null();
        }

        return nullLine;
    }

    /**
     * Cria e retorna uma linha do tabuleiro com peoes de uma determinada cor.
     *
     * @param color Cor dos peoes.
     * @return Array com peoes.
     */
    private Piece[] createFrontLine(PieceColor color){
        Piece[] frontLine = new Piece[8];

        // insere peoes em toda linha
        for(int i = 0; i < frontLine.length; i++){
            frontLine[i] = new Pawn(color);
        }

        return frontLine;
    }

    /**
     * Cria e retorna as pecas que estao atras dos peoes de uma determinada cor.
     *
     * @param color Cor das pecas.
     * @return Array com as pecas que ficam atras dos peoes.
     */
    private Piece[] createBackLine(PieceColor color){
        // cria linha de tras
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

    /**
     * Verifica se o rei de uma determinada cor morreu.
     *
     * @param color Cor do rei.
     * @return Se o rei morreu.
     */
    private boolean isKingDies(PieceColor color){
        // percorre posicoes do tabuleiro
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Position position = new Position(i, j);
                Piece piece = this.getPiece(position);
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
}